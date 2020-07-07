package it.tredi.dw4.docway.adapters;

import org.dom4j.DocumentException;

import it.tredi.dw4.adapters.AdaptersConfigurationLocator.AdapterConfig;
import it.tredi.dw4.adapters.DocEditFormsAdapter;
import it.tredi.dw4.utils.XMLDocumento;

public class DocWayAnnullaRiconciliazioneFormsAdapter extends DocEditFormsAdapter {

	public DocWayAnnullaRiconciliazioneFormsAdapter(AdapterConfig config) {
		super(config);
	}

	@Override
	protected void fillDefaultFormFromResponse(XMLDocumento response) throws DocumentException {
		super.fillDefaultFormFromResponse(response);
		
		//Element root = response.getRootElement();
	}
	
	
	public void confirmAnnullamentoRiconciliazione(String text){
		defaultForm.addParam("verbo", "documento_response");
		defaultForm.addParam("xverb", "@annullaRiconciliazione");
		defaultForm.addParam("doc_responseText", text);
	}
}
