package it.tredi.dw4.docway.model.procedimenti;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Informazioni di riconciliazione associate ad un documento
 */
public class Riconciliazione extends XmlEntity {

	/**
	 * Identificativo di riconciliazione
	 */
	private String id;
	
	/**
	 * Data di riconciliazione
	 */
	private String data;
	
	/**
	 * Stato di lavorazione della riconciliazione
	 */
	private String stato;
	
	/**
	 * Eventuale numero di procedimento alla quale la riconciliazione si riferisce 
	 */
	private String num_procedimento;
	
	/**
	 * Eventuali informazioni di cancellazione della riconciliazione
	 */
	private CancellazioneRiconciliazione cancellazione = new CancellazioneRiconciliazione();

	@Override
	public XmlEntity init(Document dom) {
		this.id = XMLUtil.parseAttribute(dom, "riconciliazione/@id");
    	this.data = XMLUtil.parseAttribute(dom, "riconciliazione/@data");
    	this.stato = XMLUtil.parseAttribute(dom, "riconciliazione/@stato");
    	this.num_procedimento = XMLUtil.parseAttribute(dom, "riconciliazione/@num_procedimento");
    	
    	this.cancellazione.init(XMLUtil.createDocument(dom, "riconciliazione/cancellazione"));
    	
        return this;
	}

	@Override
	public Map<String, String> asFormAdapterParams(String prefix) {
		if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	
    	params.put(prefix+".@id", this.id);
    	params.put(prefix+".@data", this.data);
    	params.put(prefix+".@stato", this.stato);
    	params.put(prefix+".@num_procedimento", this.num_procedimento);
    	
    	return params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNum_procedimento() {
		return num_procedimento;
	}

	public void setNum_procedimento(String numProcedimento) {
		this.num_procedimento = numProcedimento;
	}

	public CancellazioneRiconciliazione getCancellazione() {
		return cancellazione;
	}

	public void setCancellazione(CancellazioneRiconciliazione cancellazione) {
		this.cancellazione = cancellazione;
	}
	
}
