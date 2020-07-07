package it.tredi.dw4.docway.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.Element;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.beans.AutoCloseMsg;
import it.tredi.dw4.beans.ClassifFormatManager;
import it.tredi.dw4.beans.Errormsg;
import it.tredi.dw4.beans.Msg;
import it.tredi.dw4.docway.doc.adapters.DocDocWayQueryFormsAdapter;
import it.tredi.dw4.docway.model.ExportPersonalizzato;
import it.tredi.dw4.docway.model.FascicoloSpecialeInfo;
import it.tredi.dw4.docway.model.HomeContent;
import it.tredi.dw4.docway.model.Option;
import it.tredi.dw4.docway.model.Vaschetta;
import it.tredi.dw4.docway.model.VaschettaDelega;
import it.tredi.dw4.docway.utils.MultiSocietaMap;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.model.Titolo;
import it.tredi.dw4.utils.AppUtil;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.DateUtil;
import it.tredi.dw4.utils.DigitVisibilitaUtil;
import it.tredi.dw4.utils.DocWayProperties;
import it.tredi.dw4.utils.Logger;
import it.tredi.dw4.utils.QueryUtil;
import it.tredi.dw4.utils.StringUtil;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;

public class DocWayHome extends DocWayQuery {

	private DocDocWayQueryFormsAdapter formsAdapter;
	private String xml;

	private String annoProt = "";
	private String numProt = "";
	private boolean estremi = false;
	private String searchTerms = "";
	private String query = "";

	private List<Vaschetta> vaschette = new ArrayList<Vaschetta>();

	//tiommi 15/09/2017 : vaschette per deleghe
	private List<VaschettaDelega> vaschetteDeleghe = new ArrayList<VaschettaDelega>();

	private boolean scrivaniaPersonaleEnabled = false;
	private List<Vaschetta> vaschetteFatture = new ArrayList<Vaschetta>();
	private List<Vaschetta> vaschetteCustom = new ArrayList<Vaschetta>();
	private List<ExportPersonalizzato> esportazioni = new ArrayList<ExportPersonalizzato>();
	private List<Vaschetta> vaschetteRuoli = new ArrayList<Vaschetta>();
	private List<Option> uor_select;
	private String selected_uor = "";
	private String ultimo_aggiornamento_vaschette = "";

	//dpranteda 19/03/2015 : vaschette per abo on-line esterno
	private List<Vaschetta> vaschetteAlboExt = new ArrayList<Vaschetta>();
	//dpranteda 27/11/2019 : vaschette per PEC MANAGER
	private boolean showVaschettaPecManagerArchiviatoRigettato = false;
	private List<Vaschetta> vaschettePecManagerArchiviatoRigettato = new ArrayList<Vaschetta>();
	private List<Vaschetta> vaschettePecManager = new ArrayList<Vaschetta>();

	private boolean abilitaCestino = false; // abilitazione del cestino per cancellazione logica dei documenti

	// proprieta' necessarie al caricamento del popup di selezione dell'uor
	private List<Option> uor_complete_select;
	private boolean uor_popup_active = false;

	// caricamento di doc da URL esterno (prettyFaces)
	private String docAlias = ""; // alias o chiave di ricerca
	private String docValue = ""; // nrecord o valore da ricercare
	private String docDb = "";
	private String docCodDelegante = ""; // matricola del delegante con cui si cerca di accedere

	// caricamento di lista doc da URL esterno (prettyFaces)
	private String titlesQuery = "";
	private String titlesOrd = "";
	private String titlesVerbo = "";
	private String titlesDb = "";

	private boolean hide_left_column = false; // identifica se la colonna di sx (vaschette) deve essere aperta o chiusa

	private List<FascicoloSpecialeInfo> fascicoli_speciali = new ArrayList<FascicoloSpecialeInfo>(); // gestione dei fascicoli speciali

	private HomeContent homeContent = new HomeContent(); // contenuto custom della homepage dell'utente

	// creazione di un nuovo documento non protocollato tramite drag&drop di files
	private String dadxwFileIdsAttached = "";
	private String dadxwFileTitlesAttached = "";

	// etichette custom per visibilita'
	private Map<String, String> labelsVisibilita = new HashMap<String, String>();

	private boolean enableIW = false;

	private boolean showVaschetteResponsive = true;

	private Map<String, String> tipiRaccoglitoreIndice = new LinkedHashMap<String, String>();

	public DocDocWayQueryFormsAdapter getFormsAdapter() {
		return this.formsAdapter;
	}

