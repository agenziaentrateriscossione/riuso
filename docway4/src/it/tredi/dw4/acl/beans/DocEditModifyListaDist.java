package it.tredi.dw4.acl.beans;

import org.dom4j.Document;

import it.tredi.dw4.acl.adapters.AclDocEditFormsAdapter;
import it.tredi.dw4.acl.model.ListaDistribuzione;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.utils.XMLDocumento;

public class DocEditModifyListaDist extends AclDocEdit {
	private AclDocEditFormsAdapter formsAdapter;
	private ListaDistribuzione listaDistribuzione;

	private String xml;
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml){
		this.xml = xml;
	}
	public DocEditModifyListaDist() throws Exception {
		this.formsAdapter = new AclDocEditFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("aclService"));
	}
	
	public void init(Document domDocumento) {
    	xml = domDocumento.asXML();
    	this.listaDistribuzione = new ListaDistribuzione();
    	this.listaDistribuzione.init(domDocumento);    			
    }	
	
	public AclDocEditFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}

	@Override
	public String saveDocument() throws Exception {
		try {
			if(checkRequiredField()) return null;
			formsAdapter.getDefaultForm().addParams(this.listaDistribuzione.asFormAdapterParams(""));
			
			XMLDocumento response = super._saveDocument("lista_dist", "list_of_struttur");
		
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}
			buildSpecificShowdocPageAndReturnNavigationRule("lista_dist", response);
			return "showdoc@lista_dist@reload";
	}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;			
		}
	}
	
	@Override
	public String clearDocument() throws Exception {
		try {
			XMLDocumento response = super._clearDocument();
			
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}
			buildSpecificShowdocPageAndReturnNavigationRule("lista_dist", response);		
			return "showdoc@lista_dist@reload";
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;			
		}
			
	}

	public ListaDistribuzione getListaDistribuzione() {
		return listaDistribuzione;
	}

	public void setListaDistribuzione(ListaDistribuzione listaDistribuzione) {
		this.listaDistribuzione = listaDistribuzione;
	}
	public boolean checkRequiredFieldLookup() {
		boolean result = false;
		return result;
	}
	
	public boolean checkRequiredField(){
		boolean result = false;
		if (listaDistribuzione.getNome() == null || "".equals(listaDistribuzione.getNome().trim())) {
			this.setErrorMessage("templateForm:lista_dist_nome", I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("acl.name") + "'");
		    result = true;
		}
		return result;
	}}
