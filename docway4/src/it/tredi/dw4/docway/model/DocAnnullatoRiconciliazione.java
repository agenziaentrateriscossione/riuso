package it.tredi.dw4.docway.model;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

public class DocAnnullatoRiconciliazione extends XmlEntity {
	private String oggetto;
	private String id_riconciliazione;
	private String data_riconciliazione;
	private String stato_riconciliazione;

	@Override
	public XmlEntity init(Document dom) {
		this.oggetto = 	 XMLUtil.parseElement(dom, "doc/oggetto");
		this.id_riconciliazione =  XMLUtil.parseAttribute(dom, "doc/riconciliazione/@id");
		this.data_riconciliazione = XMLUtil.parseAttribute(dom, "doc/riconciliazione/@data");
		this.stato_riconciliazione = XMLUtil.parseAttribute(dom, "doc/riconciliazione/@stato");
		return null;
	}
	
	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		return null;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getOggetto() {
		return oggetto;
	}

	public String getId_riconciliazione() {
		return id_riconciliazione;
	}

	public void setId_riconciliazione(String id_riconciliazione) {
		this.id_riconciliazione = id_riconciliazione;
	}

	public String getData_riconciliazione() {
		return data_riconciliazione;
	}

	public void setData_riconciliazione(String data_riconciliazione) {
		this.data_riconciliazione = data_riconciliazione;
	}

	public String getStato_riconciliazione() {
		return stato_riconciliazione;
	}

	public void setStato_riconciliazione(String stato_riconciliazione) {
		this.stato_riconciliazione = stato_riconciliazione;
	}
	
}
