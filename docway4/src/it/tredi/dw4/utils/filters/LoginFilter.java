package it.tredi.dw4.utils.filters;

import it.tredi.dw4.acl.beans.UserBean;
import it.tredi.dw4.beans.Init;
import it.tredi.dw4.utils.AppUtil;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.Logger;
import org.apache.axis.session.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	private static String DEFAULT_INIT_PACKAGE = "it.tredi.dw4";

	@Override
	public void destroy() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		session = invalidatePreviousSession(session, request);

		String user = AppUtil.loadAuthenticationRemoteUser(request);
		Logger.info("LoginFilter.doFilter(), remoteUser caricato: " + user);

		if (request.getRequestURI().endsWith(".pf")) {
			Map<String, String[]> rewritedUriParams = (Map<String, String[]>) session.getAttribute("rewritedUriParams");
			if (rewritedUriParams == null)
				rewritedUriParams = new HashMap<String, String[]>();
			rewritedUriParams.putAll(request.getParameterMap());
			rewritedUriParams.remove("forceLogin");
			session.setAttribute("rewritedUriParams", rewritedUriParams);
		}

		String uri = request.getRequestURI();

		if (!uri.endsWith("initadm.jsf") && !uri.endsWith("javax.faces.resource/jsf.js.jsf")) {

			UserBean userBean = (UserBean) session.getAttribute("userBean");
			if (userBean == null || !userBean.getLogin().equals(user)) {
				// bean utente non in sessione - primo accesso (login)
				userBean = new UserBean(user);
				session.setAttribute("userBean", userBean);
			}

			FilterAction filterAction = initAppBeanAndDecideAction(request, response);
			switch (filterAction) {
				case PROCEED:
					// si può procedere a soddisfare la richiesta
					// accesso ad una pagina specifica (pretty faces)
					chain.doFilter(request, response);
					break;
				case HOME:
					// accesso alla homepage
					// gestita la costruzione dell'uri per login in caso di template personalizzati
					// presenti in sottocartelle. Si rischierebbe di caricare una home.jsf interna
					// ad una cartella personalizzata di un cliente (file non presente)
					response.sendRedirect(AppUtil.getSendRedirect(request.getRequestURI(), "home.jsf"));
					break;
				case INIT:
					// è necessario ritornare alla init: potrebbe essere necessaria una selezione multiutente, multisocietà
					// o si è verificato un qualche errore.
					if (uri.endsWith(".pf")) {
						savePreviousRequestURI(uri, session, request);
					}
					response.sendRedirect(AppUtil.getSendRedirect(request.getRequestURI(), "init.jsf"));
					break;
				case CHANGE_PASSWORD:
					// è necessario effettuare un cambio password
					if (uri.endsWith(".pf")) {
						savePreviousRequestURI(uri, session, request);
					}
					response.sendRedirect(AppUtil.getSendRedirect(request.getRequestURI(), "cambioPassword.jsf"));
					break;
			}
		}
		else {
			chain.doFilter(request, response);
		}
	}

	private void savePreviousRequestURI(String uri, HttpSession session, HttpServletRequest request) {
		// salvataggio della richiesta di una pagina specifica (pretty faces)
		String queryParams = getRequestQueryString(request);
		if (queryParams != null && !queryParams.equals(""))
			session.setAttribute("rewritedUri", uri + "?" + queryParams);
		else
			session.setAttribute("rewritedUri", uri);
	}

	/**
	 * verifica se il service ha già segnalato la necessità di cambiare password per l'utente corrente
	 */
	private boolean isChangePasswordRequired(Object changePasswordBean) {
		return changePasswordBean != null;
	}

	/**
	 * verifica se l'utente corrente e' effettivamente loggato (in caso di login multipla
	 * se e' stato selezionato un nominativo dall'elenco)
	 */
	private boolean isUserLoggedIn(UserBean user) {
		return (user != null
				&& user.getLogin() != null && !user.getLogin().isEmpty()
				&& user.getMatricola() != null && !user.getMatricola().isEmpty());
	}

	/**
	 * verifica che una richiesta sia valida, ovvero sia stata fatta dall'interno dell'applicativo o se sia una richiesta
	 * di un url copia incollato nella barra del browser, ma comunque gestito con pretty faces
	 * FIXME metodo necessario? implementato per mantenere stesso comportamento nel doFilter precedente al refactor
	 */
	private boolean isValidRequest(HttpSession session, String uri) {
		String appName = (String) session.getAttribute("appName");
		String curAppName = extractAppName(uri);

		if (curAppName != null && (appName == null || !appName.equals(curAppName))) {
			// reinitializzazione effettuata, uri doveva essere pretty faces
			return uri.endsWith(".pf");
		}
		return true;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	/**
	 * Estrazione del nome dell'applicazione (webapp) in base all'uri richiesto
	 *
	 * @param uri
	 * @return
	 */
	private String extractAppName(String uri) {
		String[] parts = uri.split("\\/");
		if (parts != null && parts.length > 2 && !parts[2].equals("javax.faces.resource"))
			return parts[2];
		else
			return null;
	}

	/**
	 * Inizializza (o lo carica dalla sessione se presente) il bean dell'applicazione corrente 
	 *
	 * @param request
	 * @param response
	 * @return {@link FilterAction} di descrizione dell'operazione da intraprendere
	 */
	private FilterAction initAppBeanAndDecideAction(HttpServletRequest request, HttpServletResponse response) {
		FilterAction filterAction = FilterAction.PROCEED;

		String uri = request.getRequestURI();
		String[] parts = uri.split("/");
		HttpSession session = request.getSession(false);

		String packageName = "";
		if (parts.length > 2)
			packageName = parts[2];

		if (!packageName.equals("")) {
			try {
				String initBeanName = packageName + "Init";

				Init initBean = null;
				String appName = (String) session.getAttribute("appName");
				String curAppName = extractAppName(uri);

				// Reinizializza il bean solo se l'utente e' nullo (login) o e'
				// cambiata l'applicazione (es. passaggio da docway ad acl)
				// o se è stato richiesto esplicitamente
				if ((curAppName != null && (appName == null || !appName.equals(curAppName)))) {
					Logger.info("LoginFilter.recreateInitBean(), primo caricamento dell'applicazione " + curAppName);

					if (session.getAttribute(initBeanName) != null) {
						initBean = (Init) session.getAttribute(initBeanName);

						Logger.info("LoginFilter.recreateInitBean(), applicazione " + curAppName + " in sessione, init non necessario");
					}

					if (initBean == null) {
						String className = Character.toUpperCase(initBeanName.charAt(0)) + initBeanName.substring(1);
						Class<?> initBeanClass = Class.forName(LoginFilter.DEFAULT_INIT_PACKAGE + "." + packageName + "." + className);

						Logger.info("LoginFilter.recreateInitBean(), init dell'applicazione " + curAppName + " [" + LoginFilter.DEFAULT_INIT_PACKAGE + "." + packageName + "." + className + "]");

						initBean = (Init) initBeanClass.newInstance();
						initBean.injectRequestAndResponse(request, response);
						filterAction = initBean.init();

						session.setAttribute(initBeanName, initBean);
					}

					session.setAttribute("appName", curAppName);
				}

			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}

		if (filterAction == FilterAction.PROCEED) {
			// utente non loggato, redirec INIT
			if (!uri.endsWith("init.jsf") && !isUserLoggedIn((UserBean) session.getAttribute("userBean"))) {
				return FilterAction.INIT;
			}
			// utente obbligato al cambio password, redirect CHANGE_PASSWORD
			if (!uri.endsWith("cambioPassword.jsf") && isChangePasswordRequired(session.getAttribute("forceChangePassword"))) {
				return FilterAction.CHANGE_PASSWORD;
			}
			// uri non pretty faces alla prima login, refirect HOME
			if (!uri.endsWith("home.jsf") && !uri.endsWith("init.jsf") && !uri.endsWith("cambioPassword.jsf") && !isValidRequest(session, uri)) {
				return FilterAction.HOME;
			}
		}

		return filterAction;
	}

	/**
	 * invalida sessioni utente precedentemente aperte in caso di passaggio
	 * da un'applicazione ad un'altra
	 */
	private HttpSession invalidatePreviousSession(HttpSession session, HttpServletRequest request) {
		// viene invalidata la sessione nel caso in cui si passi da una applicazione ad un altra
		if (session != null && session.getAttribute(Const.DOCWAY_EMBEDDED_APP_NAME) != null) {
			// questa attivita' deve essere svolta solo in caso di caricamento dell'applicazione (accesso
			// a home.jsf o init.jsf). Tutti gli altri url possono essere condivisi fra applicazioni
			// differenti.
			// Questa attivita' deve inoltre essere svolta solamente se la chiamata non e' AJAX (tramite JSF e' possibile
			// che vengano effettuate chiamate AJAX alla root dell'applicazione)
			if (AppUtil.isPossibleApplicationSwitch(request)) {
				if (!AppUtil.isCurrentAppInSession(session, request)) {
					Logger.info("LoginFilter.invalidatePreviousSession(), INVALIDATE CURRENT SESSION");
					session.invalidate();
					session = request.getSession(true);
				}
			}
		}

		return session;
	}

	/**
	 * restituisce i parametri specificati nella request, ripuliti da eventuali parametri 
	 * di servizio (es. 'forceLogin' per forzare il caricamento dell'utente - possibile loop con prettyfaces
	 * se mantenuto 'forceLogin')
	 *
	 * @param request
	 * @return
	 */
	private String getRequestQueryString(HttpServletRequest request) {
		String queryParams = "";
		if (request != null) {
			String params = request.getQueryString();
			if (params != null && params.length() > 0) {
				String[] paramsarray = params.split("&");
				if (paramsarray != null && paramsarray.length > 0) {
					for (int i=0; i<paramsarray.length; i++) {
						if (!paramsarray[i].startsWith("forceLogin"))
							queryParams += paramsarray[i] + "&";

						// FIXME gestire eventuali altri parametri da eliminare dalla request
					}

					if (queryParams.length() > 0)
						queryParams = queryParams.substring(0, queryParams.length()-1);
				}
			}
		}
		return queryParams;
	}

}