	public DocWayHome() throws Exception {
		this.formsAdapter = new DocDocWayQueryFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
		clearForm();
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getXml() {
		return xml;
	}

	public void setVaschette(List<Vaschetta> vaschette) {
		this.vaschette = vaschette;
	}

	public List<Vaschetta> getVaschette() {
		return vaschette;
	}

	public List<Vaschetta> getVaschetteFatture() {
		return vaschetteFatture;
	}

	public void setVaschetteFatture(
			List<Vaschetta> vaschetteFatture) {
		this.vaschetteFatture = vaschetteFatture;
	}

	public boolean isScrivaniaPersonaleEnabled() {
		return scrivaniaPersonaleEnabled;
	}

	public void setScrivaniaPersonaleEnabled(boolean scrivaniaPersonaleEnabled) {
		this.scrivaniaPersonaleEnabled = scrivaniaPersonaleEnabled;
	}

	public List<Vaschetta> getVaschetteCustom() {
		return vaschetteCustom;
	}

	public void setVaschetteCustom(List<Vaschetta> vaschetteCustom) {
		this.vaschetteCustom = vaschetteCustom;
	}

	public List<VaschettaDelega> getVaschetteDeleghe() {
		return vaschetteDeleghe;
	}

	public void setVaschetteDeleghe(List<VaschettaDelega> vaschetteDeleghe) {
		this.vaschetteDeleghe = vaschetteDeleghe;
	}

	public List<ExportPersonalizzato> getEsportazioni() {
		return esportazioni;
	}

	public void setEsportazioni(List<ExportPersonalizzato> esportazioni) {
		this.esportazioni = esportazioni;
	}

	public List<Vaschetta> getVaschetteRuoli() {
		return vaschetteRuoli;
	}

	public void setVaschetteRuoli(List<Vaschetta> vaschetteRuoli) {
		this.vaschetteRuoli = vaschetteRuoli;
	}

	public String getSelected_uor() {
		return selected_uor;
	}

	public void setSelected_uor(String selected_uor) {
		this.selected_uor = selected_uor;
	}

	public String getUltimo_aggiornamento_vaschette() {
		return ultimo_aggiornamento_vaschette;
	}

	public void setUltimo_aggiornamento_vaschette(String ultimo_aggiornamento_vaschette) {
		this.ultimo_aggiornamento_vaschette = ultimo_aggiornamento_vaschette;
	}

	public boolean isAbilitaCestino() {
		return abilitaCestino;
	}

	public void setAbilitaCestino(boolean abilitaCestino) {
		this.abilitaCestino = abilitaCestino;
	}

	public List<Option> getUor_complete_select() {
		return uor_complete_select;
	}

	public void setUor_complete_select(List<Option> uor_complete_select) {
		this.uor_complete_select = uor_complete_select;
	}

	public boolean isUor_popup_active() {
		return uor_popup_active;
	}

	public void setUor_popup_active(boolean uor_popup_active) {
		this.uor_popup_active = uor_popup_active;
	}

	public String getDocAlias() {
		return docAlias;
	}

	public void setDocAlias(String docAlias) {
		this.docAlias = docAlias;
	}

	public String getDocValue() {
		return docValue;
	}

	public void setDocValue(String docValue) {
		this.docValue = docValue;
	}

	public String getDocDb() {
		return docDb;
	}

	public void setDocDb(String docDb) {
		this.docDb = docDb;
	}

	public String getDocCodDelegante() {
		return docCodDelegante;
	}

	public void setDocCodDelegante(String docCodDelegante) {
		this.docCodDelegante = docCodDelegante;
	}

	public String getTitlesQuery() {
		return titlesQuery;
	}

	public void setTitlesQuery(String titlesQuery) {
		this.titlesQuery = titlesQuery;
	}

	public String getTitlesOrd() {
		return titlesOrd;
	}

	public void setTitlesOrd(String titlesOrd) {
		this.titlesOrd = titlesOrd;
	}

	public String getTitlesVerbo() {
		return titlesVerbo;
	}

	public void setTitlesVerbo(String titlesVerbo) {
		this.titlesVerbo = titlesVerbo;
	}

	public String getTitlesDb() {
		return titlesDb;
	}

	public void setTitlesDb(String titlesDb) {
		this.titlesDb = titlesDb;
	}

	public boolean isHide_left_column() {
		return hide_left_column;
	}

	public void setHide_left_column(boolean hide_left_column) {
		this.hide_left_column = hide_left_column;
	}

	public List<FascicoloSpecialeInfo> getFascicoli_speciali() {
		return fascicoli_speciali;
	}

	public void setFascicoli_speciali(List<FascicoloSpecialeInfo> fascicoli_speciali) {
		this.fascicoli_speciali = fascicoli_speciali;
	}

	public HomeContent getHomeContent() {
		return homeContent;
	}

	public void setHomeContent(HomeContent content) {
		this.homeContent = content;
	}

	public String getDadxwFileIdsAttached() {
		return dadxwFileIdsAttached;
	}

	public void setDadxwFileIdsAttached(String dadxwFileIdsAttached) {
		this.dadxwFileIdsAttached = dadxwFileIdsAttached;
	}

	public String getDadxwFileTitlesAttached() {
		return dadxwFileTitlesAttached;
	}

	public void setDadxwFileTitlesAttached(String dadxwFileTitlesAttached) {
		this.dadxwFileTitlesAttached = dadxwFileTitlesAttached;
	}

	public Map<String, String> getLabelsVisibilita() {
		return labelsVisibilita;
	}

	public void setLabelsVisibilita(Map<String, String> labelsVisibilita) {
		this.labelsVisibilita = labelsVisibilita;
	}

	public Map<String, String> getTipiRaccoglitoreIndice() {
		return tipiRaccoglitoreIndice;
	}

	public void setTipiRaccoglitoreIndice(Map<String, String> tipiRaccoglitoreIndice) {
		this.tipiRaccoglitoreIndice = tipiRaccoglitoreIndice;
	}

	public boolean isEnableIW() {
		return enableIW;
	}

	public void setEnableIW(boolean enableIW) {
		this.enableIW = enableIW;
	}

	public boolean isShowVaschetteResponsive() {
		return showVaschetteResponsive;
	}

	public void setShowVaschetteResponsive(boolean showVaschetteResponsive) {
		this.showVaschetteResponsive = showVaschetteResponsive;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(Document dom) {
		xml = dom.asXML();

		this.estremi = this.formsAdapter.checkBooleanFunzionalitaDisponibile("soloEstremi", false);
		this.annoProt = XMLUtil.parseAttribute(dom, "response/@currYear");
		
		// mbernardini 05/12/2018 : lettura di tutte le societa' associate all'utente corrente (memorizzazione in sessione)
		if (formsAdapter.checkBooleanFunzionalitaDisponibile("multiSocieta", false))
			MultiSocietaMap.getInstance().init(dom);

		// caricamento di eventuali fascicoli speciali da gestire
		this.fascicoli_speciali = XMLUtil.parseSetOfElement(dom, "/response/fascicolo_speciale_info", new FascicoloSpecialeInfo() );

		// caricamento del contenuto dell'homepage per l'utente
		this.homeContent.init(XMLUtil.createDocument(dom, "/response/homeContent"));

		// mbernardini 13/10/2014 - caricamento etichette custom per visibilita'
		DigitVisibilitaUtil digitVisibilitaUtil = new DigitVisibilitaUtil(XMLUtil.parseStrictAttribute(dom, "/response/@dicitVis"));
		labelsVisibilita = digitVisibilitaUtil.getDigitSingolare();

		// gestione delle vaschette utente (scrivania personale, documenti/archivio, vaschette di ufficio)
		initVaschetteHome(dom);

		String enableIWattribute = XMLUtil.parseStrictAttribute(dom, "/response/@enableIW");
		if (enableIWattribute.equals(""))
			this.enableIW = StringUtil.booleanValue(this.formsAdapter.isEnabledIWX());
		else
			this.enableIW = StringUtil.booleanValue(enableIWattribute);

		String titlesMode = XMLUtil.parseStrictAttribute(dom, "/response/titlesMode/@val");
		if (!titlesMode.isEmpty())
			setSessionAttribute("titlesmode", titlesMode);

		// tiommi 17/10/2017 : possibilità di configurare la barra laterale chiusa all'avvio
		this.hide_left_column = this.formsAdapter.checkBooleanFunzionalitaDisponibile("hideLeftColumnByDefault", false);

		// gestione dei raccoglitori custom di tipo indice
		initTipiRaccoglitoriIndice(dom);

		// mbernardini 16/12/2016 : gestione del formato della classificazione
		ClassifFormatManager.getInstance().setClassifFormat(XMLUtil.parseStrictAttribute(dom, "/response/@classifFormat"));
	}

	/**
	 * recupero delle tipologie di raccoglitori custom di tipo indice
	 * @param dom
	 */
	private void initTipiRaccoglitoriIndice(Document dom) {
		List<?> listatipi = dom.selectNodes("/response/raccoglitoriIndice/raccoglitore");
		if (listatipi != null && listatipi.size() > 0) {
			for (int i=0; i<listatipi.size(); i++) {
				Element el = (Element) listatipi.get(i);
				String cod = el.attributeValue("cod", null);
				String descr = el.getTextTrim();
				if (cod != null && !cod.isEmpty() && descr != null && !descr.isEmpty())
					tipiRaccoglitoreIndice.put(cod, descr);
			}
		}
	}

	/**
	 * inizializzazione delle vaschette utente
	 * @param dom
	 */
	@SuppressWarnings("unchecked")
	private void initVaschetteHome(Document dom) {
		this.vaschette = new ArrayList<Vaschetta>();
		this.vaschetteFatture = new ArrayList<Vaschetta>();
		this.vaschetteCustom = new ArrayList<Vaschetta>();
		this.vaschetteAlboExt = new ArrayList<Vaschetta>();
		this.vaschettePecManagerArchiviatoRigettato = new ArrayList<Vaschetta>();
		this.vaschettePecManager = new ArrayList<Vaschetta>();
		this.vaschetteRuoli = new ArrayList<Vaschetta>();
		this.setVaschetteDeleghe(new ArrayList<VaschettaDelega>());

		List<?> listavaschette = XMLUtil.parseSetOfElement(dom, "//ricerca/node()", new Vaschetta());
		for (int i=0; i< listavaschette.size(); i++) {
			Vaschetta vaschetta = (Vaschetta) listavaschette.get(i);

			if (vaschetta.getNome().startsWith("doc_FTRAPAP") || vaschetta.getNome().startsWith("doc_FTRAPAA")) // vaschette su fatturePA attive o passive
				this.vaschetteFatture.add(vaschetta);
			else if (vaschetta != null && vaschetta.getTipo() != null && vaschetta.getTipo().startsWith("custom_")) // vaschette custom definite dall'utente
				this.vaschetteCustom.add(vaschetta);
			else if (vaschetta.getNome().startsWith("alboext_"))
				this.vaschetteAlboExt.add(vaschetta);
			else if (vaschetta.getNome().startsWith("split_ruoli_")) // mbernardini 22/06/2016 : suddivisione della vaschetta 'Ruoli' per ogni ruolo assegnato all'operatore
				this.vaschetteRuoli.add(vaschetta);
			else if (vaschetta.getNome().startsWith("pecmanager_archiviato") || vaschetta.getNome().startsWith("pecmanager_rigettato")) { // dpranteda 27/11/2019 : PEC MANAGER
				this.vaschettePecManagerArchiviatoRigettato.add(vaschetta);
				if(Integer.valueOf(vaschetta.getNum()) > 0)
					showVaschettaPecManagerArchiviatoRigettato = true;
			}
			else if (vaschetta.getNome().startsWith("pecmanager_")) // dpranteda 27/11/2019 : PEC MANAGER
				this.vaschettePecManager.add(vaschetta);
			else// vaschette standard (caso di default)
				this.vaschette.add(vaschetta);
		}

		//fcappelli 19/05/2014 - caricamento export personalizzati
		this.esportazioni = XMLUtil.parseSetOfElement(dom, "//exports/node()", new ExportPersonalizzato());

		//tiommi 15/09/2017 - caricamento delle deleghe disponibili
		this.setVaschetteDeleghe(XMLUtil.parseSetOfElement(dom, "//deleghe/selezionabili/delega", new VaschettaDelega()));

		// abilitazione del cestino per cancellazione logica di documenti
		this.abilitaCestino = StringUtil.booleanValue(XMLUtil.parseStrictAttribute(dom, "/response/funzionalita_disponibili/@abilitaCestino"));

		if (this.vaschetteCustom.size() > 0
				|| this.esportazioni.size() > 0
				|| this.formsAdapter.checkBooleanFunzionalitaDisponibile("abilitaeXtraWayWorkflow", false)
				|| this.vaschetteAlboExt.size() > 0
				|| this.abilitaCestino
				|| this.getFormsAdapter().checkBooleanFunzionalitaDisponibile("verificaVirus", false))
			this.scrivaniaPersonaleEnabled = true;
		else
			this.scrivaniaPersonaleEnabled = false;

		this.ultimo_aggiornamento_vaschette = XMLUtil.parseAttribute(dom, "funzionalita_disponibili/@ultimoAggiornamentoVaschette");

		this.uor_select = XMLUtil.parseSetOfElement(dom, "//uor_select/option", new Option() );
		if (this.uor_select != null && this.uor_select.size() > 0) {
			for (int i=0; i<this.uor_select.size(); i++) {
				if (this.uor_select.get(i).getSelected() != null && this.uor_select.get(i).getSelected().equals("selected"))
					this.selected_uor = this.uor_select.get(i).getValue();
			}
		}
	}

	@Override
	public String queryPlain() throws Exception {
		return null;
	}

	public String search() throws Exception {
		try {
			this.query = ""; // Azzero il valore di query in caso di search (caso di precedente query che aveva restituito 'nessun risultato')

			// Imposto a vuoto il selId visto che potrebbe essere stato valorizzato
			// attraverso le vaschette
			formsAdapter.getDefaultForm().addParam("selid", "");

			if (!checkFields()) return null;

			if (this.searchTerms.length() == 0 && this.numProt.length() == 0) {
				// Ricerca troppo ampia... restituisco un messaggio di avviso
				handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(I18N.mrs("dw4.ambito_di_ricerca_troppo_ampio"), this.query, ErrormsgFormsAdapter.INFO));
				return null;
			}

			// costruzione della query globale
			this.query = QueryUtil.createGlobalQuery(searchTerms, estremi, annoProt, numProt);

			// Imposto l'ordinamento dei risultati
			String ord = "XML(xpart:/doc/@data_prot:d),XML(xpart:/doc/@num_prot),XML(xpart:/doc/repertorio/@numero)";
			this.formsAdapter.getDefaultForm().addParam("qord", ord); // TODO Andrebbe specificato all'interno di un file di properties

			XMLDocumento response = this._queryPlain(this.query, "", "");
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}

			this.xml = response.asXML();
			clearForm();

			Element root = response.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> tit = root.selectNodes("//titolo");
			if (null != tit && !tit.isEmpty()){
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);
				return "showtitles@docway";
			}
			else {
				if (response.getAttributeValue("//response/@verbo", "").equals("showdoc")) {
					return buildSpecificShowdocPageAndReturnNavigationRule(response.getRootElement().attributeValue("dbTable"), response);
				} else {
//					return buildTitlePageAndReturnNavigationRule(response);
					return null;
				}
			}
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String search(XMLDocumento response) throws Exception {
		try {
			this.xml = response.asXML();
			clearForm();

			Element root = response.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> tit = root.selectNodes("//titolo");
			if(null != tit && !tit.isEmpty() && response.getAttributeValue("/response/@verbo", "").equals("showtitles") && response.getAttributeValue("/response/@dbTable", "").equals("@fascicolo")){
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());
				titles.setPopupPage(isPopupPage());
				setSessionAttribute("docwayTitles", titles);
				return "showtitles@fascicolo";
			}
			else if (null != tit && !tit.isEmpty()){
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);
				return "showtitles@docway";
			}
			else {
				if (response.getAttributeValue("//response/@verbo", "").equals("showdoc")) {
					return buildSpecificShowdocPageAndReturnNavigationRule(response.getRootElement().attributeValue("dbTable"), response);
				} else {
//					return buildTitlePageAndReturnNavigationRule(response);
					return null;
				}
			}
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}
	/**
	 * Verifico se all'interno dei campi di tipo numero sono stati impostati dei valori corretti
	 * @return true se anche un solo campo non è compilato correttamente, false altrimenti
	 */
	public boolean checkFields(){
		boolean result = true;

		if (annoProt != null && annoProt.length() > 0) {
			if (!StringUtil.isNumber(annoProt)) {
				this.setErrorMessage("templateForm:searchAnnoProt", I18N.mrs("dw4.inserire_un_valore_numerico_nel_campo") + " '" + I18N.mrs("dw4.anno_protocollo") + "' ");
				result = false;
			}
		}
		if (numProt != null && numProt.length() > 0) {
			if (!StringUtil.isNumber(numProt)) {
				this.setErrorMessage("templateForm:searchNumProt", I18N.mrs("dw4.inserire_un_valore_numerico_nel_campo") + " '" + I18N.mrs("dw4.numero_protocollo") + "' ");
				result = false;
			}
		}
		return result;
	}

