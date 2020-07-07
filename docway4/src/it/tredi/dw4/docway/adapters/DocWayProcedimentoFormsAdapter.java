package it.tredi.dw4.docway.adapters;

import org.dom4j.DocumentException;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator.AdapterConfig;
import it.tredi.dw4.adapters.DocEditFormsAdapter;
import it.tredi.dw4.utils.XMLDocumento;

public class DocWayProcedimentoFormsAdapter extends DocEditFormsAdapter {

	public DocWayProcedimentoFormsAdapter(AdapterConfig config) {
		super(config);
	}

	@Override
	protected void fillDefaultFormFromResponse(XMLDocumento response) throws DocumentException {
		super.fillDefaultFormFromResponse(response);
		
		//Element root = response.getRootElement();
		// TODO eventuali parametri aggiuntivi del formsAdapter
	}

	/**
	 * Conferma di avvio di un procedimento
	 */
	public void confirmProcedimento()	{
		this.defaultForm.addParam("verbo", "procedimento_response");
		this.defaultForm.addParam("xverb", "@add");
	}
	
}
