package it.tredi.dw4.acl.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;

import it.tredi.dw4.acl.adapters.AclDocumentFormsAdapter;
import it.tredi.dw4.acl.model.Account;
import it.tredi.dw4.acl.model.Archive;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.adapters.DocumentFormsAdapter;
import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.beans.ReloadMsg;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.XMLDocumento;

public class ShowdocAccount extends AclShowdoc{
	private AclDocumentFormsAdapter formsAdapter;
	private String xml;
	private Account account = new Account();
	
	public ShowdocAccount() throws Exception {
		this.formsAdapter = new AclDocumentFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("aclService"));
	}

	@Override
	public void init(Document dom) {
		this.setXml(dom.asXML());
		this.account.init(dom);
		
		// inizializzazione di componenti common
		initCommons(dom);
	}

	@Override
	public DocumentFormsAdapter getFormsAdapter() {
		return this.formsAdapter;
	}

	@Override
	public void reload() throws Exception {
		super._reload(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/acl/showdoc@account");
	}
	
	//getter / setter
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	/**
	 * Abilitazione multipla utenti per Acciunt
	 * @param archive Identificatore dell'archivio per il quale operare l'abilitazione
	 * @param users	Elenco degli utenti da abilitare
	 * @param role	Ruolo da assegnare agli utenti
	 * @return
	 * @throws Exception
	 */
	//public String abilitaUtenti(String archive, String users, String role) throws Exception {
	public String abilitaUtenti(Archive archive) throws Exception {
		if ( null == archive ) return null;
		try {
			formsAdapter.abilitaUtenti(archive.getAlias(), archive.getNuoviAmministratori(), archive.getNuoviArchivisti(), archive.getNuoviLettori());
			XMLDocumento response = formsAdapter.getDefaultForm().executePOST(getUserBean());
			if (handleErrorResponse(response)) {
				formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
				return null;
			}
			
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			
			// lettura del messaggio di ritorno
			ReloadMsg message = new ReloadMsg();
			message.setActive(true);
			message.init(response.getDocument());
			message.setLevel(Const.MSG_LEVEL_SUCCESS);
			
			reload(); // reload del documento
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("reloadmsg", message);
			
			return null;
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;			
		}
	}
}
