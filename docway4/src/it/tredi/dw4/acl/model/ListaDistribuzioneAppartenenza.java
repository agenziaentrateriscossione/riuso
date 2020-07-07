package it.tredi.dw4.acl.model;

import java.util.HashMap;
import java.util.Map;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

import org.dom4j.Document;

public class ListaDistribuzioneAppartenenza extends XmlEntity {
	private String cod;
	private String nome;
	private String physDoc;
	
	public ListaDistribuzioneAppartenenza() {}
    
	public ListaDistribuzioneAppartenenza(String xmlListaDistAppartenenza) throws Exception {
        this.init(XMLUtil.getDOM(xmlListaDistAppartenenza));
    }
    
    public ListaDistribuzioneAppartenenza init(Document domListaDistAppartenenza) {
    	this.cod = XMLUtil.parseAttribute(domListaDistAppartenenza, "lista_dist_appartenenza/@cod");
    	this.nome = XMLUtil.parseAttribute(domListaDistAppartenenza, "lista_dist_appartenenza/@nome");
    	this.physDoc = XMLUtil.parseAttribute(domListaDistAppartenenza, "lista_dist_appartenenza/@physDoc");
        return this;
    }

	public Map<String, String> asFormAdapterParams(String prefix){
    	if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(prefix + ".@cod", this.cod);
    	params.put(prefix + ".@nome", this.nome);
    	params.put(prefix + ".@physDoc", this.physDoc);
    	return params;
    }
    
    public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    public String getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(String physDoc) {
		this.physDoc = physDoc;
	}

}
