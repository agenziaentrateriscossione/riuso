package it.tredi.dw4.docway.model.procedimenti;

import java.util.ArrayList;
import java.util.List;

import it.tredi.dw4.i18n.I18N;

/**
 * Definizione dei campi di ricerca su riconciliazione di documenti
 */
public class RiconciliazioneFilter {

	private String idRiconciliazione = "";
	
	private List<StatoRiconciliazione> stati;
	
	private String data_da = "";
	
	private String data_a = "";
	
	/**
	 * Costruttore vuoto
	 */
	public RiconciliazioneFilter() {
		// Definizione degli stati di riconciliazione che devono essere ricercabili dalla pagina di ricerca
		this.stati = new ArrayList<StatoRiconciliazione>();
		this.stati.add(new StatoRiconciliazione("lavorazione", I18N.mrs("dw4.ric_api_da_lavorare")));
		this.stati.add(new StatoRiconciliazione("cancellato", I18N.mrs("dw4.ric_api_cancellato")));
		this.stati.add(new StatoRiconciliazione("protocollato", I18N.mrs("dw4.ric_api_protocollato")));
		this.stati.add(new StatoRiconciliazione("annullato", I18N.mrs("dw4.ric_api_annullato")));
	}

	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}

	public List<StatoRiconciliazione> getStati() {
		return stati;
	}

	public void setStati(List<StatoRiconciliazione> stati) {
		this.stati = stati;
	}

	public String getData_da() {
		return data_da;
	}

	public void setData_da(String data_da) {
		this.data_da = data_da;
	}

	public String getData_a() {
		return data_a;
	}

	public void setData_a(String data_a) {
		this.data_a = data_a;
	}
	
}
