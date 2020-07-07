package it.tredi.dw4.docway.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.dom4j.Document;

import it.tredi.dw4.acl.model.ListaDistribuzioneAppartenenza;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.docway.doc.adapters.DocDocWayDocEditFormsAdapter;
import it.tredi.dw4.docway.model.Partenza;
import it.tredi.dw4.docway.model.RifEsterno;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.model.Titolo;
import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.AppStringPreferenceUtil;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.DateUtil;
import it.tredi.dw4.utils.XMLDocumento;

public class DocEditModifyPartenza extends DocEditDoc {
	private Partenza doc = new Partenza();
	
	private final String DEFAULT_PARTENZA_TITLE = "dw4.mod_partenza";
	private ListaDistribuzioneContainer listaDistribuzioneContainer = new ListaDistribuzioneContainer();

	public class ListaDistribuzioneContainer extends XmlEntity{
		// N.B. XmlEntity esteso solo per utilizzare metodi lookup
		
		/**
		 * Utilizzata per lookup lista distribuzione
		 */
		private ListaDistribuzioneAppartenenza listaDistribuzione;
		/**
		 * Placeholder per lookup 
		 */
		private List<RifEsterno> rif_esterni = new ArrayList<>();
		/**
		 * Elementi che verranno aggiunti post ricerca
		 */
		private List<RifEsterno> rifEsterniDaAggiungere = new ArrayList<>();
		
		public ListaDistribuzioneContainer() {
			listaDistribuzione = new ListaDistribuzioneAppartenenza();
		}

		public List<RifEsterno> getRifEsterniDaAggiungere() {
			return rifEsterniDaAggiungere;
		}
		public void setRifEsterniDaAggiungere(List<RifEsterno> rifEsterniDaAggiungere) {
			this.rifEsterniDaAggiungere = rifEsterniDaAggiungere;
		}
		public ListaDistribuzioneAppartenenza getListaDistribuzione() {
			return listaDistribuzione;
		}
		public void setListaDistribuzione(ListaDistribuzioneAppartenenza listaDistribuzione) {
			this.listaDistribuzione = listaDistribuzione;
		}
		public List<RifEsterno> getRif_esterni() {
			return rif_esterni;
		}
		public void setRif_esterni(List<RifEsterno> rif_esterni) {
			this.rif_esterni = rif_esterni;
		}
		@Override
		public XmlEntity init(Document dom) {
			return null;
		}
		@Override
		public Map<String, String> asFormAdapterParams(String prefix) {
			return null;
		}
		
	}

	public DocEditModifyPartenza() throws Exception {
		this.formsAdapter = new DocDocWayDocEditFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
	}
	
	@Override
	public boolean isDocEditModify() {
		return true;
	}
	
	@Override
	public void init(Document domDocumento) {
		this.doc = new Partenza();
		this.doc.init(domDocumento);
		
		// inizializzazione common per tutte le tipologie di documenti di DocWay
		initCommon(domDocumento);
		
		// Imposto il titolo della pagina di creazione del documento
		setInsPartenzaTitleByCodRepertorio();
	}
	
	/**
	 * Imposta il titolo della maschera di inserimento del documento
	 */
	private void setInsPartenzaTitleByCodRepertorio() {
		if (doceditRep && descrizioneRepertorio != null && descrizioneRepertorio.length() > 0)
			docEditTitle = descrizioneRepertorio + " - " + (getFormsAdapter().checkBooleanFunzionalitaDisponibile("trasformaByDocEdit", false) ? I18N.mrs("dw4.trasformazioneRepertorio") : I18N.mrs("acl.modify"));
		else
			docEditTitle = I18N.mrs(DEFAULT_PARTENZA_TITLE);
	}

	@Override
	public boolean checkRequiredField() {
		return checkRequiredField(!doc.isHideDestinatari());
	}

