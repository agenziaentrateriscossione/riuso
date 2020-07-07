package it.tredi.dw4.docway.model.webmail;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.DateUtil;
import it.tredi.dw4.utils.XMLUtil;

public class Filter extends XmlEntity {

	private String type = ""; // none, uidrange, daterange, advanced
	private String from = "";
	private String to = "";
	private String contains = "";
	
	@Override
	public XmlEntity init(Document dom) {
		this.type 		= XMLUtil.parseAttribute(dom, "filter/@type");
		this.from 		= XMLUtil.parseAttribute(dom, "filter/@from");
		this.to 		= XMLUtil.parseAttribute(dom, "filter/@to");
		this.contains 	= XMLUtil.parseAttribute(dom, "filter/@contains");
		
		return this;
	}

	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		if (null == prefix) prefix = "";
		Map<String, String> params = new HashMap<String, String>();
		
    	params.put(prefix+".filter.type", this.type);
    	params.put(prefix+".filter.from", (this.from != null && !this.from.isEmpty()) ? DateUtil.formatDate2XW(this.from, "") : "");
    	params.put(prefix+".filter.to", (this.to != null && !this.to.isEmpty()) ? DateUtil.formatDate2XW(this.to, "") : "");
    	params.put(prefix+".filter.contains", this.contains);
    	
    	return params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContains() {
		return contains;
	}

	public void setContains(String contains) {
		this.contains = contains;
	}

}
