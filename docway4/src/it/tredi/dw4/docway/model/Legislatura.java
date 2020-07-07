package it.tredi.dw4.docway.model;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Informazioni relative alla legislatura
 */
public class Legislatura extends XmlEntity {
	
	/**
	 * Numero della legislatura
	 */
	private String numero;
	
	/**
	 * Descrizione della legislatura
	 */
	private String descrizione;

	@Override
	public XmlEntity init(Document dom) {
		this.numero = XMLUtil.parseAttribute(dom, "legislatura/@numero");
    	this.descrizione = XMLUtil.parseElement(dom, "legislatura", true);
    	
        return this;
	}

	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	
    	params.put(prefix+".@numero", this.numero);
    	params.put(prefix, this.descrizione);
    	
    	return params;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
