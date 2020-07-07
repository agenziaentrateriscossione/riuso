package it.tredi.dw4.docway.beans.dexia;

import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.beans.Errormsg;
import it.tredi.dw4.docway.beans.DocEditVarie;
import it.tredi.dw4.docway.model.Classif;
import it.tredi.dw4.docway.model.Extra;
import it.tredi.dw4.docway.model.dexia.UnitaRichiamata;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.utils.AppStringPreferenceUtil;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;

public class DocEditVarieNA extends DocEditVarie {

	public DocEditVarieNA() throws Exception {
		super();
	}

	@Override
	public void init(Document domDocumento) {
		super.init(domDocumento);
	}

	@Override
	public String saveDocument() throws Exception {
		try {
			//riempi i campi mancanti automaticamente
			Extra extra = getDoc().getExtra();
			extra.setNa_statoNorma("in lavorazione");

			Classif classif = new Classif();
			classif.setText("Nessuna classificazione");
			classif.setCod("0/0");
			getDoc().setClassif(classif);

			if (checkRequiredField()) return null;

			formsAdapter.getDefaultForm().addParams(getDoc().asFormAdapterParams("", false, true));
			XMLDocumento response = super._saveDocument("doc", "list_of_doc");

			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}

			ShowdocVarieNA showdocVarieNA = new ShowdocVarieNA();
			showdocVarieNA.getFormsAdapter().fillFormsFromResponse(response);
			showdocVarieNA.init(response.getDocument());
			showdocVarieNA.loadAspects("@varie", response, "showdoc");
			setSessionAttribute("showdocVarieNA", showdocVarieNA);

			return "dexia_showdoc@varie@NA@reload";
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
	}

	public String lookupUnitaRichiesta() throws Exception {
		UnitaRichiamata unitaRichiamata = (UnitaRichiamata) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("unita");
		int num = (getDoc().getExtra().getUnitaRichiamate().contains(unitaRichiamata)) ? getDoc().getExtra().getUnitaRichiamate().indexOf(unitaRichiamata): 0;
		String value = unitaRichiamata.getNome_uff();

//		// controllo su lunghezza minima del filtro di lookup su rif esterni
//		if (!checkUnitaLookup(value))
//			return null;

		String aliasName 	= "struint_nome"; //aliasName
		String aliasName1 	= ""; //aliasName1
		String titolo 		= "xml,/struttura_interna/nome"; //titolo
		String ord 			= "xml(xpart:/struttura_interna/nome)"; //ord
		String campiLookup	= ".extra.unitaRichiamate[" + num + "]..nome_uff=xml,/struttura_interna/nome " +
								" ; .extra.unitaRichiamate[" + num + "].@cod_uff=xml,/struttura_interna/@cod_uff";
		String campiClear 	= ".unitaRichiamate[" + num + "].nome_uff=xml,/struttura_interna/nome" +
							  " ; .extra.unitaRichiamate[" + num + "].@cod_uff=xml,/struttura_interna/@cod_uff";

		String db 			= getFormsAdapter().getDefaultForm().getParam("aclDb"); //db
		String xq			= ""; //extraQuery

		callLookupWithClearFields(getDoc(), aliasName, aliasName1, titolo, ord, campiLookup, campiClear, xq, db, "", value);

		return null;
	}

	public String clearLookupUnitaRichiesta() throws Exception {
		UnitaRichiamata unitaRichiamata = (UnitaRichiamata) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("unita");
		int num = (getDoc().getExtra().getUnitaRichiamate().contains(unitaRichiamata)) ? getDoc().getExtra().getUnitaRichiamate().indexOf(unitaRichiamata): 0;

		String campi = ".extra.unitaRichiamate[" + num + "].nome_uff=xml,/struttura_interna/nome" +
					   " ; .extra.unitaRichiamate[" + num + "].@cod_uff=xml,/struttura_interna/@cod_uff";
		return clearField(campi, getDoc());
	}

