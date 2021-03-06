package it.tredi.dw4.beans;

import it.tredi.dw4.acl.beans.UserBean;
import it.tredi.dw4.acl.model.Societa;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.docway.beans.DocWayChangePassword;
import it.tredi.dw4.docway.doc.adapters.DocDocWayQueryFormsAdapter;
import it.tredi.dw4.jsf.scope.ClientWindowUtils;
import it.tredi.dw4.utils.*;
import it.tredi.dw4.utils.filters.FilterAction;
import org.dom4j.Element;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Init extends Page {

	protected DocDocWayQueryFormsAdapter formsAdapter;

	private List<Map<String,String>> matricoleL;
	private List<Societa> societaL;
	private boolean allSocieta = true;
	private String societaSelezionata = "";
	private String currentLoginStep = "";

	private String archivio = ""; // identifica l'archivio al quale si sta facendo accesso

	public Init() throws Exception {
		try {
			this.formsAdapter = new DocDocWayQueryFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			setFatalLevelInErrormsg();
			return;
		}
	}

	/**
	 * Metodo eseguito per il login dell'utente (con scelta della matricola) e inizializzazione di tutti
	 * i bean necessari al caricamento della pagina richiesta (in base all'URL utilizzato per la chiamata)
	 *
	 * @return false in caso di redirect su init.jsf (login con scelta matricola), true in caso di caricamento di qualsiasi altra pagina
	 * @throws Exception
	 */
	public FilterAction init() throws Exception {
		//formsAdapter.getDefaultForm().addParam("verbo", "");
		//formsAdapter.getDefaultForm().addParam("query", "");

		String forceLogin = ""; // forzatura della login (azzeramento di qualsiasi sessione utente)

		String mutiarchEnabled = ""; // login multiarchivio abilitata (caricamento dell'archivio docway in base all'AOO di appartenenza della persona)

		// caricamento su formsAdapter dei parametri che arrivano da url (tipo nome del db)
		if (request != null) {
			String appName = AppUtil.getAppName(request);
			setEmbeddedAppInSession(appName); // aggiunta dell'applicazione in sessione

			String db = getDbForApp(appName);
			formsAdapter.getDefaultForm().addParam("db", db); // gestione db dell'applicazione (aggiunta del parametro al formadapter)
			archivio = db;

			// interfaccia utente specifica per la gestione di un repertorio (CRUD)
			if (appName.equals("dwrep")) {
				String repCode = (String) request.getParameter("repCode");
				if (repCode == null || repCode.length() == 0)
					repCode = DocWayProperties.readProperty("dwrep.codici_repertori", ""); // tentativo di caricamento da file di properties

				if (repCode.length() > 0) {
					formsAdapter.getDefaultForm().addParam("verbo", appName);
					formsAdapter.getDefaultForm().addParam("dwRepCode", repCode);
					setSessionAttribute("repCode", repCode);
				}
			}

			// caricamento dell'eventuale parametro di debug
			String debug = (String) request.getParameter("debug");
			if (debug != null && debug.length() > 0)
				setSessionAttribute("debugMode", debug);

			isEnabledIWX(); // abilitazione o meno di IWX

			// caricamento dell'eventuale parametro di forceLogin
			forceLogin = (String) request.getParameter("forceLogin");

			// login multiarchivio...
			mutiarchEnabled = (String) request.getParameter("mutiarchEnabled");
			// salvataggio in sessione del parametro per la gestione del logout
			if (mutiarchEnabled != null && !mutiarchEnabled.isEmpty())
				setSessionAttribute("mutiarchEnabled", mutiarchEnabled);
		}

		UserBean user = getUserBean();
		if (forceLogin != null && forceLogin.length() > 0 && forceLogin.equals("true"))
			formsAdapter.getDefaultForm().addParam("renewLogin", "true");

		if (mutiarchEnabled != null && mutiarchEnabled.length() > 0 && mutiarchEnabled.equals("true"))
			formsAdapter.getDefaultForm().addParam("mutiarchEnabled", "true");

		XMLDocumento response = formsAdapter.getDefaultForm().executePOST(user);
		Logger.info("Init.init(), chiamata a service per l'utente " + user.getLogin() + " [matricola: " + user.getMatricola() + "]");

		// azzeramento del parametro forceLogin
		formsAdapter.getDefaultForm().removeParam("renewLogin");

		// gestione multilogin
		if (handleSceltaLoginResponse(response)) {
			Logger.info("Init.init(), login multipla, scelta dell'utente");

			if (user.getMatricola() == null || user.getMatricola().equals(""))
				return FilterAction.INIT; //multilogin -> scelta persona
			else {
				formsAdapter.loginWithMatricola(user.getMatricola());
				response = formsAdapter.getDefaultForm().executePOST(user);
			}
		}

		// gestione multisocieta'
		if (handleSceltaMultisocietaResponse(response)) {
			Logger.info("Init.init(), multisocieta, scelta della societa' dell'utente");
			return FilterAction.INIT; //multisocieta -> scelta societa'
		}

		if (handleErrorResponse(response)) {
			Logger.info("Init.init(), errore riscontrato");

			setFatalLevelInErrormsg();
			return FilterAction.INIT;
		}

		if (user.getMatricola() == null || user.getMatricola().equals("")) {
			String matricola = response.getRootElement().attributeValue("opCodPersona");
			if (matricola != null) {
				user.setMatricola(matricola);
				setSessionAttribute("userBean", user);

				Logger.info("Init.init(), assegnazione della matricola " + matricola + " a bean utente " + user.getLogin() + " da cache del service");
			}
		}

		// cambio password necessario
		if (handleCambioPassword(response)) {
			return FilterAction.CHANGE_PASSWORD;
		}

		initDocWayHomeFromResponse(response);
		return FilterAction.PROCEED;
	}

	/**
	 * Verifica se l'utente deve effettuare un cambio password obbligatorio e nel caso, prepara il bean necessario e lo
	 * salva in sessione.
	 *
	 * @param response Response del service
	 * @throws Exception
	 * @return true in caso in cui il cambio password sia necessario, false altrimenti
	 */
	protected boolean handleCambioPassword(XMLDocumento response) throws Exception {
		if (response.testAttributeValue("/response/@dbTable", "@pwd")) {
			// lascio in sessione rewriteUri che mi servirà per il redirect dopo il cambio password obbligatorio
			// TODO verificare che sia login principale
			DocWayChangePassword docWayChangePassword = new DocWayChangePassword();
			docWayChangePassword.getFormsAdapter().fillFormsFromResponse(response);
			docWayChangePassword.init(response.getDocument());
			docWayChangePassword.setLogin(getUserBean().getLogin());
			docWayChangePassword.setForcedChange(true);
			setSessionAttribute("forceChangePassword", docWayChangePassword);
			return true;
		}
		return false;
	}

	/**
	 * aggiunta dell'applicazione in sessione
	 * @param appName
	 */
	private void setEmbeddedAppInSession(String appName) {
		// l'aggiunta in sessione dell'applicazione corrente avviene solo al caricamento
		// della home e se non si tratta di una chiamata AJAX
		if (AppUtil.isPossibleApplicationSwitch(request)) {
			String sharedSessionApps = DocWayProperties.readProperty(Const.PROPERTY_SHARED_SESSION_APPS, ""); // eventuali apps embedded con sessione condivisa con DocWay

			if (!appName.equals("docway") && !appName.equals("acl") && !appName.equals("docwayproc") && !sharedSessionApps.contains(appName)) {
				formsAdapter.getDefaultForm().addParam(Const.DOCWAY_EMBEDDED_APP_NAME, appName); // gestione di applicazioni embedded interne a DocWay (es. crud su repertori)
				setSessionAttribute(Const.DOCWAY_EMBEDDED_APP_NAME, appName);
			}
			else {
				formsAdapter.getDefaultForm().addParam(Const.DOCWAY_EMBEDDED_APP_NAME, "");
				setSessionAttribute(Const.DOCWAY_EMBEDDED_APP_NAME, "");
			}
		}
	}

	/**
	 * Chiamata a logout (inattiva la cache del service)
	 * @return
	 * @throws Exception
	 */
	public boolean logout(UserBean userBean) throws Exception {
		formsAdapter.logout();
		XMLDocumento response = formsAdapter.getDefaultForm().executePOST(userBean);
		if (handleErrorResponse(response)) {
			return false;
		}
		return true;
	}

	/**
	 * selezione login da pagina di multilogin
	 * @return
	 * @throws Exception
	 */
	public String loginWithMatricola() throws Exception {
		return loginWithMatricola("");
	}

	/**
	 * selezione login da pagina di multilogin
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loginWithMatricola(String matricola) throws Exception {

		if(matricola == null || matricola.isEmpty()) {
			Map<String,String> map = (Map<String,String>)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("item");
			matricola = map.get("matr");
		}

		UserBean user = getUserBean();
		user.setMatricola(matricola);
		setSessionAttribute("userBean", user);

		formsAdapter.loginWithMatricola(matricola);

		return executeLogin(user);

	}

	/**
	 * selezione di utente delega
	 * @return
	 * @throws Exception
	 */
	public String loginWithDelega() throws Exception {
		UserBean user = getUserBean();
		UserBean delegato = getDelegatoBean();

		formsAdapter.loginByDelega(user.getMatricola(), delegato);
		return executeLogin(user);
	}

	/**
	 * funzione che ripristina la login dell'utente che era entrato come delegato
	 * @return
	 * @throws Exception
	 */
	public String resetLoginAfterDelega() throws Exception {
		UserBean user = getUserBean();
		formsAdapter.resetDelega(user.getMatricola());
		return executeLogin(user);
	}

	/**
	 * esegue il login dello userBean e il redirect nella pagina "base"
	 * @return
	 * @throws Exception
	 */
	public String executeLogin(UserBean user) throws Exception {
		try {
			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(user);

			if (handleErrorResponse(response)) {
				if (formsAdapter.getLastResponse() != null)
					formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				setFatalLevelInErrormsg();
				return null;
			}

			// gestione multisocieta'
			if (handleSceltaMultisocietaResponse(response)) {
				Logger.info("Init.init(), multisocieta, scelta della societa' dell'utente");
				return null; //multisocieta -> scelta societa'
			}

			if (handleCambioPassword(response)) {
				Logger.info("Init.executeLogin(), cambio password necessario");
				return "show@change_pwd";
			}

			initDocWayHomeFromResponse(response);

			String rewritedUri = (String) getSessionAttribute("rewritedUri");
			if (rewritedUri != null && rewritedUri.length() > 0) {
				setSessionAttribute("rewritedUri", null);
				FacesContext.getCurrentInstance().getExternalContext().redirect(ClientWindowUtils.addClientIdToUri(rewritedUri));
				return null;
			}

			return redirectFromMatricolaLogin();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			setFatalLevelInErrormsg();
			return null;
		}
	}

	/**
	 * selezione societa' da pagina di multisocieta'
	 * @return
	 * @throws Exception
	 */
	public String loginWithSocieta() throws Exception {
		try {
			UserBean user = getUserBean();

			formsAdapter.loginWithSocietaMatricola(societaSelezionata, allSocieta, user.getMatricola());
			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(user);

			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				setFatalLevelInErrormsg();
				return null;
			}

			user.setCodSocieta(societaSelezionata);
			user.setAllSocieta(allSocieta);
			setSessionAttribute("userBean", user);

			if (handleCambioPassword(response)) {
				Logger.info("Init.executeLogin(), cambio password necessario");
				return "show@change_pwd";
			}

			initDocWayHomeFromResponse(response);

			String rewritedUri = (String) getSessionAttribute("rewritedUri");
			if (rewritedUri != null && rewritedUri.length() > 0) {
				setSessionAttribute("rewritedUri", null);
				FacesContext.getCurrentInstance().getExternalContext().redirect(rewritedUri);
				return null;
			}

			return redirectFromMatricolaLogin();
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			setFatalLevelInErrormsg();
			return null;
		}
	}

	/**
	 * richiesta all'utente la scelta della login (multilogin)
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected boolean handleSceltaLoginResponse(XMLDocumento response) throws Exception {
		if (response.testAttributeValue("/response/@verbo", "scelta_login")) { //nel caso di multilogin propone la lista delle matricole
			matricoleL = new ArrayList<Map<String,String>>();
			List<Element> els = response.selectNodes("/response/login");
			for (int i=0; i<els.size(); i++) {
				Element el = (Element)els.get(i);
				Map<String, String> map = new HashMap<String, String>();
				matricoleL.add(map);
				map.put("matr", el.attributeValue("matr"));
				map.put("descr", el.attributeValue("descr"));
			}

			currentLoginStep = "login";

			return true;
		}
		return false;
	}

	/**
	 * richiesta all'utente la scelta della societa' (multisocieta')
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected boolean handleSceltaMultisocietaResponse(XMLDocumento response) throws Exception {
		if (response.testAttributeValue("/response/@verbo", "scelta_societa")) { //nel caso di multisocieta propone la lista delle societa'

			societaL = XMLUtil.parseSetOfElement(response.getDocument(), "/response/societa_select/societa", new Societa());
			Societa emptySocieta = new Societa();
			emptySocieta.setCod("_");
			emptySocieta.setText("");
			emptySocieta.setSelected("true");
			societaL.add(0, emptySocieta);

			allSocieta = true;
			currentLoginStep = "societa";

			return true;
		}
		return false;
	}



	/**
	 * selezione di una societa (change sulla select) in caso di multisocieta
	 */
	public void societaChangeListener(ValueChangeEvent e) {
		if (e.getNewValue() != null && !e.getNewValue().equals("_"))
			this.allSocieta = false;
		else
			this.allSocieta = true;
	}

	public DocDocWayQueryFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}

	public void setFormsAdapter(DocDocWayQueryFormsAdapter formsAdapter) {
		this.formsAdapter = formsAdapter;
	}

	public List<Map<String, String>> getMatricoleL() {
		return matricoleL;
	}

	public List<Societa> getSocietaL() {
		return societaL;
	}

	public boolean isAllSocieta() {
		return allSocieta;
	}

	public void setAllSocieta(boolean allSocieta) {
		this.allSocieta = allSocieta;
	}

	public String getCurrentLoginStep() {
		return currentLoginStep;
	}

	public String getSocietaSelezionata() {
		return societaSelezionata;
	}

	public void setSocietaSelezionata(String societaSelezionata) {
		this.societaSelezionata = societaSelezionata;
	}

	public String getArchivio() {
		return archivio;
	}

	public void setArchivio(String db) {
		this.archivio = db;
	}

	abstract protected void initDocWayHomeFromResponse(XMLDocumento response) throws Exception;
	abstract protected String redirectFromMatricolaLogin();

	/**
	 * Inizializzazione di tutti i dati dell'utente corrente recuperati dalla response (in fase di init post login) e memorizzazione
	 * in sessione
	 * @param userbean Oggetto UserBean contenente le informazioni relative all'utente loggato
	 * @param response Response relativa ad una init post login
	 * @return
	 */
	public UserBean initUserBeanData(UserBean userbean, XMLDocumento response) {
		Element rootResponse = response.getRootElement();
		//tiommi 26/09/2017 - gestione deleghe
		UserBean delegato = getDelegatoBean();
		if(delegato != null && delegato.getUserInfo() != null) {
			Logger.info("Init.initUserBeanData(): found delegato data for " + delegato.getLogin());

			rootResponse.addAttribute("delegato", delegato.getUserInfo());
			rootResponse.addAttribute("delegatoLogin", delegato.getLogin());
			rootResponse.addAttribute("delegatoMatricola", delegato.getMatricola());
		}

		if (null != userbean && null == userbean.getUserInfo()) {
			Logger.info("Init.initUserBeanData(): init user data for " + userbean.getLogin());

			if (userbean.getMatricola() == null || userbean.getMatricola().equals(""))
				userbean.setMatricola(rootResponse.attributeValue("matricola", "")); // assegnazione della matricola
			userbean.setUserInfo(rootResponse.attributeValue("userInfo", null));
			userbean.setCodSede(rootResponse.attributeValue("cod_sede", null));
			setSessionAttribute("userBean", userbean);
		}
		return userbean;
	}

}
