package it.tredi.dw4.docway.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.tredi.dw4.utils.DocWayProperties;

/**
 * Utilities per la gestione delle specifiche di NSO (Nodo di smistamento degli ordini di acquisto delle amministrazioni pubbliche)
 */
public class NsoUtils {
	
	/**
	 * Data una versione di NSO restituisce il file da utilizzare per la generazione della preview dell'ordine
	 * in base a quanto definito sul file di properties di DocWay
	 * 
	 * @param versione versione del file XML di NSO 
	 * @return
	 */
	public static String getXsltFileForPreview(String versione) {
		String file = "";
		
		if (versione != null && !versione.isEmpty()) {
			List<String> xslts = DocWayProperties.getPropertyList("nso.xslt");
			if (xslts != null && xslts.size() > 0) {
				int i = 0;
				while (i < xslts.size() && file.length() == 0) {
					String xslt = xslts.get(i);
					if (xslt.length() > 0 && xslt.startsWith(versione + ","))
						file = xslt.substring(xslt.indexOf(",")+1);
					i++;
				}
				if (file.isEmpty()) {
					// versione specificata non trovata... tento di utilizzare l'ultime disponibile
					String xslt = xslts.get(xslts.size()-1);
					file = xslt.substring(xslt.indexOf(",")+1);
				}
			}
		}
		
		return file;
	}
	
	/**
	 * Dato il nome di un file allegato al documento, ritorna TRUE se il file potrebbe corrispondere ad un file di Ordine, 
	 * FALSE altrimenti
	 * 
	 * @param fileName Nome del file da verificare
	 * @return
	 */
	public static boolean isNsoFile(String fileName) {
		boolean nso = false;
		if (fileName != null && !fileName.isEmpty()) {
			fileName = fileName.toLowerCase();
			String regexp = "^[a-zA-Z]{2}[a-zA-Z0-9]{11,16}_oz_[a-zA-Z0-9]{1,5}.xml"; // regular expression per trasmittente italiano
			if (!fileName.toLowerCase().startsWith("it")) // codice del trasmittente di un paese estero
				regexp = "^[a-zA-Z]{2}[a-zA-Z0-9]{2,28}_oz_[a-zA-Z0-9]{1,5}.xml";
			
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(fileName);
			nso = matcher.find();
		}
		return nso;
	}
	
}