	/**
	 * Controllo dei campo obbligatori
	 *
	 * @return false se tutti i campo obbligatori sono stati compilati, true se anche un solo campo obbligatorio non e' compilato
	 */
	public boolean checkRequiredField() {
		boolean result = false;

		result = super.checkRequiredFieldCommon(false); // controlli comuni a tutte le tipologie di documenti

		// Controllo su campo Codice Norma
		if (getDoc().getExtra().getNa_codiceNorma() == null || getDoc().getExtra().getNa_codiceNorma().length() == 0) {
			this.setErrorMessage("templateForm:codiceNorma", I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dexia.codice_norma") + "'");
			result = true;
		}
		else {
			// Controllo se il valore impostato e' univoco
			boolean codiceUnivoco = true;
			try {
				codiceUnivoco = isCodiceNormaUnivoco();
			}
			catch (Throwable t) {
				Logger.error(t.getMessage(), t);
				codiceUnivoco = false;
			}
			if (!codiceUnivoco) {
				this.setErrorMessage("templateForm:codiceNorma", I18N.mrs("dexia.il_codice_della_norma_indicato_risulta_gia_registrato"));
				result = true;
			}
		}

		// Controllo su campo Release
		if (getDoc().getExtra().getNa_releaseNorma() == null || getDoc().getExtra().getNa_releaseNorma().length() == 0) {
			this.setErrorMessage("templateForm:releaseNorma", I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dw4.release") + "'");
			result = true;
		}
		else if (!getDoc().getExtra().getNa_releaseNorma().matches("(\\d+)\\.(\\d+)")) { // controllo sul formato del campo Release
			this.setErrorMessage("templateForm:releaseNorma", I18N.mrs("dexia.la_release_deve_essere_espressa_nel_formato_xx_xx"));
			result = true;
		}

		// 20150112 fcappelli - rimosso per richiesta cliente
		// Controllo su data del documento
		/*
		String dataProtDocFieldId = "dataEmanazioneDoc";
		if (getDoc().getExtra().getNa_dataEmanazioneNorma() == null || getDoc().getExtra().getNa_dataEmanazioneNorma().length() == 0) {
			this.setErrorMessage("templateForm:" + dataProtDocFieldId, I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dexia.data_emanazione") + "'");
			result = true;
		}
		else {
			// Controllo se il valore associato alla data e' corretto
			if (!DateUtil.isValidDate(getDoc().getExtra().getNa_dataEmanazioneNorma(), formatoData)) {
				this.setErrorMessage("templateForm:" + dataProtDocFieldId, I18N.mrs("acl.inserire_una_data_valida_nel_campo") + " '" + I18N.mrs("dexia.data_emanazione") + "': " + formatoData.toLowerCase());
				result = true;
			}
		}
		*/

		// Imposto lo scarto automatico se non impostato
		if (getDoc().getScarto() == null || getDoc().getScarto().length() == 0)
			getDoc().setScarto(AppStringPreferenceUtil.getAppStringPreference(this.getAppStringPreferences(), AppStringPreferenceUtil.decodeAppStringPreference("ScartoAutomatico")));

		// Controllo che l'RPA sia stato selezionato
		if (!getFormsAdapter().checkBooleanFunzionalitaDisponibile("docRPAEreditabile", false)) {
			if (getDoc().getAssegnazioneRPA() == null ||
					((getDoc().getAssegnazioneRPA().getNome_uff() == null || "".equals(getDoc().getAssegnazioneRPA().getNome_uff().trim())) &&
							(getDoc().getAssegnazioneRPA().getNome_persona() == null || "".equals(getDoc().getAssegnazioneRPA().getNome_persona().trim())))) {

				String[] fieldIds = { "templateForm:rpa_nome_uff_input", "templateForm:rpa_nome_persona_input", "templateForm:rpa_nome_ruolo_input" };
				this.setErrorMessage(fieldIds, I18N.mrs("dw4.occorre_valorizzare_il_campo_proprietario"));
				result = true;
			}
		}

		return result;
	}

	/**
	 * verifica di univocita' sul codice della norma
	 * @return
	 * @throws Exception
	 */
	public String verificaUnivocitaCodiceNorma() throws Exception {

		if (!isCodiceNormaUnivoco()) {
			// caricamento del dialog di errore
			this.setInputFieldNotValid("templateForm:codiceNorma");
		}

		return null;
	}

	/**
	 * verifica di univocita' sul codice della norma tramite chiamata al service
	 *
	 * @return true se il codice della norma e' univoco, false altrimenti
	 * @throws Exception
	 */
	private boolean isCodiceNormaUnivoco() throws Exception {
		boolean codiceUnivoco = false;
		try {
			if (getDoc().getExtra().getNa_codiceNorma() != null && getDoc().getExtra().getNa_codiceNorma().length() > 0) {
				String query = "([/doc/extra/NA_codiceNorma]=\"" + getDoc().getExtra().getNa_codiceNorma() + "\")";

				getFormsAdapter().verificaDuplicatiDoc(query);
				XMLDocumento response = getFormsAdapter().getIndexForm().executePOST(getUserBean());
				if (handleErrorResponse(response) ) {
					// Verifico se l'errore corrente si riferisce ad un esito di ricerca nullo. In caso contrario
					// mostro l'errore all'operatore
					HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
					Errormsg errormsg = (Errormsg) session.getAttribute("errormsg");

					if (!errormsg.getErrore().isUnexpected() && Const.RITORNO_ESITO_RICERCA_NULLO.equals(errormsg.getErrore().getErrtype().trim())) {
						session.removeAttribute("errormsg");
						codiceUnivoco = true;
					}
				}

				getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form
			}
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form

			codiceUnivoco = false;
		}
		return codiceUnivoco;
	}

//	private boolean checkUnitaLookup(String val) throws Exception {
//		while (val.indexOf("*") == 0)
//			val = val.substring(1);
//		if (val.length() < 2) {
//			Errormsg errormsg = new Errormsg();
//			errormsg.setActive(true);
//			Errore error = new Errore();
//			error.setLevel("warning");
//			error.setErrtype(I18N.mrs("dw4.ambito_di_ricerca_troppo_ampio_Si_prega_di_digitare_almento_2_caratteri"));
//			errormsg.setErrore(error);
//
//			setSessionAttribute("errormsg", errormsg);
//			return false;
//		}
//
//		return true;
//	}
}
