package it.tredi.dw4.docway.model.pecmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Informazioni relative alla gestione del doc nel flusso PEC MANAGER
 * @author dpranteda
 */
public class PecManager extends XmlEntity {
	
	public static final String PEC_MANAGER_STATUS_ARCHIVIATO = "archiviato";
	public static final String PEC_MANAGER_STATUS_ASSEGNATO = "assegnato";
	public static final String PEC_MANAGER_STATUS_RIGETTATO = "rigettato";
	public static final String PEC_MANAGER_STATUS_PRESO_IN_CARICO = "preso_in_carico";
	public static final String PEC_MANAGER_STATUS_LAVORATO = "lavorato";
	
	private String status;
	private Esecutore esecutore = new Esecutore();
	private List<Step> steps = new ArrayList<Step>();
	
	@SuppressWarnings("unchecked")
	@Override
	public XmlEntity init(Document dom) {
		this.status = XMLUtil.parseStrictAttribute(dom, "pecmanager/@status");
		this.esecutore.init(XMLUtil.createDocument(dom, "pecmanager"));
		this.steps = XMLUtil.parseSetOfElement(dom, "/pecmanager/flusso/step", new Step()); 
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
	
	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
}