	/**
	 * Selezione di un ufficio da select delle vaschette
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String changeUORTendina() throws Exception {
		try {
			if (selected_uor != null && !(selected_uor.equals(""))) {
				if (selected_uor.equals("searchUOR")) {
					formsAdapter.showAllUOR();
					XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
					if (handleErrorResponse(response)) {
						formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					}

					uor_complete_select = XMLUtil.parseSetOfElement(response.getDocument(), "//uor_select/option", new Option() );
					if (uor_complete_select != null && uor_complete_select.size() > 0) {
						Option empty = new Option();
						empty.setLabel("");
						empty.setValue("searchUOR");
						empty.setSelected("true");
						uor_complete_select.add(empty);
					}
					setUor_popup_active(true);

					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}
				else {
					if (selected_uor.indexOf("##") == 0) {
						// selezione UOR da popup

						setUor_popup_active(false);
						formsAdapter.selectUORTendina(selected_uor, uor_complete_select);
						XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
						if (handleErrorResponse(response)) {
							formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
							return null;
						}

						formsAdapter.fillFormsFromResponse(response);
						init(response.getDocument());
					}
					else {
						formsAdapter.changeUORTendina(selected_uor);

						XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
						if (handleErrorResponse(response)) {
							formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
							return null;
						}

						formsAdapter.fillFormsFromResponse(response);
						init(response.getDocument());
					}
				}
			}

			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * Chiusura del popup di selezione UOR (vaschette DocWay)
	 * @return
	 * @throws Exception
	 */
	public String closeUORSelection() throws Exception {
		setUor_popup_active(false);
		return null;
	}

