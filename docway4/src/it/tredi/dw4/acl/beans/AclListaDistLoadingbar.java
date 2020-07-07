package it.tredi.dw4.acl.beans;

import it.tredi.dw4.adapters.ErrormsgFormsAdapter;
import it.tredi.dw4.beans.Page;
import it.tredi.dw4.docway.beans.DocWayTitles;
import it.tredi.dw4.docway.model.Resoconto;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;

public class AclListaDistLoadingbar extends AclLoadingbar {

	private Page holderPageBean = null;
	
	private String xml = "";
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

	private Resoconto resoconto = new Resoconto();
	private String customData0 = "";
	private String customData1 = "";
	private String customData2 = "";
	private String customData3 = "";
	private String customData4 = "";
	private String customData5 = "";
	
	public AclListaDistLoadingbar() throws Exception {
		super();
	}
	@Override
	public void init(XMLDocumento response) {
		super.init(response);
		this.xml = response.asXML();
		this.resoconto.init(XMLUtil.createDocument(getDocument().getDocument(), "/response/resoconto"));
		this.customData0 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData0");
		this.customData1 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData1");
		this.customData2 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData2");
		this.customData3 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData3");
		this.customData4 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData4");
		this.customData5 = XMLUtil.parseElementNode(getDocument().getDocument(), "/response/customData");
	}
	public Page getHolderPageBean() {
		return holderPageBean;
	}
	public void setHolderPageBean(Page holderPageBean) {
		this.holderPageBean = holderPageBean;
	}
	public Resoconto getResoconto() {
		return resoconto;
	}
	public void setResoconto(Resoconto resoconto) {
		this.resoconto = resoconto;
	}
	public String getCustomData0() {
		return customData0;
	}
	public void setCustomData0(String customData0) {
		this.customData0 = customData0;
	}
	public String getCustomData1() {
		return customData1;
	}
	public void setCustomData1(String customData1) {
		this.customData1 = customData1;
	}
	public String getCustomData2() {
		return customData2;
	}
	public void setCustomData2(String customData2) {
		this.customData2 = customData2;
	}
	public String getCustomData3() {
		return customData3;
	}
	public void setCustomData3(String customData3) {
		this.customData3 = customData3;
	}
	public String getCustomData4() {
		return customData4;
	}
	public void setCustomData4(String customData4) {
		this.customData4 = customData4;
	}
	public String getCustomData5() {
		return customData5;
	}
	public void setCustomData5(String customData5) {
		this.customData5 = customData5;
	}

	/**
	 * chiamata a chiusura del popup di loadingbar con refresh
	 * della lista titoli
	 */
	public String closeAndReloadListaTitoli() throws Exception {
		try {
			setActive(false);
			
			if (holderPageBean != null && holderPageBean instanceof DocWayTitles) {
				// se lista titoli, refresh della pagina corrente
				DocWayTitles titles = (DocWayTitles) holderPageBean;
				return titles.paginaSpecifica();
			}
			else { 
				// caricamento home DocWay
				return queryPage();
			}
		}
		catch (Throwable t) {
			handleErrorResponse(ErrormsgFormsAdapter.buildErrorResponse(t));
			getFormsAdapter().fillFormsFromResponse(getFormsAdapter().getLastResponse()); //restore delle form
			return null;
		}
	}
}
