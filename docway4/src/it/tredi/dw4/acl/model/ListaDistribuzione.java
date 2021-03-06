package it.tredi.dw4.acl.model;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.dom4j.Document;

public class ListaDistribuzione extends XmlEntity {
	private Document response;
	private String nrecord = ".";
	private String nome;
	private String id = ".";
	private String descrizione;
	private String pers_ass_count;
	private String physDoc;
	
	private List<Modifica> modifiche;
	private Creazione creazione = new Creazione();
	private UltimaModifica ultima_modifica = new UltimaModifica();

	private List<Listof_rights> listof_rights;
	private HashMap<String, Right> rights = new HashMap<String, Right>();
	private List<Right> personal_rights;

	
	public ListaDistribuzione() {}
    
    public ListaDistribuzione(String xmlPersonaInterna) throws Exception {
        this.init(XMLUtil.getDOM(xmlPersonaInterna));
    }
    
    @SuppressWarnings("unchecked")
	public ListaDistribuzione init(Document domListaDistribuzione) {
    	this.response = 			domListaDistribuzione;
    	this.nrecord = 				XMLUtil.parseAttribute(domListaDistribuzione, "lista_dist/@nrecord", ".");
    	this.nome = 				XMLUtil.parseElement(domListaDistribuzione, "lista_dist/nome");
    	this.descrizione = 			XMLUtil.parseElement(domListaDistribuzione, "lista_dist/descrizione_ld", false);
    	this.id = 					XMLUtil.parseAttribute(domListaDistribuzione, "lista_dist/@id", ".");
    	this.pers_ass_count = 		XMLUtil.parseAttribute(domListaDistribuzione, "lista_dist/@pers_ass_count");
    	this.physDoc = 				XMLUtil.parseAttribute(domListaDistribuzione, "lista_dist/@physDoc");
    	this.modifiche = 			XMLUtil.parseSetOfElement(domListaDistribuzione, "//lista_dist/storia/modifica", new Modifica());
    	this.creazione.init(XMLUtil.createDocument(domListaDistribuzione, "//lista_dist/storia/creazione"));
    	this.ultima_modifica.init(XMLUtil.createDocument(domListaDistribuzione, "//lista_dist/storia/ultima_modifica"));
    	this.personal_rights = XMLUtil.parseSetOfElement(domListaDistribuzione, "//lista_dist/personal_rights/right", new Right());
    	initListOfRights();
    	if (personal_rights.size() > 0) fillPersonalRights();
    	
    	// mbernardini 03/04/2015 : spostata la valutazione dei disabled perche' non ancora caricati i diritti del gruppo
    	evaluateDisabled();
    	
        return this;
    }
    public List<Listof_rights> getListof_rights() {
		return listof_rights;
	}

	public void setListof_rights(List<Listof_rights> listof_rights) {
		this.listof_rights = listof_rights;
	}

	public List<Right> getPersonal_rights() {
		return personal_rights;
	}

	public void setPersonal_rights(List<Right> personal_rights) {
		this.personal_rights = personal_rights;
	}

