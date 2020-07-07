package it.tredi.dw4.docway.model.procedimenti;

public class StatoRiconciliazione {

	/**
	 * Codice associato allo stato
	 */
	private String cod = "";
	
	/**
	 * Etichetta da mostrare associata allo stato
	 */
	private String text = "";
	
	/**
	 * Indica se lo stato risulta selezionato dall'utente
	 */
	private boolean selected = false;
	
	/**
	 * Costruttore vuoto
	 */
	public StatoRiconciliazione() {
	}
	
	/**
	 * Costruttore
	 * 
	 * @param cod Codice associato allo stato
	 * @param text Etichetta da mostrare associata allo stato
	 */
	public StatoRiconciliazione(String cod, String text) {
		this.cod = cod;
		this.text = text;
	}
	
	public String getCod() {
		return cod;
	}
	
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
