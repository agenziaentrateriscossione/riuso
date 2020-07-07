package it.tredi.dw4.docway.beans;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.docway.doc.adapters.DocDocWayDocumentFormsAdapter;
import it.tredi.dw4.docway.model.Arrivo;
import it.tredi.dw4.docway.model.Doc;
import it.tredi.dw4.docway.model.XwFile;
import it.tredi.dw4.docway.model.intraaoo.IntraAoo;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowdocArrivo extends ShowdocDoc {

    /**
     * Lista di file generati dal protocollo PEC o dall'archiviatore da scartare in fase di generazione di protocolli multipli
     */
    public static final List<String> INVALID_FILE_LIST = Arrays.asList("testo email", "testo email html", "daticert.xml", "smime.p7s", "MessaggioOriginale.eml", "Segnatura.xml");

    public ShowdocArrivo() throws Exception {
        this.formsAdapter = new DocDocWayDocumentFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
    }

    @Override
    public void init(Document dom) {
        Element root = dom.getRootElement();

        xml = dom.asXML();
        if (root.attributeValue("view", "").equals("verificaDuplicati"))
            this.verificaDuplicati = true;
        doc = new Arrivo();
        doc.init(dom);

        // mbernardini 28/10/2016 : gestione delle aoo configurate per la comunicazione intra-aoo
        setIntraAoo(new IntraAoo());
        getIntraAoo().init(XMLUtil.createDocument(dom, "/response/doc/extra/intraAoo"));

        initAvailableIntraAoos(dom);

        initCommon(dom);

        // in caso di presenza di dati relativi a intraAOO occorre visualizzare la sezione 'stati documento'
        if (!isShowSectionStatiDocumento()) {
            if (getIntraAoo() != null && (!getIntraAoo().getFrom().isEmpty() || !getIntraAoo().getTo().isEmpty()))
                setShowSectionStatiDocumento(true);
        }
    }

    @Override
    public void reload() throws Exception {
        super._reload(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/docway/showdoc@arrivo");
    }

    /**
     * Gestione delle bozze in arrivo con la creazione di protocolli multipli.
     * Scatena l'apertura di una modale dove è possibile specificare il repertorio dei protocolli in arrivo da creare
     * e quale file ricevuto tramite email associare a ciascun nuovo protocollo.
     * <br>
     * Effettua un controllo preventivo sui file, se non ve ne sono di validi, lancia un warning e impedisce la
     * continuazione
     */
    public void protocolliMultipli() throws Exception {
        try {
            if (this.doc.getFiles() == null ||
                    this.doc.getFiles().isEmpty() ||
                    this.doc.getFiles().stream().noneMatch(this::fileNameIsValidAttachment)) {
                // creazione del warning, perchè nessun file è utilizzabile
                setErroreResponse(I18N.mrs("dw4.nessun_allegato_disponibile_per_la_protocollazione_multipla"),
                        Const.MSG_LEVEL_WARNING);
            } else {
                // richiesta ed estrazione dei repertori in arrivo inseribili dall'utente
                this.formsAdapter.richiestaProtocollazioneMultipla(doc.getNrecord());
                XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
                if (handleErrorResponse(response)) {
                    formsAdapter.fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form
                    return;
                }

                // lista di file validi
                List<XwFile> availableFiles = this.doc.getFiles()
                        .stream()
                        .filter(this::fileNameIsValidAttachment)
                        .collect(Collectors.toList());

                // apertura modale di inserimento protocolli multipli // FIXME inizializzazione può essere gestita un pò meglio
                DocWayProtocolliMultipli docWayProtocolliMultipli = new DocWayProtocolliMultipli(this, availableFiles);
                docWayProtocolliMultipli.init(response.getDocument());

                // controlli su file disponibili
                if (!docWayProtocolliMultipli.isAnyFileSelectable()) {
                    // nessun file sarebbe selezionabile, errore
                    setErroreResponse(I18N.mrs("dw4.tutti_gli_allegati_sono_gia_stati_gestiti_da_precedenti_protocollazioni"),
                            Const.MSG_LEVEL_WARNING);
                } else {
                    // apertura modale vera e propria
                    docWayProtocolliMultipli.setActive(true);
                    setSessionAttribute("docWayProtocolliMultipli", docWayProtocolliMultipli);
                }
            }
        } catch (Throwable t) {
            handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
            getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
        }
    }

    /**
     * Metodo di gestione dei protocolli arrivo multipli da creare, già selezionati dalla modale creata attraverso
     * {@link ShowdocArrivo#protocolliMultipli()}
     *
     * @param protocolliDaCreare Lista dei protocolli da salvare su db
     * @param originalDraft      Documento arrivo da salvare come protocollo multiplo
     */
    public void handleRichiestaProtocolliMultipli(List<DocWayProtocolliMultipli.ProtocolloArrivo> protocolliDaCreare,
                                                  Doc originalDraft) throws Exception {
        try {
            this.formsAdapter.saveProtocolliMultipliDaBozzaArrivo(protocolliDaCreare, originalDraft);
            XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
            if (handleErrorResponse(response)) {
                formsAdapter.fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form
                return;
            }
            // visualizzazione loadingbar
            String verbo = response.getAttributeValue("/response/@verbo");
            if (verbo.equals("loadingbar")) {
                DocWayLoadingbar docWayLoadingbar = new DocWayLoadingbar();
                docWayLoadingbar.getFormsAdapter().fillFormsFromResponse(response);
                docWayLoadingbar.init(response);
                docWayLoadingbar.setHolderPageBean(this);
                setLoadingbar(docWayLoadingbar);
                docWayLoadingbar.setActive(true);
                setAction("saveProtocolliMultipli");
            }
            formsAdapter.fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form

        } catch (Throwable t) {
            handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
            getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
        }
    }

    /**
     * Controllo se il nome dell'allegato è valido, ovvero non è tra gli allegati da scartare perchè generati dal
     * protocollo PEC o dal MSA e che non sia un der_from generato dall'indicizzazione del contenuto degli allegati di xw
     *
     * @param xwFile XwFile da controllare
     * @return TRUE se il filename è valido, FALSE altrimenti
     */
    private boolean fileNameIsValidAttachment(XwFile xwFile) {
        // set di files allegati alla bozza da scartare in fase di selezione
        Set<String> invalidFiles = new HashSet<>(INVALID_FILE_LIST);
        String nomeFile = xwFile.getTitle();
        String der_from = xwFile.getDer_from();
        return !invalidFiles.contains(nomeFile) && der_from.isEmpty();
    }

    /**
     * Caricamento della lista titoli dei protocolli multipli appena generati
     * @return String JSF di caricamento della lista titoli
     */
    public String loadProtocolliMultipliCreated() throws Exception {
        try {
            getLoadingbar().setActive(false);
            formsAdapter.queryDocs("[/doc/extra/protocolliMultipli/@originalNrecord/]="+doc.getNrecord());
            XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
            if (handleErrorResponse(response)) {
                formsAdapter.fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form
                return null;
            }
            if (response.getAttributeValue("//response/@verbo", "").equals("showdoc")){
                return buildSpecificShowdocPageAndReturnNavigationRule(response.getAttributeValue("/response/@dbTable"), response);
            }
            else{
                DocWayTitles titles = new DocWayTitles();
                titles.getFormsAdapter().fillFormsFromResponse(response);
                titles.init(response.getDocument());
                titles.setPopupPage(isPopupPage());
                setSessionAttribute("docwayTitles", titles);
                return "showtitles@docway";
            }
        }
        catch (Throwable t) {
            handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
            return null;
        }
    }

}
