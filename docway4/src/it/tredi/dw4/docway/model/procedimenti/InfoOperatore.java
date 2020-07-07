package it.tredi.dw4.docway.model.procedimenti;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Set di informazioni che identificano un operatore
 */
public class InfoOperatore extends XmlEntity {
	
	/**
	 * Matricola dell'operatore
	 */
	private String cod_persona;
	
	/**
	 * Nome completo dell'operatore (cognome e nome)
	 */
	private String nome_persona;
	
	/**
	 * Codice dell'ufficio di appartenenza dell'operatore
	 */
	private String cod_uff;
	
	/**
	 * Nome dell'ufficio di appartenenza dell'operatore
	 */
	private String nome_uff;

	@Override
	public XmlEntity init(Document dom) {
		this.cod_persona = XMLUtil.parseAttribute(dom, "operatore/@cod_persona");
		this.nome_persona = XMLUtil.parseAttribute(dom, "operatore/@nome_persona");
		this.cod_uff = XMLUtil.parseAttribute(dom, "operatore/@cod_uff");
		this.nome_uff = XMLUtil.parseAttribute(dom, "operatore/@nome_uff");
    	
        return this;
	}

	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		return null;
	}

	public String getCod_persona() {
		return cod_persona;
	}

	public void setCod_persona(String cod_persona) {
		this.cod_persona = cod_persona;
	}

	public String getNome_persona() {
		return nome_persona;
	}

	public void setNome_persona(String nome_persona) {
		this.nome_persona = nome_persona;
	}

	public String getCod_uff() {
		return cod_uff;
	}

	public void setCod_uff(String cod_uff) {
		this.cod_uff = cod_uff;
	}

	public String getNome_uff() {
		return nome_uff;
	}

	public void setNome_uff(String nome_uff) {
		this.nome_uff = nome_uff;
	}

}
