package it.tredi.dw4.acl.beans;

import it.tredi.dw4.acl.adapters.AclQueryFormsAdapter;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;

import org.dom4j.Document;

public class QueryListaDist extends AclQuery {
	
	private String lista_dist_nrecord;
	private String lista_dist_nome;
	private String lista_dist_descrizioneld;
	private String focusElement = "lista_dist_nome";
	
	private AclQueryFormsAdapter formsAdapter;
	
	public QueryListaDist() throws Exception {
		this.formsAdapter = new AclQueryFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("aclService"));
	}
	
	public void init(Document dom) {
    	//DO NOTHING
    }	
	
	public AclQueryFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}
	
	//TEMP
	public String queryPlain() throws Exception {
		String query =  "";
		query +=  addQueryField("xml,/lista_dist/@nrecord", lista_dist_nrecord);
		query +=  addQueryField("xml,/lista_dist/nome", lista_dist_nome);
		query +=  addQueryField("xml,/lista_dist/descrizione_ld", lista_dist_descrizioneld);
		if (query.endsWith(" AND "))
			query = query.substring(0, query.length()-4);
		else if("".equals(query.trim())) query = "[UD,/xw/@UdType]=lista_dist";
		
		this.formsAdapter.getDefaultForm().addParam("qord", "xml(xpart:/lista_dist/nome)"); // TODO Andrebbe specificato all'interno di un file di properties
		
		return queryPlain(query);
	}
	
	public String getLista_dist_nrecord() {
		return lista_dist_nrecord;
	}

	public void setLista_dist_nrecord(String lista_dist_nrecord) {
		this.lista_dist_nrecord = lista_dist_nrecord;
	}

	public String getLista_dist_nome() {
		return lista_dist_nome;
	}

	public void setLista_dist_nome(String lista_dist_nome) {
		this.lista_dist_nome = lista_dist_nome;
	}

	public String getLista_dist_descrizioneld() {
		return lista_dist_descrizioneld;
	}

	public void setLista_dist_descrizioneld(String lista_dist_descrizioneld) {
		this.lista_dist_descrizioneld = lista_dist_descrizioneld;
	}

	public String resetQuery() {
		super.resetAddonsQuery();
		
		this.lista_dist_nrecord = null;
		this.lista_dist_nome = null;
		this.lista_dist_descrizioneld = null;
		return null;
	}

	
	public void setFocusElement(String focusElement) {
		this.focusElement = focusElement;
	}

	public String getFocusElement() {
		return focusElement;
	}
	
	public String openIndexNome() throws Exception {
		this.focusElement = "lista_dist_nome";
		this.openIndex("lista_dist_nome", this.lista_dist_nome, "0", " ");
		return null;
	}
	public String openIndexDescrizione() throws Exception {
		this.focusElement = "lista_dist_descrizioneld";
		this.openIndex("lista_dist_descrizioneld", this.lista_dist_descrizioneld, "0", null);
		return null;
	}

}
