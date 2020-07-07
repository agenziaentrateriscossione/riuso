package it.tredi.dw4.acl.model;

import java.util.HashMap;
import java.util.Map;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

import org.dom4j.Document;

public class Qualifica extends XmlEntity {
	private String text;
    
	public Qualifica() {}
    
	public Qualifica(String xmlQualifica) throws Exception {
        this.init(XMLUtil.getDOM(xmlQualifica));
    }
    
    public Qualifica init(Document domQualifica) {
		this.text = XMLUtil.parseElement(domQualifica, "qualifica");
        return this;
    }
    
    public Map<String, String> asFormAdapterParams(String prefix){
    	if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(prefix, this.text);
    	return params;
    }
    
    public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}

