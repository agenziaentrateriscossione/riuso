package it.tredi.dw4.docway.model.procedimenti;

import java.util.ArrayList;
import java.util.List;

import it.tredi.dw4.i18n.I18N;

/**
 * Definizione dei campi di ricerca su procedimenti avviati su documenti
 */
public class ProcedimentiFilter {

	private String tipoProcedimento = "";
	
	/**
	 * Identifica se il tipo di procedimento selezionato e' un procedimento per legislatura o meno (procedimento
	 * per anno)
	 */
	private CategoriaProcedimento categoriaProc = CategoriaProcedimento.UNDEFINED;
	
	private String anno_numProc = "";
	
	private String numLegislatura = "";
	
	private String numProc_da = "";
	
	private String numProc_a = "";
	
	private String anno_numeroProcedimento = "";
	
	private String numeroProcedimento_da = "";
	
	private String numeroProcedimento_a = "";
	
	private String descrizione = "";
	
	private String dataAvvio_da = "";
	
	private String dataAvvio_a = "";
	
	private List<StatoWfProcedimento> statiWf;
	
	/**
	 * Costruttore vuoto
	 */
	public ProcedimentiFilter() {
		// Definizione degli stati di WF su procedimenti che devono essere ricercabili dalla pagina di ricerca
		this.statiWf = new ArrayList<StatoWfProcedimento>();
		this.statiWf.add(new StatoWfProcedimento("wait", I18N.mrs("dw4.wf_api_attesa_di_avvio")));
		this.statiWf.add(new StatoWfProcedimento("started", I18N.mrs("dw4.wf_api_avviato")));
		this.statiWf.add(new StatoWfProcedimento("error", I18N.mrs("dw4.wf_api_in_errore")));
		
		// Setto di default la categoria del procedimento a indefinita
		this.categoriaProc = CategoriaProcedimento.UNDEFINED;
	}
	
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	
	public String getAnno_numProc() {
		return anno_numProc;
	}

	public void setAnno_numProc(String anno_numProc) {
		this.anno_numProc = anno_numProc;
	}

	public String getAnno_numeroProcedimento() {
		return anno_numeroProcedimento;
	}

	public void setAnno_numeroProcedimento(String anno_numeroProcedimento) {
		this.anno_numeroProcedimento = anno_numeroProcedimento;
	}

	public String getNumLegislatura() {
		return numLegislatura;
	}

	public void setNumLegislatura(String numLegislatura) {
		this.numLegislatura = numLegislatura;
	}

	public String getNumProc_da() {
		return numProc_da;
	}

	public void setNumProc_da(String numProc_da) {
		this.numProc_da = numProc_da;
	}

	public String getNumProc_a() {
		return numProc_a;
	}

	public void setNumProc_a(String numProc_a) {
		this.numProc_a = numProc_a;
	}

	public String getNumeroProcedimento_da() {
		return numeroProcedimento_da;
	}

	public void setNumeroProcedimento_da(String numeroProcedimento_da) {
		this.numeroProcedimento_da = numeroProcedimento_da;
	}

	public String getNumeroProcedimento_a() {
		return numeroProcedimento_a;
	}

	public void setNumeroProcedimento_a(String numeroProcedimento_a) {
		this.numeroProcedimento_a = numeroProcedimento_a;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDataAvvio_da() {
		return dataAvvio_da;
	}

	public void setDataAvvio_da(String dataAvvio_da) {
		this.dataAvvio_da = dataAvvio_da;
	}

	public String getDataAvvio_a() {
		return dataAvvio_a;
	}

	public void setDataAvvio_a(String dataAvvio_a) {
		this.dataAvvio_a = dataAvvio_a;
	}

	public List<StatoWfProcedimento> getStatiWf() {
		return statiWf;
	}

	public void setStatiWf(List<StatoWfProcedimento> statiWf) {
		this.statiWf = statiWf;
	}

	public CategoriaProcedimento getCategoriaProc() {
		return categoriaProc;
	}

	public void setCategoriaProc(CategoriaProcedimento categoriaProc) {
		this.categoriaProc = categoriaProc;
	}
	
}
