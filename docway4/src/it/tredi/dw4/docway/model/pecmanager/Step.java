package it.tredi.dw4.docway.model.pecmanager;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Step del flusso PEC MANAGER
 * @author dpranteda
 */
public class Step extends XmlEntity {

	private String status;
	private Esecutore esecutore = new Esecutore();
	
	@Override
	public XmlEntity init(Document dom) {
		this.status = XMLUtil.parseStrictAttribute(dom, "step/@status");
		this.esecutore.init(XMLUtil.createDocument(dom, "step"));
		return this;
	}
	
	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		return null;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Esecutore getEsecutore() {
		return esecutore;
	}

	public void setEsecutore(Esecutore esecutore) {
		this.esecutore = esecutore;
	}

	
}
