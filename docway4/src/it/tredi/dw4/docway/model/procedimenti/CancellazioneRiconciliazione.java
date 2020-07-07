package it.tredi.dw4.docway.model.procedimenti;

import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Informazioni di annullamento di una riconciliazione
 * @author mbernardini
 *
 */
public class CancellazioneRiconciliazione extends XmlEntity {

	private String operatore;
	
	private String codOperatore;
	
	private String data;
	
	private String ora;
	
	private String motivazione;
	
	@Override
	public XmlEntity init(Document dom) {
		this.operatore = XMLUtil.parseAttribute(dom, "cancellazione/@operatore");
		this.codOperatore = XMLUtil.parseAttribute(dom, "cancellazione/@cod_operatore");
		this.data = XMLUtil.parseAttribute(dom, "cancellazione/@data");
    	this.ora = XMLUtil.parseAttribute(dom, "cancellazione/@ora");
    	this.motivazione = XMLUtil.parseElementNode(dom, "cancellazione");
    	return this;
	}

	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		return null;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getCodOperatore() {
		return codOperatore;
	}

	public void setCodOperatore(String codOperatore) {
		this.codOperatore = codOperatore;
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

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

}
