package it.tredi.dw4.docway.model.pecmanager;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Esecutore di uno step del flusso PEC MANAGER
 * @author dpranteda
 */
public class Esecutore extends XmlEntity {

	private String codOperatore;
	private String operatore;
	private String data;
	private String ora;
	private String ufficio;
	
	@Override
	public XmlEntity init(Document dom) {
		this.codOperatore 	= XMLUtil.parseAttribute(dom, "@cod_oper");
		this.operatore 		= XMLUtil.parseAttribute(dom, "@oper");
		this.data 			= XMLUtil.parseAttribute(dom, "@data");
		this.ora 			= XMLUtil.parseAttribute(dom, "@ora");
		this.ufficio		= XMLUtil.parseAttribute(dom, "@uff_oper"); 
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

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	
}