	@Override
	public String saveDocument() throws Exception {
		try {
			if (checkRequiredField()) return null;
			
			// personalizzazione del salvataggio per il repertorio
			boolean isRepertorio = false;
			if (doc.getRepertorio() != null && doc.getRepertorio().getCod() != null && doc.getRepertorio().getCod().length() > 0)
				isRepertorio = true;
			
			boolean rifEsterniModificabili = true;
			boolean abilitaModificaDatiDiProtocollo = formsAdapter.checkBooleanFunzionalitaDisponibile("abilitaModificaDatiDiProtocollo", false);
			boolean abilitaModificaMittenteConDiritto = formsAdapter.checkBooleanFunzionalitaDisponibile("abilitaModificaMittenteDestinatarioProtocolloConDiritto", false);
			boolean isProtocollato = !doc.isBozza() && doc.getNum_prot() != null && !doc.getNum_prot().isEmpty();
			if (!abilitaModificaDatiDiProtocollo && !abilitaModificaMittenteConDiritto && isProtocollato && copyIfNotStandardRep)
				rifEsterniModificabili = false;
			// controllo se i rif_esterni sono stati modificati nel protocollo
			if (abilitaModificaMittenteConDiritto && isProtocollato && isRifEsterniChanged()) {
				formsAdapter.addRifEsterniChangedInfos(null);
			}

			formsAdapter.getDefaultForm().addParams(this.doc.asFormAdapterParams("", true, rifEsterniModificabili, isRepertorio));
			XMLDocumento response = super._saveDocument("doc", "list_of_doc");
		
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}
			buildSpecificShowdocPageAndReturnNavigationRule("partenza", response);		
			return "showdoc@partenza@reload";
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;			
		}
	}
	
	public Partenza getDoc() {
		return doc;
	}

	public void setDoc(Partenza partenza) {
		this.doc = partenza;
	}
	
	/**
	 * Lookup su numero di raccomandata
	 * @throws Exception
	 */
	public String lookupNumRaccomandata() throws Exception {
		// TODO Da completare (diverso da arrivo perche' campo multiplo)
		return null;
	}
	
	/**
	 * Pulizia dei campi di lookup su numero di raccomandata
	 * @throws Exception
	 */
	public String clearLookupNumRaccomandata() throws Exception {
		// TODO Da completare (diverso da arrivo perche' campo multiplo)
		return null;
	}
	
	/**
	 * Verifica se esistono duplicati del documento in partenza che si sta 
	 * registrando
	 * 
	 * @throws Exception
	 */
	public String verificaDuplicatiDoc() throws Exception {
		return null;
	}	
	
	/**
	 * Eliminazione di un rif est al doc (destinatario del doc in partenza)
	 * @return
	 */
	public String deleteRifEst(){
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		getDoc().deleteRifEst(rifEsterno);
		return null;
	}

	/**
	 * Aggiunta di un rif est al doc (destinatario del doc in partenza)
	 * @return
	 */
	public String addRifEst(){
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		getDoc().addRifEst(rifEsterno);
		return null;
	}
	
	/**
	 * Lookup su destinatario del documento
	 * @throws Exception
	 */
	public String lookupDestinatario() throws Exception {
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		return this.lookupRifStrutEst((getDoc().getRif_esterni().contains(rifEsterno)) ? getDoc().getRif_esterni().indexOf(rifEsterno): 0);
	}
	
	/**
	 * Pulizia dei campi di lookup su destinatario del documento
	 * @throws Exception
	 */
	public String clearLookupDestinatario() throws Exception {
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		return this.clearLookupRifStrutEst((getDoc().getRif_esterni().contains(rifEsterno)) ? getDoc().getRif_esterni().indexOf(rifEsterno): 0);
	}
	
	/**
	 * Lookup su cortese attenzione del documento
	 * @throws Exception
	 */
	public String lookupCorteseAttenzione() throws Exception {
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		return this.lookupRifPersEst((getDoc().getRif_esterni().contains(rifEsterno)) ? getDoc().getRif_esterni().indexOf(rifEsterno): 0);
	}
	
	/**
	 * Pulizia dei campi di lookup su cortese attenzione del documento
	 * @throws Exception
	 */
	public String clearLookupCorteseAttenzione() throws Exception {
		RifEsterno rifEsterno = (RifEsterno) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("rifEsterno");
		return this.clearLookupRifPersEst((getDoc().getRif_esterni().contains(rifEsterno)) ? getDoc().getRif_esterni().indexOf(rifEsterno): 0);
	}
	
	/**
	 * Controllo dei campo obbligatori
	 * 
	 * @return false se tutti i campo obbligatori sono stati compilati, true se anche un solo campo obbligatorio non e' compilato
	 */
	public boolean checkRequiredField(boolean checkDestinatari) {
		String formatoData = Const.DEFAULT_DATE_FORMAT; // TODO Dovrebbe essere caricato dal file di properties dell'applicazione
		boolean result = false;
		
		result = super.checkRequiredFieldCommon(true); // controlli comuni a tutte le tipologie di documenti
		
		if (checkDestinatari) {
			// Controllo di obbligatorieta' su destinatari del documento
			boolean allDestinatariCC = true;
			int destinatari = this.getDoc().getRif_esterni().size();
			for(int i = 0; i < destinatari; i++) {
				RifEsterno rifEsterno = getDoc().getRif_esterni().get(i);
				if(!rifEsterno.isCopia_conoscenza())
					allDestinatariCC = false;
				
				if (rifEsterno.getNome() == null || rifEsterno.getNome().length() == 0) {
					String fieldId = "templateForm:docEditDestinatari:" + i + ":nomeDestinatario_input";
					if (!rifEsterno.isVincolato())
						fieldId = "templateForm:docEditDestinatari:" + i + ":nomeDestinatarioLibero";
					this.setErrorMessage(fieldId, I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dw4.destinatario") + "'");
					result = true;
				}
			}
			
			if(allDestinatariCC) {
				String fieldId = "templateForm:docEditDestinatari:#{indice.index}:ccCheck";
				this.setErrorMessage(fieldId, I18N.mrs("dw4.cc_destinatario"));
				result = true;
			}
		}
		
		// Imposto lo scarto automatico se non impostato
		if (getDoc().getScarto() == null || getDoc().getScarto().length() == 0)
			getDoc().setScarto(AppStringPreferenceUtil.getAppStringPreference(this.getAppStringPreferences(), AppStringPreferenceUtil.decodeAppStringPreference("ScartoAutomatico")));
		
		// controllo su tutti i campi data (verifica del formato)
		if (getDoc().getData_reale() != null && getDoc().getData_reale().length() > 0) {
			if (!DateUtil.isValidDate(getDoc().getData_reale(), formatoData)) {
				this.setErrorMessage("templateForm:dataRealeDoc", I18N.mrs("acl.inserire_una_data_valida_nel_campo") + " '" + I18N.mrs("dw4.data_doc") + "': " + formatoData.toLowerCase());
				result = true;
			}
			else {
				int dataReale = new Integer(DateUtil.formatDate2XW(getDoc().getData_reale(), formatoData)).intValue();
				
				if (getDoc().getData_prot() != null && !getDoc().getData_prot().equals("") && !getDoc().getData_prot().equals(".")) {
					// Controllo che la data specificata sia antecedente alla data di protocollo
					String dataProtDocumento = getDoc().getData_prot();
					if (!DateUtil.checkNumericDate(dataProtDocumento))
						dataProtDocumento = DateUtil.formatDate2XW(dataProtDocumento, formatoData);
					if (dataReale > new Integer(dataProtDocumento).intValue()) {
						this.setErrorMessage("templateForm:dataRealeDoc", I18N.mrs("dw4.la_data_del_documento_non_puo_essere_superiore_alla_data_di_protocollazione"));
						result = true;
					}
				}
				else {
					// Controllo che la data specificata sia antecedente alla data corerente
					int dataCur = new Integer(DateUtil.getCurrentDateNorm()).intValue();
					if (dataReale > dataCur) {
						this.setErrorMessage("templateForm:dataRealeDoc", I18N.mrs("dw4.la_data_del_documento_non_puo_essere_superiore_alla_data_odierna"));
						result = true;
					}
				}
			}
		}
				
		return result;
	}
	
	@Override
	public XmlEntity getModel() {
		return this.doc;
	}
	/**
	 *  Aggiunta azione da eseguire post confirm modale lookup
	 */
	protected void doActionOnConfirm(String alias, String confirmValue) throws Exception{
		if(alias!=null && !alias.isEmpty() ) {
			String codLista= getListaDistribuzioneContainer().getListaDistribuzione().getCod();
			String nomeLista = getListaDistribuzioneContainer().getListaDistribuzione().getNome();
			if(alias.equals("lista_dist_nome")) {
				this.lookupRifStrutEstFromLista(codLista, nomeLista);
			}
		}
		
	}

	protected void lookupPopupStatusAfterSearch(String alias, boolean lookupActive, int resultSize) {
	}
	/**
	 * Ricerca lookup su liste di distribuzione
	 * @return
	 * @throws Exception
	 */
	public String lookupListaDistribuzioneAppartenenza() throws Exception {
		String aliasName = "lista_dist_nome";
		String aliasName1 = "";
		String titolo = "xml,/lista_dist/nome - xml,/lista_dist/descrizione_ld";
		String xq = "";
		String db 			= getFormsAdapter().getDefaultForm().getParam("aclDb");
		String ord 			= "xml(xpart:/lista_dist/nome)";
		String campi 		= ".@cod=xml,/lista_dist/@nrecord ; .@nome=xml,/lista_dist/nome ; .@physDoc=xml,/lista_dist/@physDoc"; //campi
		String newRecord 	= ""; //newRecord 
		 
		String value = getListaDistribuzioneContainer().getListaDistribuzione().getNome();
		callLookupFromLista( getListaDistribuzioneContainer().getListaDistribuzione(), aliasName, aliasName1, titolo, ord, campi, campi, xq, db, newRecord, value, "", "", null, "Lookup Liste Distribuzione"); 
		return null;
	}
	/**
	 * Ricerca su strutture esterne e persone esterne contenute in una determinata lista di distribuzione
	 * @param codLista
	 * @param nomeLista
	 * @return
	 * @throws Exception
	 */
	public String lookupRifStrutEstFromLista(String codLista, String nomeLista) throws Exception {
		String value = codLista;
		getListaDistribuzioneContainer().getRif_esterni().clear();
		getListaDistribuzioneContainer().getRif_esterni().add(new RifEsterno());
		int num = getListaDistribuzioneContainer().getRif_esterni().size()-1;
		if(codLista==null || codLista.isEmpty()) {
			return null;
		}
		String aliasName 	= "persest_cod_listaDist,struest_cod_listaDist";
		String aliasName1 	= "";
		String titolo 		= "xml,/struttura_esterna/nome \"^ (~\" xml,/struttura_esterna/indirizzo/@comune \"~^)\" \"^ [csap: ~\" xml,/struttura_esterna/@cod_SAP \"~^]\" \"^ - P.IVA ~\" xml,/struttura_esterna/@partita_iva \"~^|\" \"^ - C.F. ~\" xml,/struttura_esterna/@codice_fiscale \"~^|\" xml,/persona_esterna/@cognome xml,/persona_esterna/@nome"; //titolo
		String ord="xml(xpart:/struttura_esterna/lista_dist_appartenenza/@cod),xml(xpart:/persona_esterna/lista_dist_appartenenza/@cod)(join)";
		String campiLookup	= ".rif_esterni.rif[" + num + "].nome=xml,/struttura_esterna/nome xml,/persona_esterna/@cognome xml,/persona_esterna/@nome" +
								" ; .rif_esterni.rif[" + num + "].indirizzo=xml,/struttura_esterna/indirizzo \"~-\" xml,/struttura_esterna/indirizzo/@cap xml,/struttura_esterna/indirizzo/@comune \"^ (~\" xml,/struttura_esterna/indirizzo/@prov \"~^)\" \" -~\" xml,/struttura_esterna/indirizzo/@nazione xml,/persona_esterna/recapito/indirizzo \"-~\" xml,/persona_esterna/recapito/indirizzo/@cap xml,/persona_esterna/recapito/indirizzo/@comune \"^ (~\" xml,/persona_esterna/recapito/indirizzo/@prov \"~^)\" \" -~\" xml,/persona_esterna/recapito/indirizzo/@nazione" +
								" ; .rif_esterni.rif[" + num + "].@cod=xml,/struttura_esterna/@cod_uff xml,/persona_esterna/@matricola" +
								" ; .rif_esterni.rif[" + num + "].@email_certificata=xml,/struttura_esterna/email_certificata/@addr  xml,/persona_esterna/recapito/email_certificata/@addr" +
								" ; .rif_esterni.rif[" + num + "].@email=xml,/struttura_esterna/email/@addr xml,/persona_esterna/recapito/email/@addr" +
								" ; .rif_esterni.rif[" + num + "].@tel=xml,/" +
								" ; .rif_esterni.rif[" + num + "].@fax=xml,/" +
								" ; .rif_esterni.rif[" + num + "].referente.@cod=xml,/" +
								" ; .rif_esterni.rif[" + num + "].referente.@nominativo=xml,/" +
								" ; telefono=xml,/struttura_esterna/telefono/@num \"~^!\" xml,/struttura_esterna/telefono/@tipo xml,/persona_esterna/recapito/telefono/@num \"^!\" xml,/persona_esterna/recapito/telefono/@tipo" +
								" ; lookup_xq_.rif_esterni.rif.referente.@nominativo=xml,/" +
								" ; .rif_esterni.rif[" + num + "].indirizzo_personale=xml,/persona_esterna/recapito_personale/indirizzo \"-~\" xml,/persona_esterna/recapito_personale/indirizzo/@cap xml,/persona_esterna/recapito_personale/indirizzo/@comune \"^ (~\" xml,/persona_esterna/recapito_personale/indirizzo/@prov \"~^)\" \" -~\" xml,/persona_esterna/recapito_personale/indirizzo/@nazione" +
								" ; telefono_personale=xml,/persona_esterna/recapito_personale/telefono/@num \"^!\" xml,/persona_esterna/recapito_personale/telefono/@tipo" +
								" ; .rif_esterni.rif[" + num + "].referente.@ruolo=xml,/" +
								" ; sx=xml,/" +
								" ; .rif_esterni.rif[" + num + "].@codice_fiscale=xml,//@codice_fiscale" +
								" ; .rif_esterni.rif[" + num + "].@cod_SAP=xml,/struttura_esterna/@cod_SAP" +
								" ; .rif_esterni.rif[" + num + "].@partita_iva=xml,//@partita_iva";
		String campiClear 	= ".rif_esterni.rif[" + num + "].nome=xml,/struttura_esterna/nome xml,/persona_esterna/@cognome xml,/persona_esterna/@nome";

		String db 			= getFormsAdapter().getDefaultForm().getParam("aclDb"); //db
		if (getFormsAdapter().checkBooleanFunzionalitaDisponibile("SAPLookup", false)) // TODO da verficare lookup su SAP
			db				= "generic-lookup-call;sap_lookup_mitt-dest_class";
 
		String newRecord 	= "/base/acl/engine/acl.jsp?db=" + getFormsAdapter().getDefaultForm().getParam("aclDb") + ";dbTable=struttura_esterna;fillField=struttura_esterna.nome;rightCode=ACL-6"; //newRecord
		String xq			= ""; //extraQuery
		if (getFormsAdapter().checkBooleanFunzionalitaDisponibile("acl_ext_aoo_restriction", false))
			xq				= "[struest_codammaoo]=" + getDoc().getCod_amm_aoo() + " OR [persest_codammaoo]=" + getDoc().getCod_amm_aoo();

		String schedaTitle = String.format("%s: %s", I18N.mrs("dw4.pers_strut_associate"), nomeLista);
		callLookupFromLista( getListaDistribuzioneContainer(), aliasName, aliasName1, titolo, ord, campiLookup, campiClear, xq, db, newRecord, value, "", "", "lookupFromListaDist", schedaTitle);
		getListaDistribuzioneContainer().getRifEsterniDaAggiungere().clear();
		return null;
	}
	/**
	 * Aggiunta azione da eseguire post confirmAll modale lookup
	 * @param docwayLookup
	 * @throws Exception
	 */
	public void onConfirmAll(DocWayLookupListaDist docwayLookup) throws Exception {
		if(docwayLookup.getLookupType()!=null && docwayLookup.getLookupType().equals("lookupFromListaDist")) {
			String toPut = ".rifEsterniDaAggiungere[";
			for(Titolo t: docwayLookup.getAllTitles()) {
				final RifEsterno rifEsternoToAdd = new RifEsterno();
				rifEsternoToAdd.setVincolatoListaDist(true);
				getListaDistribuzioneContainer().getRifEsterniDaAggiungere().add(rifEsternoToAdd);
				t.getCampi().forEach( campo -> {
					if(campo.getNome().startsWith(".rif_esterni.rif")) {
						campo.setNome(campo.getNome().replace(".rif_esterni.rif[", ".rifEsterniDaAggiungere["));
						int num = getListaDistribuzioneContainer().getRifEsterniDaAggiungere().size()-1;
						campo.setNome( campo.getNome().substring(0,toPut.length())+num+campo.getNome().substring(toPut.length()+1));
					}
				});
				docwayLookup.setLookupType("mittente");
				docwayLookup.confirm(t);
			}
			getListaDistribuzioneContainer().getRifEsterniDaAggiungere().forEach( rifE ->{
				// se nessun destinatario e' gia' presente
				if(getDoc().getRif_esterni().stream().noneMatch( rifDom -> rifDom.getNome().equals(rifE.getNome()) && rifDom.getCod().equals(rifE.getCod()))) {
					getDoc().getRif_esterni().add(rifE);
				}
			});
			//rimuuovo eventuali destinatari placeholder
			getDoc().getRif_esterni().removeIf( rifE -> rifE.getNome()==null || rifE.getNome().isEmpty());
			if(getDoc().getRif_esterni().isEmpty()) {
				getDoc().getRif_esterni().add(new RifEsterno());
			}
			docwayLookup.setActive(false);
		}
		getListaDistribuzioneContainer().setListaDistribuzione(new ListaDistribuzioneAppartenenza());
		getListaDistribuzioneContainer().getRifEsterniDaAggiungere().clear();
	}
	protected void callLookupFromLista(XmlEntity entity, String aliasName, String aliasName1, String titolo, String ord, String campiLookup, String campiClear, String xq, String db,
			String newRecord, String value, String tipoDoc, String xverbDoc, String lookupType, String schedaTitle) throws Exception {
		try {
			// in caso di lookup occorre impostare a false il reset dei jobs di iwx
			// perche' viene poi ricaricata la pagina di inserimento/modifica e in caso di immagini precedentemente
			// caricate si perderebbero le anteprime
			setResetJobsIWX(false);
			
			DocWayLookupListaDist docwayLookup = new DocWayLookupListaDist() {
				@Override
				public String confirm() throws Exception {
					String confirmValue = super.confirm();
					doActionOnConfirm( aliasName, confirmValue);
					return confirmValue;
				}
				@Override
				public void confermaTutti() throws Exception {
					super.confermaTutti();
					onConfirmAll(this);
				}
				@Override
				public void init(Document domTitoli) {
					super.init(domTitoli);
					if(schedaTitle!=null && !schedaTitle.isEmpty()) {
						setSchedaTitle(schedaTitle);
					}
				}
			};
			setLookup(docwayLookup);
			docwayLookup.setModel(entity);
			
			// Tipologia del documento corrente e operazione corrente (inserimento, 
			// modifica) per la gestione di lookup particolari (es. voci d'indice)
			if (tipoDoc != null && tipoDoc.length() > 0)
				docwayLookup.setTipoDoc(tipoDoc);
			if (xverbDoc != null && xverbDoc.length() > 0)
				docwayLookup.setXverbDoc(xverbDoc);
	
			XMLDocumento response = this._lookup(aliasName, aliasName1, titolo, ord, campiLookup, campiClear, xq, db, newRecord, value);
			if (handleErrorResponse(response, Const.MSG_LEVEL_ERROR)) {
				getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
				return;
			}
			docwayLookup.getFormsAdapter().fillFormsFromResponse(response);
			docwayLookup.init(response.getDocument());
			docwayLookup.setLookupFieldVal(value);

			if (!docwayLookup.getLookupType().equals("voceIndice") && docwayLookup.getTitoli().size() == 1 && value != null && value.length() > 0 &&
					(lookupType== null || !lookupType.equals("lookupFromListaDist"))) {
				String confirmValue = docwayLookup.confirm(docwayLookup.getTitoli().get(0));
				doActionOnConfirm( aliasName, confirmValue);
				lookupPopupStatusAfterSearch( aliasName, false, docwayLookup.getTitoli().size());
			}else {
				docwayLookup.setActive(true);
				lookupPopupStatusAfterSearch( aliasName, docwayLookup.isActive(), docwayLookup.getTitoli().size());
			}
			if(lookupType!=null) {
				docwayLookup.setLookupType(lookupType);
				docwayLookup.setActive(true);
			}
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return;			
		}
	}
	public String clearLookupListaDistribuzioneAppartenenza() throws Exception{
		String campi = "@nrecord=xml,/lista_dist/@nrecord ; @nome=xml,/lista_dist/nome"; //campi
		return clearField(campi, getListaDistribuzioneContainer().getListaDistribuzione());
	}
	public ListaDistribuzioneContainer getListaDistribuzioneContainer() {
		return listaDistribuzioneContainer;
	}

	public void setListaDistribuzioneContainer(ListaDistribuzioneContainer listaDistribuzioneContainer) {
		this.listaDistribuzioneContainer = listaDistribuzioneContainer;
	}
}