	@SuppressWarnings("unchecked")
	private void initListOfRights() {
    	this.listof_rights = XMLUtil.parseSetOfElement(response, "//listof_rights", new Listof_rights());
    	resetRights();
	}
    private void resetRights(){
    	List<Right> group_rights = new ArrayList<Right>();
    	this.rights.clear();
    	boolean openedFirst = true;
    	for (Iterator<Listof_rights> iter = listof_rights.iterator(); iter.hasNext();) {
			Listof_rights liste = (Listof_rights) iter.next();
			List<Group> gruppi = liste.getGroups_gr();
			for (Iterator<Group> iterator = gruppi.iterator(); iterator.hasNext();) {
				Group gruppo = (Group) iterator.next();
				gruppo.setOpened(openedFirst);
				openedFirst = false;
				group_rights.addAll(gruppo.getRights());
				List<Group> sottogruppi = gruppo.getGroups();
				for (Iterator<Group> iterator2 = sottogruppi.iterator(); iterator2.hasNext();) {
					Group sottogruppo = (Group) iterator2.next();
					group_rights.addAll(sottogruppo.getRights());
				}
			}
		}
    	for (Iterator<Right> iterator = group_rights.iterator(); iterator.hasNext();) {
			Right right = (Right) iterator.next();
			this.rights.put(right.getCod(), right);
		}
    	// mbernardini 03/04/2015 : spostata la valutazione dei disabled perche' non ancora caricati i diritti del gruppo
    	//evaluateDisabled();
    }
    public void fillPersonalRights(){
    	for (Iterator<Right> iterator = personal_rights.iterator(); iterator.hasNext();) {
			Right right = (Right) iterator.next();
			if (null != this.rights.get(right.getCod()))
				this.rights.get(right.getCod()).setValue(right.getValue());
		}
    }
    public Map<String, String> asFormAdapterParams(String prefix){
    	if (null == prefix) prefix = "";
    	Map<String, String> params = new HashMap<String, String>();
    	params.put(prefix + ".@nrecord", this.nrecord);
    	params.put(prefix + ".nome", this.nome);
    	params.put(prefix + ".descrizione_ld", this.descrizione);
    	params.put(prefix + ".@id", (this.id != null) ? this.id.trim() : null);
    	for ( Iterator<?> i = this.rights.entrySet().iterator() ; i.hasNext() ; ) {  
    	    @SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) i.next();  
    	    Right right = (Right) e.getValue();
			if(right.getType().equals("alfa"))
				params.put("*tRight_"+right.getCod(), right.getValue());
			else if(right.getSelected())
				params.put("*right_"+right.getCod(), "1");
		}
    	return params;
    }
    
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setNrecord(String nrecord) {
		this.nrecord = nrecord;
	}

	public String getNrecord() {
		return nrecord;
	}

	public void setModifiche(List<Modifica> modifiche) {
		this.modifiche = modifiche;
	}

	public List<Modifica> getModifiche() {
		return modifiche;
	}

	public void setCreazione(Creazione creazione) {
		this.creazione = creazione;
	}

	public Creazione getCreazione() {
		return creazione;
	}

	public void setUltima_modifica(UltimaModifica ultima_modifica) {
		this.ultima_modifica = ultima_modifica;
	}

	public UltimaModifica getUltima_modifica() {
		return ultima_modifica;
	}

	public void setRights(HashMap<String, Right> rights) {
		this.rights = rights;
	}

	public HashMap<String, Right> getRights() {
		return rights;
	}
	
	public String modifyRight(){
		Right right = (Right) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("right");
		boolean newVal = !right.getSelected();
		
		// mbernardini 04/09/2017 : migliorata la modifica di diritti di tipo 'alfa'
		if (right.getType() != null && right.getType().equals("alfa")) {
			if (right.getValue().equals("") || right.getValue().equals("*NHL*"))
				newVal = false;
			else
				newVal = true;
		}
		else
			right.setValue(String.valueOf(newVal));

		if (newVal){
			List<Oncheck> operation = right.getOnchecks();
			for (Iterator<Oncheck> iterator = operation.iterator(); iterator.hasNext();) {
				Oncheck oncheck = (Oncheck) iterator.next();
				Right changed = this.rights.get(oncheck.getWhat());
				if( oncheck.getAction().equals("check") ) 
					changed.setValue("true");
				else if( oncheck.getAction().equals("uncheck") )
					changed.setValue("false");
			}
		}
		else{
			List<Onuncheck> operation = right.getOnunchecks();
			for (Iterator<Onuncheck> iterator = operation.iterator(); iterator.hasNext();) {
				Onuncheck onuncheck = (Onuncheck) iterator.next();
				Right changed = this.rights.get(onuncheck.getWhat());
				if( onuncheck.getAction().equals("check") ) 
					changed.setValue("true");
				else if( onuncheck.getAction().equals("uncheck") )
					changed.setValue("false");
			}
		}
		evaluateDisabled();
		return null;
	}
	
	public String evaluateDisabled(){
		for ( Iterator<?> i = this.rights.entrySet().iterator() ; i.hasNext() ; ) {  
    	    @SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry) i.next();  
    	    Right right = (Right) e.getValue();
    	    String disabled = right.getDisabled().getIfVar();
    	    if (null != disabled && disabled.trim().length() > 0){
		        JexlEngine jexl = new JexlEngine();
		        // Create an expression object
		        Expression ex = jexl.createExpression( disabled );
		
		        // Create a context and add data
		        JexlContext jc = new MapContext();
		        jc.set("rights", this.rights);
		        jc.set("this", this);
		
		        // Now evaluate the expression, getting the result
		        boolean enabled = (Boolean) ex.evaluate(jc);
	        	right.setDisable(enabled);
	        	if ( enabled ) right.setValue("false");
    	    }
		}
        return null;
	}

	public void setPers_ass_count(String pers_ass_count) {
		this.pers_ass_count = pers_ass_count;
	}

	public String getPers_ass_count() {
		return pers_ass_count;
	}

	public void setPhysDoc(String physDoc) {
		this.physDoc = physDoc;
	}

	public String getPhysDoc() {
		return physDoc;
	}
}
