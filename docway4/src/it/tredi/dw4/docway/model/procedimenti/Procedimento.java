package it.tredi.dw4.docway.model.procedimenti;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.docway.model.Legislatura;
import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Informazioni relative ad un procedimento avviato su un documento
 */
public class Procedimento extends XmlEntity {
	
	/**
	 * Anno di avvio del procedimento
	 */
	private String anno;
	/**
	 * Data di avvio del procedimento
	 */
	private String data_avvio;
	/**
	 * Legislatura all'interno della quale e' avviato il procedimento
	 */
	private Legislatura legislatura = new Legislatura();
	/**
	 * Tipo di procedimento
	 */
	private String tipo_procedimento;
	/**
	 * Oggetto del procedimento
	 */
	private String oggetto;
	/**
	 * Descrzione del procedimento
	 */
	private String descrizione;
	/**
	 * Numero associato al procedimento (dipendente dalla tipologia di procedimento)
	 */
	private String numero;
	/**
	 * Numero progressivo del procedimento (indipendentemente dal tipo) sull'anno corrente
	 */
	private String numero_procedimento;
	/**
	 * Stato di avvio del workflow legato al procedimento su API
	 */
	private String wf;
	/**
	 * Eventuale data di avvio del workflow
	 */
	private String data_avvio_wf;
	/**
	 * Eventuale ora di avvio del workflow
	 */
	private String ora_avvio_wf;
	/**
	 * Informazioni che identificano l'operatore che ha avviato il procedimento
	 */
	private InfoOperatore operatore = new InfoOperatore();
	
	/**
	 * TRUE se il procedimento e' "linkato" a causa di una riconciliazione, FALSE altrimenti
	 */
	private boolean fromRiconciliazione;
	
	public Procedimento() {}

	public Procedimento(String xml) throws Exception {
        this.init(XMLUtil.getDOM(xml));
    }

    public Procedimento init(Document dom) {
    	this.anno = XMLUtil.parseAttribute(dom, "procedimento/@anno");
    	this.data_avvio = XMLUtil.parseAttribute(dom, "procedimento/@data_avvio");
    	this.legislatura.init(XMLUtil.createDocument(dom, "//procedimento/legislatura"));
    	this.tipo_procedimento = XMLUtil.parseAttribute(dom, "procedimento/@tipo_procedimento");
    	this.oggetto = XMLUtil.parseElement(dom, "procedimento/oggetto", true);
    	this.descrizione = XMLUtil.parseElement(dom, "procedimento/descrizione", false);
    	this.numero = XMLUtil.parseAttribute(dom, "procedimento/@numero");
    	this.numero_procedimento = XMLUtil.parseAttribute(dom, "procedimento/@numero_procedimento");
    	this.wf = XMLUtil.parseAttribute(dom, "procedimento/@wf");
    	this.data_avvio_wf = XMLUtil.parseAttribute(dom, "procedimento/@data_avvio_wf");
    	this.ora_avvio_wf = XMLUtil.parseAttribute(dom, "procedimento/@ora_avvio_wf");
    	this.operatore.init(XMLUtil.createDocument(dom, "//procedimento/operatore"));
    	this.fromRiconciliazione = XMLUtil.parseAttribute(dom, "procedimento/@fromRiconciliazione").equalsIgnoreCase("true");
    	
        return this;
    }

    public Map<String, String> asFormAdapterParams(String prefix){
    	if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	
    	params.put(prefix+".@anno", this.anno);
    	params.put(prefix+".@data_avvio", this.data_avvio);
    	params.putAll(legislatura.asFormAdapterParams(".legislatura"));
    	params.put(prefix+".@tipo_procedimento", this.tipo_procedimento);
    	// params.put(prefix+".oggetto", this.oggetto); // campo non compilato
    	params.put(prefix+".descrizione", this.descrizione);
    	// params.put(prefix+".@numero", this.numero); // campo automatico (seriale)
    	// params.put(prefix+".@numero_procedimento", this.numero_procedimento); // campo automatico (seriale)
    	
    	return params;
    }

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getData_avvio() {
		return data_avvio;
	}

	public void setData_avvio(String data_avvio) {
		this.data_avvio = data_avvio;
	}

	public Legislatura getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(Legislatura legislatura) {
		this.legislatura = legislatura;
	}

	public String getTipo_procedimento() {
		return tipo_procedimento;
	}

	public void setTipo_procedimento(String tipo_procedimento) {
		this.tipo_procedimento = tipo_procedimento;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumero_procedimento() {
		return numero_procedimento;
	}

	public void setNumero_procedimento(String numero_procedimento) {
		this.numero_procedimento = numero_procedimento;
	}

	public String getWf() {
		return wf;
	}

	public void setWf(String wf) {
		this.wf = wf;
	}

	public String getData_avvio_wf() {
		return data_avvio_wf;
	}

	public void setData_avvio_wf(String data_avvio_wf) {
		this.data_avvio_wf = data_avvio_wf;
	}

	public String getOra_avvio_wf() {
		return ora_avvio_wf;
	}

	public void setOra_avvio_wf(String ora_avvio_wf) {
		this.ora_avvio_wf = ora_avvio_wf;
	}

	public InfoOperatore getOperatore() {
		return operatore;
	}

	public void setOperatore(InfoOperatore operatore) {
		this.operatore = operatore;
	}

	public boolean isFromRiconciliazione() {
		return fromRiconciliazione;
	}

	public void setFromRiconciliazione(boolean fromRiconciliazione) {
		this.fromRiconciliazione = fromRiconciliazione;
	}
    
}

