package it.tredi.dw4.docway.model.analisivirus;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Richiedente di una verifica virus su un documento
 * @author mbernardini
 */
public class Richiedente extends XmlEntity {

	private String codOperatore;
	private String operatore;
	private String data;
	private String ora;
	
	@Override
	public XmlEntity init(Document dom) {
		this.codOperatore = XMLUtil.parseStrictAttribute(dom, "richiedente/@cod_operatore");
		this.operatore = XMLUtil.parseStrictAttribute(dom, "richiedente/@operatore");
		this.data = XMLUtil.parseStrictAttribute(dom, "richiedente/@data");
		this.ora = XMLUtil.parseStrictAttribute(dom, "richiedente/@ora");
		return this;
	}
	
	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		return null;
	}

	public String getCodOperatore() {
		return codOperatore;
	}

	public void setCodOperatore(String codOperatore) {
		this.codOperatore = codOperatore;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}
	
}