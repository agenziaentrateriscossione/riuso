package it.tredi.dw4.acl.beans;

import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.dom4j.Document;

import it.tredi.dw4.acl.adapters.AclDocumentFormsAdapter;
import it.tredi.dw4.acl.model.ListaDistribuzione;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.docway.beans.DocWayLoadingbar;
import it.tredi.dw4.utils.XMLDocumento;

public class ShowdocListaDist extends AclShowdoc {
	private AclDocumentFormsAdapter formsAdapter;
	private ListaDistribuzione listaDistribuzione;
	private String xml;
	private String action;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public ShowdocListaDist() throws Exception {
		this.formsAdapter = new AclDocumentFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("aclService"));
	}
	
	public void init(Document domDocumento) {
    	xml = domDocumento.asXML();
    	listaDistribuzione = new ListaDistribuzione();
    	listaDistribuzione.init(domDocumento);
    	
    	// inizializzazione di componenti common
    	initCommons(domDocumento);
    }	
	
	public AclDocumentFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}
	
	public void reload(ComponentSystemEvent event) throws Exception {
		reload();
	}
	
	public void reload() throws Exception {
		super._reload(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/acl/showdoc@lista_dist");
	}

	public ListaDistribuzione getListaDistribuzione() {
		return listaDistribuzione;
	}

	public void setListaDistribuzione(ListaDistribuzione listaDistribuzione) {
		this.listaDistribuzione = listaDistribuzione;
	}
	@Override
	public String remove() throws Exception {
		formsAdapter.rimuoviListaDistribuzione(listaDistribuzione);

		XMLDocumento response = getFormsAdapter().getDefaultForm().executePOST(getUserBean());
		if (handleErrorResponse(response)) {
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); // restore delle form
			return null;
		}

		String verbo = response.getAttributeValue("/response/@verbo");
		if (verbo.equals("loadingbar")) {
			AclListaDistLoadingbar docWayLoadingbar = new AclListaDistLoadingbar();
			docWayLoadingbar.getFormsAdapter().fillFormsFromResponse(response);
			docWayLoadingbar.init(response);
			docWayLoadingbar.setHolderPageBean(this);
			setLoadingbar(docWayLoadingbar);
			docWayLoadingbar.setActive(true);
			return null;
		}
		formsAdapter.fillFormsFromResponse(getFormsAdapter().getLastResponse());
		return "showdoc@lista_dist@reload";
	}
}
