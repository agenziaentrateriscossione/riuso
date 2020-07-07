package it.tredi.dw4.docway.beans;

import org.dom4j.Document;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.beans.DocEdit;
import it.tredi.dw4.beans.Showdoc;
import it.tredi.dw4.docway.adapters.DocWayAnnullaRiconciliazioneFormsAdapter;
import it.tredi.dw4.docway.model.DocAnnullatoRiconciliazione;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.utils.Const;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Annullamento del processo di riconciliazione su un documento
 */
public class DocWayAnnullamentoRiconciliazione extends DocEdit {
	private DocWayAnnullaRiconciliazioneFormsAdapter formsAdapter;
	private boolean visible = false;
	private DocAnnullatoRiconciliazione docAnnullato = new DocAnnullatoRiconciliazione();
	private String text;
	private String userInfo;
	private String currDate;
	private Showdoc showdoc;
	
	private String xml;
	
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public DocWayAnnullamentoRiconciliazione() throws Exception {
		this.formsAdapter = new DocWayAnnullaRiconciliazioneFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
	}
	
	public void init(Document dom) {
    	xml = dom.asXML();
    	visible = true;
    	this.userInfo = XMLUtil.parseAttribute(dom, "response/@userInfo", "");
    	this.userInfo = this.userInfo.replaceAll("\\(", "- ").replaceAll("\\)", "");
    	this.currDate = XMLUtil.parseAttribute(dom, "response/@currDate", "");
		docAnnullato = new DocAnnullatoRiconciliazione();
		docAnnullato.init(dom);
    }	
	
	public DocWayAnnullaRiconciliazioneFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}

	/**
	 * annullamento della riconciliazione sul documento
	 * @return
	 * @throws Exception
	 */
	@Override
	public String saveDocument() throws Exception {
		// controllo di obbligatorieta' sul campo 'note'
		if (text == null || text.trim().length() == 0) {
			this.setErrorMessage("templateForm:annullaRiconciliazione_text", I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dw4.note") + "'");
			return null;
		}
		
		formsAdapter.confirmAnnullamentoRiconciliazione(text);
		XMLDocumento response = this.formsAdapter.getDefaultForm().executePOST(getUserBean());
		if (handleErrorResponse(response, Const.MSG_LEVEL_ERROR)) {
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
		visible = false;
		if (null != showdoc) showdoc.reload();
		return null;		
	}

	@Override
	public String clearDocument() throws Exception {
		visible = false;
		setSessionAttribute("docwayAnnullamentoRiconciliazione", null);
		return null;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setShowdoc(Showdoc showdoc) {
		this.showdoc = showdoc;
	}

	public Showdoc getShowdoc() {
		return showdoc;
	}

	public DocAnnullatoRiconciliazione getDocAnnullato() {
		return docAnnullato;
	}

	public void setDocAnnullato(DocAnnullatoRiconciliazione docAnnullato) {
		this.docAnnullato = docAnnullato;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public String getCurrDate() {
		return currDate;
	}
	
}