	/**
	 * Refresh delle vaschette di DocWay (sia personali che di ufficio)
	 * @return
	 * @throws Exception
	 */
	public String refreshVaschette() throws Exception {
		try {
			formsAdapter.refreshVaschette();

			XMLDocumento response = formsAdapter.getIndexForm().executePOST(getUserBean());
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			}
			init(response.getDocument());

			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String clearForm() {
		this.numProt = "";
		this.searchTerms = "";
		this.estremi = this.formsAdapter.checkBooleanFunzionalitaDisponibile("soloEstremi", false);
		this.query = "";

		return null;
	}

	public void setUor_select(List<Option> uor_select) {
		this.uor_select = uor_select;
	}

	public List<Option> getUor_select() {
		return uor_select;
	}

	public String getNumProt() {
		return numProt;
	}

	public void setNumProt(String numProt) {
		this.numProt = numProt;
	}

	public String getAnnoProt() {
		if (annoProt == null || annoProt.length() == 0)
			return DateUtil.getCurrentYear()+"";
		else
			return annoProt;
	}

	public void setAnnoProt(String annoProt) {
		this.annoProt = annoProt;
	}

	public String getSearchTerms() {
		return searchTerms;
	}

	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setEstremi(boolean estremi) {
		this.estremi = estremi;
	}

	public boolean isEstremi() {
		return estremi;
	}

	/**
	 * caricamento dei titoli relativi a vaschette custom dell'utente (da lista ui:repeat)
	 * @return
	 * @throws Exception
	 */
	public String gotoCustomInGestione() throws Exception {
		Vaschetta vaschetta = (Vaschetta)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("vaschetta");
		return gotoCustomInGestione(vaschetta);
	}

	/**
	 * caricamento dei titoli relativi a vaschette custom dell'utente
	 * @return
	 * @throws Exception
	 */
	private String gotoCustomInGestione(Vaschetta vaschetta) throws Exception {
		try {
			if (formsAdapter.checkBooleanFunzionalitaDisponibile("eliminaFrequenze", false) || (vaschetta.getNum().length() == 0 || (!vaschetta.getNum().equals("-1") && !vaschetta.getNum().equals("0")))) {
				formsAdapter.gotoDocsInGestione(vaschetta.getSelid());
				formsAdapter.getDefaultForm().addParam("dbTable", "");

				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
				if (handleErrorResponse(response)) {
					showErrorMessageRefreshVaschette();
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);

				String page = "showtitles@docway";
				if (vaschetta.getTipo() != null) {
					if (vaschetta.getTipo().equals("custom_fasc"))
						page = "showtitles@fascicolo";
					else if (vaschetta.getTipo().equals("custom_rac"))
						page = "showtitles@raccoglitore";
				}

				return page;
			}
			else
				return showMessageEmptyVaschetta();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String gotoCustomEsportazione() throws Exception {
		try {
			ExportPersonalizzato export = (ExportPersonalizzato)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("esportazione");
			formsAdapter.gotoDocsInGestione(export.getSelid());
			formsAdapter.getDefaultForm().addParam("dbTable", "");

			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
			if (handleErrorResponse(response)) {
				showErrorMessageRefreshVaschette();
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}
			DocWayTitles titles = new DocWayTitles();
			titles.getFormsAdapter().fillFormsFromResponse(response);

			titles.init(response.getDocument());

			setSessionAttribute("docwayExportPersonalizzato", export);
			setSessionAttribute("docwayTitles", titles);

			String page = "showtitles@docway";
			if (export.getKey() != null) {
				if (export.getKey().equals("custom_fasc"))
					page = "showtitles@fascicolo";
				else if (export.getKey().equals("custom_rac"))
					page = "showtitles@raccoglitore";
			}

			return page;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String gotoDocsInGestione() throws Exception {
		try {
			Vaschetta vaschetta = (Vaschetta)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("vaschetta");
			if (formsAdapter.checkBooleanFunzionalitaDisponibile("eliminaFrequenze", false) || (vaschetta.getNum().length() == 0 || (!vaschetta.getNum().equals("-1") && !vaschetta.getNum().equals("0")))) {
				formsAdapter.gotoDocsInGestione(vaschetta.getSelid());
				formsAdapter.getDefaultForm().addParam("dbTable", "");

				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
				if (handleErrorResponse(response)) {
					showErrorMessageRefreshVaschette();
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);

				String page = "showtitles@docway";
				if (vaschetta.getTipo() != null) {
					if (vaschetta.getTipo().equals("custom_fasc"))
						page = "showtitles@fascicolo";
					else if (vaschetta.getTipo().equals("custom_rac"))
						page = "showtitles@raccoglitore";
				}
				return page;
			}
			else
				return showMessageEmptyVaschetta();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String gotoFascInGestione() throws Exception {
		try {
			Vaschetta vaschetta = (Vaschetta)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("vaschetta");
			if (formsAdapter.checkBooleanFunzionalitaDisponibile("eliminaFrequenze", false) || (vaschetta.getNum().length() == 0 || (!vaschetta.getNum().equals("-1") && !vaschetta.getNum().equals("0")))) {
				formsAdapter.gotoFascInGestione(vaschetta.getSelid());
				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
				if (handleErrorResponse(response)) {
					showErrorMessageRefreshVaschette();
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);
				return "showtitles@fascicolo";
			}
			else
				return showMessageEmptyVaschetta();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	public String gotoRacInGestione() throws Exception {
		try {
			Vaschetta vaschetta = (Vaschetta)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("vaschetta");
			if (formsAdapter.checkBooleanFunzionalitaDisponibile("eliminaFrequenze", false) || (vaschetta.getNum().length() == 0 || (!vaschetta.getNum().equals("-1") && !vaschetta.getNum().equals("0")))) {
				formsAdapter.gotoRacInGestione(vaschetta.getSelid());
				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
				if (handleErrorResponse(response)) {
					showErrorMessageRefreshVaschette();
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}
				DocWayTitles titles = new DocWayTitles();
				titles.getFormsAdapter().fillFormsFromResponse(response);

				titles.init(response.getDocument());

				setSessionAttribute("docwayTitles", titles);
				return "showtitles@raccoglitore";
			}
			else
				return showMessageEmptyVaschetta();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * apertura di un popup di messaggio di vaschetta vuota in caso di click su
	 * vaschetta priva di record
	 *
	 * @return
	 * @throws Exception
	 */
	private String showMessageEmptyVaschetta() throws Exception {
		Msg message = new Msg();
		message.setActive(true);
		message.setTitle(I18N.mrs("dw4.info"));
		message.setType(Const.MSG_LEVEL_INFO);
		message.setMessage(I18N.mrs("dw4.la_vaschetta_risulta_essere_vuota"));

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("msg", message);

		return null;
	}

	/**
	 * personalizzazione del popup di errore in caso di caricamento di una selezione di documenti (o fascicoli o raccoglitori) da
	 * vaschette della home page di DocWay
	 * @throws Exception
	 */
	private void showErrorMessageRefreshVaschette() throws Exception {
		Errormsg errormsg = (Errormsg) getSessionAttribute("errormsg");
		if (errormsg != null && errormsg.getErrore() != null
				&& errormsg.getErrore().getExtra() != null) {

			String message = errormsg.getErrore().getErrtype();
			if (message == null)
				message = "";
			message += "<br/><strong>" + I18N.mrs("dw4.si_consiglia_di_aggiornare_le_vaschette_della_colonna_di_sinitra_e_ritentare_il_caricamento_della_selezione") + "</strong>";
			errormsg.getErrore().setErrtype(message);
			errormsg.getErrore().setLevel(Const.MSG_LEVEL_ERROR);
			setSessionAttribute("errormsg", errormsg);
		}
	}

	public String workflowAttivi() throws Exception{
		formsAdapter.workflowAttivi(AppUtil.getXdocwayprocDbName(getFormsAdapter().getDb()));
		XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
		if (handleErrorResponse(response)) {
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
		//TODO: da completare
		return null;
	}

	/**
	 * Caricamento di un documento da URL (prettyFaces)
	 *
	 * @return
	 * @throws Exception
	 */
	public String loadDoc() throws Exception {
		try {
			if (docAlias != null && !docAlias.equals("")
					&& docValue != null && !docValue.equals("")) {
				String q = "";

				// verifica della presenza di alias multipli separati da virgola (,)
				String[] aliases = docAlias.split(",");
				if (aliases != null && aliases.length > 0) {
					for (int i=0; i<aliases.length; i++) {
						if (aliases[i] != null && !aliases[i].equals("")) {
							if (!q.equals("")) // la ricerca su alias multipli viene fatta in OR
								q = q + " OR ";
							q = q + "([" + aliases[i] + "]=\"" + docValue + "\")";
						}
					}
				}

				// nel caso in cui sia stato specificato un database tramite
				// prettyfaces si aggiorna il formsAdapter
				if (docDb != null && !docDb.equals(""))
					formsAdapter.getDefaultForm().addParam("db", docDb);
				// tiommi 06/10/2017 - deleghe
				// nel caso in cui sia stato specificato una matricola di un delegato tramite URL (prettyfaces) accedo in delega di quell'utente
				// evito se sono già loggato con la delega giusta
				// gestisco anche il caso in cui sono in delega, ma devo visualizzare un doc dell'utente (ripristino utente)
				String codDelegato;
				String loginDelegato;
				String userInfo;
				boolean inDelega;
				if (getDelegatoBean() != null) {
					loginDelegato = getDelegatoBean().getLogin();
					codDelegato = getDelegatoBean().getMatricola();
					userInfo = getDelegatoBean().getUserInfo();
					inDelega = true;
				}
				else {
					loginDelegato = getUserBean().getLogin();
					codDelegato = getUserBean().getMatricola();
					userInfo = getUserBean().getUserInfo();
					inDelega = false;
				}
				if (docCodDelegante != null && !docCodDelegante.isEmpty() && !docCodDelegante.equals(getUserBean().getMatricola())) {
					//controllo se posso entrare in delega ed effettuo lo switch degli utenti
					String loginDelegante = getLoginDelegante(docCodDelegante, codDelegato);
					if (loginDelegante == null) return null;
					loginInDelega(docCodDelegante, loginDelegante);
					
					formsAdapter.getDefaultForm().addParam("matricola", docCodDelegante);
					formsAdapter.getDefaultForm().addParam("delegato", userInfo);
					formsAdapter.getDefaultForm().addParam("delegatoLogin", loginDelegato);
					formsAdapter.getDefaultForm().addParam("delegatoMatricola", codDelegato);
				}
				else if ((docCodDelegante == null || docCodDelegante.isEmpty()) && inDelega) {
					//nessun controllo da fare e ripristino il vecchio utente
					logOutDelega(true);
					
					formsAdapter.getDefaultForm().addParam("matricola", codDelegato);
					formsAdapter.getDefaultForm().addParam("delegato", "");
					formsAdapter.getDefaultForm().addParam("delegatoLogin", "");
					formsAdapter.getDefaultForm().addParam("delegatoMatricola", "");
				}

				//nel caso sia aperto il pannello modale del workflow, chiudilo.
				DocWayDocExecuteTask execTask = (DocWayDocExecuteTask) getSessionAttribute("execTask");
				if (execTask != null)
					execTask.closeEditTask();

				formsAdapter.getDefaultForm().addParam("auditVisualizzazione", "true");

				return this.queryPlain(q);
			}
			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	// cambia l'utente con il delegato prima di eseguire uno showdoc se specificato da url o viceversa se in delega e non specificato da url.
	private String getLoginDelegante(String codDelegante, String codDelegato) throws Exception {
		getFormsAdapter().checkDelegaAndGetLogin(codDelegante, codDelegato);
		XMLDocumento response = getFormsAdapter().getDefaultForm().executePOST(getUserBean());
		String loginDelegante = response.getAttributeValue("//response/@loginDelegante", "");
		if (handleErrorResponse(response)) {
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
		if (loginDelegante.isEmpty()) {
			throw new Exception("Errore nel recupero della login del delegante");
		}
		return loginDelegante;
	}

	//aggiorna i form adapter dopo la login/logout del delegante (per il bean non ancora inizializzato che gestisce la query)
	private void updateFormAdapterDelega(String matricola, String userInfo) {
		formsAdapter.getDefaultForm().addParam("matricola", matricola);
		formsAdapter.getDefaultForm().addParam("delegato", userInfo);
	}

	/**
	 * Caricamento di una lista documenti in base ad un URL con SELID (prettyFaces)
	 *
	 * @return
	 * @throws Exception
	 */
	public String loadTitles() throws Exception {
		try {
			if (titlesQuery != null && !titlesQuery.equals("")) {
				formsAdapter.loadTitlesFromQuery(titlesQuery, titlesOrd, titlesVerbo, titlesDb);
				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
				if (handleErrorResponse(response)) {
					getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
					return null;
				}

				String verbo = response.getAttributeValue("//response/@verbo", "");
				if (verbo.equals("resoconto_doc_assegnati")) {
					// richiesta di resoconto doc assegnati

					ShowdocNotifDiff showdocNotifDiff = new ShowdocNotifDiff();
					showdocNotifDiff.getFormsAdapter().fillFormsFromResponse(response);
					showdocNotifDiff.init(response.getDocument());
					// showdocNotifDiff.setShowSxCol(false);

					setSessionAttribute("showdocNotifDiff", showdocNotifDiff);
					return "showdoc@notif_diff";

				}
				if (verbo.equals("showdoc")) {
					// restituito un solo risultato, redirect a showdoc

					return buildSpecificShowdocPageAndReturnNavigationRule(response.getRootElement().attributeValue("dbTable"), response);

				} else {
					// restituiti piu' risultati redirect a showtitles

					DocWayTitles titles = new DocWayTitles();
					titles.getFormsAdapter().fillFormsFromResponse(response);
					titles.init(response.getDocument());

					setSessionAttribute("docwayTitles", titles);
					return "showtitles";

				}
			}

			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * Nasconde la colonna di sinistra di Docway (vaschette)
	 * @return
	 */
	public String hideLeftColumn() {
		setHide_left_column(true);
		return null;
	}

	/**
	 * Mostra la colonna di sinistra di Docway (vaschette)
	 * @return
	 */
	public String showLeftColumn() {
		setHide_left_column(false);
		return null;
	}

	/**
	 * Caricamento della pagina di ricerca su un fascicolo speciale (es. fascicolo del personale)
	 *
	 * @return
	 * @throws Exception
	 */
	public String gotoTableQFascicoloSpeciale() throws Exception {
		try {
			String id_fascicolo_speciale = "";
			FascicoloSpecialeInfo fascSpeciale = (FascicoloSpecialeInfo) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("fascSpeciale");
			if (fascSpeciale != null) {
				id_fascicolo_speciale = fascSpeciale.getId();
			}
			else {
				Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				id_fascicolo_speciale = params.get("idFascSpeciale");
			}

			formsAdapter.gotoTableQ("fascicolo@" + id_fascicolo_speciale, false);
			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
			if (handleErrorResponse(response)) {
				getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
				return null;
			}

			return buildSpecificQueryPageAndReturnNavigationRule("fascicolo", "", "", "@" + id_fascicolo_speciale, response, isPopupPage());
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * caricamento di un documento presente in home page dell'utente
	 *
	 * @return
	 * @throws Exception
	 */
	public String caricaDocHomeContent() throws Exception{
		try{
			Titolo titolo = (Titolo)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("title");
			int index = new Integer(titolo.getIndice()).intValue() - 1;
			String dbTable = titolo.getDbTable();
			String db = titolo.getDb();

			formsAdapter.mostraDocumento(homeContent.getSelid(), homeContent.getCount(), index, db, dbTable);
			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
			if (handleErrorResponse(response)) {
				getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
				return null;
			}

			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form

			dbTable = response.getAttributeValue("/response/@dbTable", "");
			if (dbTable.startsWith("@")) dbTable = dbTable.substring(1);

			if (dbTable.equals("differito"))
				dbTable = "arrivo";

			return this.buildSpecificShowdocPageAndReturnNavigationRule(dbTable, response);

		} catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * recupera la lunghezza massima (espressa in num di caratteri) da impostare
	 * per la visualizzazione dell'oggetto sul titolo
	 * @return
	 * @throws Exception
	 */
	public int getLunghezzaMaxOggetto() throws Exception {
		int lunghezzaOggetto = 0;
		try {
			lunghezzaOggetto = new Integer(DocWayProperties.readProperty(Const.PROPERTY_TITOLO_LUNGHEZZA_MAX_OGGETTO, "0")).intValue();
		}
		catch (Exception e) {
			Logger.warn(e.getMessage());
		}
		return lunghezzaOggetto;
	}

	/**
	 * recupera la lunghezza massima (espressa in num di caratteri) da impostare
	 * per la visualizzazione del soggetto sul titolo
	 * @return
	 * @throws Exception
	 */
	public int getLunghezzaMaxSoggetto() throws Exception {
		int lunghezzaSoggetto = 0;
		try {
			lunghezzaSoggetto = new Integer(DocWayProperties.readProperty(Const.PROPERTY_TITOLO_LUNGHEZZA_MAX_SOGGETTO, "0")).intValue();
		}
		catch (Exception e) {
			Logger.warn(e.getMessage());
		}
		return lunghezzaSoggetto;
	}

	/**
	 * apertura del cestino documenti (visualizzazione di documenti spostati nel cestino e non ancora eliminati
	 * dal processo di eliminazione fisica dei documenti)
	 * @return
	 * @throws Exception
	 */
	public String apriCestino() throws Exception {
		try {
			String queryCestino = "[doc_cestino]=\"si\"";

			String ord = "XML(xpart:/doc/storia/creazione/@data:d)";
			this.formsAdapter.getDefaultForm().addParam("qord", ord);

			XMLDocumento response = this._queryPlain(queryCestino, "", "");
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}

			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form

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

		} catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
	}

	public List<Vaschetta> getVaschetteAlboExt() {
		return vaschetteAlboExt;
	}

	public void setVaschetteAlboExt(List<Vaschetta> vaschetteAlboExt) {
		this.vaschetteAlboExt = vaschetteAlboExt;
	}
	
	public List<Vaschetta> getVaschettePecManagerArchiviatoRigettato() {
		return vaschettePecManagerArchiviatoRigettato;
	}

	public void setVaschettePecManagerArchiviatoRigettato(List<Vaschetta> vaschettePecManagerArchiviatoRigettato) {
		this.vaschettePecManagerArchiviatoRigettato = vaschettePecManagerArchiviatoRigettato;
	}
	
	public boolean isShowVaschettaPecManagerArchiviatoRigettato() {
		return showVaschettaPecManagerArchiviatoRigettato;
	}

	public void setShowVaschettaPecManagerArchiviatoRigettato(boolean showVaschettaPecManagerArchiviatoRigettato) {
		this.showVaschettaPecManagerArchiviatoRigettato = showVaschettaPecManagerArchiviatoRigettato;
	}

	public List<Vaschetta> getVaschettePecManager() {
		return vaschettePecManager;
	}

	public void setVaschettePecManager(List<Vaschetta> vaschettePecManager) {
		this.vaschettePecManager = vaschettePecManager;
	}
	

	/**
	 * caricamento da sessione (o eventualmente da file di properties) della modalita' di visualizzazione
	 * dei titoli
	 */
	public String getTitlesMode() {
		String mode = (String) getSessionAttribute("titlesmode");
		if (mode == null || (!mode.equals(DocWayTitles.MODE_LIST) && !mode.equals(DocWayTitles.MODE_TABLE)))
			mode = DocWayProperties.readProperty("titles.mode", DocWayTitles.MODE_LIST);
		return mode;
	}

	/**
	 * Creazione di un nuovo documento tramite DragAndDrop di files
	 * @return
	 * @throws Exception
	 */
	public String creaDocByDragAndDrop() throws Exception {
		try {

			if (dadxwFileIdsAttached != null && !dadxwFileIdsAttached.isEmpty() && dadxwFileTitlesAttached != null && !dadxwFileTitlesAttached.isEmpty()) {
				formsAdapter.creaDocByDragAndDrop(dadxwFileIdsAttached, dadxwFileTitlesAttached);
				XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());

				if (handleErrorResponse(response)) {
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
					return null;
				}

				AutoCloseMsg message = new AutoCloseMsg();
				message.setActive(true);
				message.init(response.getDocument());
				message.setLevel(Const.MSG_LEVEL_SUCCESS);
				setSessionAttribute("autoclosemsg", message);

				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			}

			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
	}

	/**
	 * indica se e' stata attivata o meno la visualizzazione espansa delle icone relative alle info su una lista titoli: Tutte le icone delle info visualizzate
	 * direttamente all'interno della riga relativa al titolo.
	 *
	 * @return true se le info devono essere espanse, false se devono essere raggruppate sotto un'unica icona e visualizzate sull'hover (default)
	 */
	public boolean isTitlesInfoExpanded() {
		if (DocWayProperties.readProperty("titles.info.expanded", "no").toLowerCase().equals("si"))
			return true;
		else
			return false;
	}

	/**
	 * @author tiommi 15/09/2017
	 *
	 * Metodo per loggare la persona interna che ha concesso la delega, mantendo comunque le informazioni del delegato
	 * (chi ha effettuato il log-in originariamente in sessione)
	 */
	public String selectDelega() throws Exception {

		//recupera le info del delegante
		VaschettaDelega delega = (VaschettaDelega)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("vaschettaDelega");
		String loginName = delega.getLoginName();
		String matricola = delega.getCodPersona();

		return loginInDelega(matricola, loginName);

	}

	/**
	 * @author tiommi 18/09/2017
	 *
	 * Metodo per reloggare la persona che ha sfruttato la delega fino a questo momento
	 * @throws Exception
	 */
	public String rilasciaDelega() throws Exception {
		return logOutDelega(true);
	}

	/**
	 * Metodo per mostrare/nascondere le vaschette durante la visualizzazione smartphone/tablet : max-width: 988px
	 */
	public void toggleVaschetteResponsive() {
		this.showVaschetteResponsive = !this.showVaschetteResponsive;
	}
	
}
