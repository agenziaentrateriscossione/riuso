package it.tredi.dw4.docway.beans;

import java.util.List;

import org.dom4j.Document;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator;
import it.tredi.dw4.beans.DocEdit;
import it.tredi.dw4.beans.Showdoc;
import it.tredi.dw4.docway.adapters.DocWayProcedimentoFormsAdapter;
import it.tredi.dw4.docway.model.Option;
import it.tredi.dw4.docway.model.procedimenti.Procedimento;
import it.tredi.dw4.i18n.I18N;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;

/**
 * Bean di pagina (modale) di aggiunta di un procedimento
 */
public class DocWayProcedimentoAddPage extends DocEdit {
	
	private DocWayProcedimentoFormsAdapter formsAdapter;
	
	private Procedimento procedimento;
	
	/**
	 * Elenco di tipi di procedimento sui quali puo' operare l'utente corrente
	 */
	private List<Option> tipiProcedimento;
	
	private boolean visible = false;
	private Showdoc showdoc;
	
	private String xml;

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public DocWayProcedimentoAddPage() throws Exception {
		this.formsAdapter = new DocWayProcedimentoFormsAdapter(AdaptersConfigurationLocator.getInstance().getAdapterConfiguration("docwayService"));
	}

	@SuppressWarnings("unchecked")
	public void init(Document dom) {
		this.xml = dom.asXML();
		
		this.procedimento = new Procedimento();
		this.procedimento.init(dom);
		
		this.tipiProcedimento = XMLUtil.parseSetOfElement(dom, "/response/tipi_procedimento_select/option", new Option());
		
		this.visible = true;
	}

	public DocWayProcedimentoFormsAdapter getFormsAdapter() {
		return formsAdapter;
	}

	/**
	 * Avvio del procedimento (aggiornamento del documento)
	 */
	@Override
	public String saveDocument() throws Exception {
		if (procedimento.getTipo_procedimento() == null || procedimento.getTipo_procedimento().isEmpty()) {
			this.setErrorMessage("templateForm:tipo_procedimento_select", I18N.mrs("acl.requiredfield") + " '" + I18N.mrs("dw4.tipo_procedimento") + "'");
			return null;
		}
		formsAdapter.getDefaultForm().addParams(this.procedimento.asFormAdapterParams(""));
		formsAdapter.confirmProcedimento();
		XMLDocumento response = this.formsAdapter.getDefaultForm().executePOST(getUserBean());
		if (handleErrorResponse(response)) {
			formsAdapter.fillFormsFromResponse(formsAdapter.getLastResponse()); //restore delle form
			return null;
		}
		visible = false;
		if (null != showdoc) {
			showdoc._reloadWithoutNavigationRule();
		}
		return null;
	}

	/**
	 * Annullamento dell'avvio del procedimento sul documento
	 */
	@Override
	public String clearDocument() throws Exception {
		visible = false;
		setSessionAttribute("docwayProcedimentoAddPage", null);
		return null;
	}

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}

	public List<Option> getTipiProcedimento() {
		return tipiProcedimento;
	}

	public void setTipiProcedimento(List<Option> tipiProcedimento) {
		this.tipiProcedimento = tipiProcedimento;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Showdoc getShowdoc() {
		return showdoc;
	}

	public void setShowdoc(Showdoc showdoc) {
		this.showdoc = showdoc;
	}
	
}
