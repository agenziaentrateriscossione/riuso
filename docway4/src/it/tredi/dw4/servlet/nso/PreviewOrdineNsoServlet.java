package it.tredi.dw4.servlet.nso;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.io.SAXReader;

import it.tredi.dw4.acl.beans.UserBean;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator.AdapterConfig;
import it.tredi.dw4.adapters.FormAdapter;
import it.tredi.dw4.beans.AttachFile;
import it.tredi.dw4.docway.utils.NsoUtils;
import it.tredi.dw4.utils.Logger;
import it.tredi.utils.bom.UTFBOMStringCleaner;
import it.tredi.utils.bom.UnicodeBOMInputStream;

/**
 * Servlet di visualizzazione in anteprima di un ordine NSO. Applicazione di un foglio di stile XSLT
 * all'XML dell'ordine in modo da produrre una visualizzazione piu' comprensibile per l'operatore.
 */
public class PreviewOrdineNsoServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4821347373043069834L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream outStream = null;
		try {
			String fileName = (String) request.getParameter("name");
			Logger.info("PreviewOrdineNsoServlet - Param name = " + fileName);
			
			String fileTitle = (String) request.getParameter("title");
			Logger.info("PreviewOrdineNsoServlet - Param title = " + fileTitle);
			if (fileTitle == null || fileTitle.length() == 0)
				fileTitle = fileName;
			
			String login = "";
			String matricola = "";
			HttpSession session = request.getSession();
			if (session != null && session.getAttribute("userBean") != null) {
				// recupero dei dati dell'utente dalla sessione
				UserBean user = (UserBean) session.getAttribute("userBean");
				login = user.getLogin();
				matricola = user.getMatricola();
			}
			else {
				// tentativo di recupeor dell'utente tramite parametri dalla request
				login = (String) request.getParameter("login");
				matricola = (String) request.getParameter("matricola");
			}
			
			String db = (String) request.getParameter("db");
			String customTupleName = (String) request.getParameter("_cd"); // parametro necessario in caso di multisocieta'
			
			Logger.info("PreviewOrdineNsoServlet - Param login = " + login);
			Logger.info("PreviewOrdineNsoServlet - Param matricola = " + matricola);
			Logger.info("PreviewOrdineNsoServlet - Param db = " + db);
			Logger.info("PreviewOrdineNsoServlet - Param _cd = " + customTupleName);
			
			// recupero del file tramite chiamata al service
			AttachFile attachFile = this.redirectRequestToService(login, matricola, customTupleName, db, fileName); // TODO gestire il caso di ritono con errore?
			
			Map <String, String> signatureParams = new HashMap<String, String>();
			String numProt = (String) request.getParameter("numProt");
			if (numProt != null && !numProt.isEmpty()) {
				signatureParams.put("NumeroProtocollo", (String) request.getParameter("numProt"));
				signatureParams.put("DataProtocollo", (String) request.getParameter("dataProt"));
				signatureParams.put("TipoDocumento", (String) request.getParameter("tipoDoc"));
				signatureParams.put("ClassificazioneDocumento", (String) request.getParameter("cassif"));
			}
			
			// applicazione del foglio di stile XSLT all'XML della fattura
			byte[] ordine = transformOrdineXSLT(request, attachFile.getContent(), signatureParams);
				
			// modifica dell'estensione del nome/titolo del file per corretta gestione del mimetype
			// l'estensione dei file p7m viene convertita in xml perche' in realta' viene scaricato dal 
			// service il contenuto dei file (fattura xml)
			if (fileName.toLowerCase().endsWith(".p7m"))
				fileName = fileName.replace(".p7m", ".xml");
			if (fileTitle.toLowerCase().endsWith(".p7m"))
				fileTitle = fileTitle.replace(".p7m", ".xml");
			
			outStream = response.getOutputStream();
			ServletContext context = getServletConfig().getServletContext();
			String mimetype = context.getMimeType(fileName);

			// imposta il content type della response
			if (mimetype == null) {
				mimetype = "application/octet-stream";
			}
			response.setContentType(mimetype);
			response.setContentLength(ordine.length);
			
			// imposta l'header HTTP
			response.setHeader("Content-Disposition", "inline; filename=\"" + fileTitle + "\"");

			outStream.write(ordine);
		} 
		catch (Throwable t) {
			Logger.error("Errore in download del file da DocWay-service", t);
			// TODO gestire il caso di eccezione con il ritorno di errore a IWX
		}
		finally {
			if (outStream != null)
				outStream.close();
		}
	}
	
	/**
	 * Redirect della richiesta di download del file a DocWay4-Service
	 * 
	 * @param login
	 * @param matricola
	 * @param db
	 * @param fileName
	 * 
	 * @return contenuto della response restituito dal service (file o messaggio di errore) 
	 * @throws Exception
	 */
	private AttachFile redirectRequestToService(String login, String matricola, String customTupleName, String db, String fileName) throws Exception {
		UserBean userBean = new UserBean(login);
		userBean.setMatricola(matricola);
		
		userBean.setServiceFormParam("db", db);
		userBean.setServiceFormParam("verbo", "attach");
		userBean.setServiceFormParam("xverb", "");
		userBean.setServiceFormParam("enableIW", "true"); // impostato a true perche' questa chiamata viene fatta sempre e solo da IWX
		userBean.setServiceFormParam("_cd", customTupleName); // parametro necessario in caso di multisocieta'
		userBean.setServiceFormParam("name", fileName);
		userBean.setServiceFormParam("id", fileName);
		userBean.setServiceFormParam("extractIfP7M", "true"); // in caso di file firmato P7M forza l'estrazione del contenuto
					
		AdapterConfig config = AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService");
		FormAdapter formAdapter = new FormAdapter(config.getHost(), config.getPort(), config.getProtocol(), config.getResource(), config.getUserAgent());
		
		return formAdapter.executeDownloadFile(userBean);
	}
	
	/**
	 * generazione dell'anteprima dell'ordine (si da per scontato che il file passato faccia
	 * riferimento all'XML dell'ordine)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] transformOrdineXSLT(HttpServletRequest req, byte[] bytesOrdine, Map<String, String> signatureParams) throws Exception {
		
		String xsltFileName = NsoUtils.getXsltFileForPreview("latest"); // TODO eventuale recupero del corretto file XSLT in base alla versione dell'ordine
		
		String xslt = req.getRequestURL().toString().replace("previewnso", "docway/nso/xslt/" + xsltFileName);
		Logger.info("PreviewOrdineNsoServlet - XSLT = " + xslt);
		
		String xmlOrdine = "";
		
		// mbernardini 14/04/2015 : eventuale eliminazione di riferimenti a XSL gia' presenti all'interno dell'ordine
		try {
			SAXReader reader = new SAXReader();
			// mbernardini 10/04/2019 : problema di enconding su contenuto del file XML
			//String strxml = new String(bytesfattura, "UTF-8");
			//Document docFattura = reader.read(new StringReader(cleanXMLString(strxml)));
			UnicodeBOMInputStream is = new UnicodeBOMInputStream(new ByteArrayInputStream(bytesOrdine));
			is.skipBOM();
			Document docOrdine = reader.read(is);
			
			List<?> pis = docOrdine.processingInstructions();
			if (pis.size() > 0) {
				for (int i=0; i<pis.size(); i++) {
					ProcessingInstruction pi = (ProcessingInstruction) pis.get(i);
					if (pi != null && pi.getName().equals("xml-stylesheet"))
						docOrdine.remove(pi);
				}
			}
			
			DocumentFactory factory = new DocumentFactory();
			Map<String, String> piArgs = new HashMap<String, String>();
			piArgs.put("type", "text/xsl");
			piArgs.put("href", xslt);
			ProcessingInstruction pi = factory.createProcessingInstruction("xml-stylesheet", piArgs);
			List content = docOrdine.content();
			content.add(0, pi);
			docOrdine.setContent(content);
			
			// Aggiunta dei dati di segnatura all'XML dell'ordine
			if (signatureParams != null && !signatureParams.isEmpty()) {
				Element datiSegnatura = DocumentHelper.createElement("DatiSegnatura");
				for (Map.Entry<String, String> entry : signatureParams.entrySet()) {
					Element keyElement = DocumentHelper.createElement(entry.getKey());
					keyElement.addText((entry.getValue() != null) ? entry.getValue() : "");
					datiSegnatura.add(keyElement);
				}
				docOrdine.getRootElement().add(datiSegnatura);
			}
			
			xmlOrdine = docOrdine.asXML();
		}
		catch (Exception e) {
			Logger.error(e.getMessage());
			
			xmlOrdine = new String(bytesOrdine, "UTF-8");
			xmlOrdine = cleanXMLString(xmlOrdine);
			
			String xmlDeclaration = "";
			if (xmlOrdine.contains("<?xml")) {
				xmlDeclaration = xmlOrdine.substring(0, xmlOrdine.indexOf("?>")+2);
				xmlOrdine = xmlOrdine.substring(xmlOrdine.indexOf("?>")+2);
			}
			
			if (xmlOrdine.contains("<?xml-stylesheet"))
				xmlOrdine = xmlOrdine.substring(xmlOrdine.indexOf("?>")+2);
			
			xmlOrdine = xmlDeclaration + "<?xml-stylesheet type=\"text/xsl\" href=\"" + xslt + "\"?>" + xmlOrdine;
			
			if (signatureParams != null && !signatureParams.isEmpty())
				Logger.warn("XML ordine elaborato come STRINGA... Dati di protocollo NON aggiunti!");
		}
		
		// mbernardini 10/04/2019 : corretto problema di enconding su contenuto del file XML
		// mbernardini 26/11/2019 : ripristinata l'indicazione di UTF-8 per problemi di encoding su ambiente windows (andrebbe approfondito il motivo del precedente intervento) 
		return xmlOrdine.getBytes("UTF-8");
	}
	
	/**
	 * Ripulisce la stringa contenente XML in input dagli spazi all'inizio e
	 * alla fine e rimuove eventuali BOM ad inizio stringa.
	 * 
	 * @param inputXML
	 *            La stringa contenente il XML da pulire
	 * @return La stringa senza spazi e BOM all'inizio/fine o la stringa
	 *         iniziale se vuota o null.
	 */
	private String cleanXMLString(String inputXML) {
		String result = inputXML;
		if (inputXML != null && !inputXML.isEmpty()) {
			result = result.trim();
			result = UTFBOMStringCleaner.removeBOM(result);
		}
		return result;
	}
	
}
