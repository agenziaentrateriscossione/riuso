package it.tredi.dw4.acl.adapters;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import it.tredi.dw4.acl.model.ListaDistribuzione;
import it.tredi.dw4.adapters.AdaptersConfigurationLocator.AdapterConfig;
import it.tredi.dw4.adapters.DocumentFormsAdapter;
import it.tredi.dw4.adapters.FormsAdapter;
import it.tredi.dw4.utils.XMLDocumento;

public class AclDocumentFormsAdapter extends DocumentFormsAdapter {

	public AclDocumentFormsAdapter(AdapterConfig config) {
		super(config);
	}
	
	@Override
	protected void fillDefaultFormFromResponse(XMLDocumento response) throws DocumentException {
		super.fillDefaultFormFromResponse(response);
		
		Element root = response.getRootElement();
		
		//inserimento di una persona interna in una struttura interna
		defaultForm.addParam("physDoc_struint", root.attributeValue("physDoc_struint", ""));
		defaultForm.addParam("selid_struint", root.attributeValue("selid_struint", ""));
		defaultForm.addParam("pos_struint", root.attributeValue("pos_struint", ""));
		
		//per editing della gerarchia
		defaultForm.addParam("physDocOrigine", "0");
		defaultForm.addParam("relType", "");
//defaultForm.addParam("nodoCopiato", root.attributeValue("nodoCopiato", ""));

		//per cambio password
		defaultForm.addParam("pwdLogin", "");
	}
	
	// TODO Metodo presente anche in AclDocEditFormsAdapter (potrebbe essere creato un metodo di utility comune)
	public void insPersona(String cod_uff, String cod_amm, String cod_aoo, String additionalCodUff)
	{
		this.defaultForm.addParam("verbo", "docEdit");

	    // Federico 16/07/07: aggiunta trasmissione di un eventuale id addizionale per l'ufficio [RW 0045515]
		this.defaultForm.addParam("xverb", "@" + cod_uff + "|" + cod_amm + "|" + cod_aoo +
	                                        ((null!= additionalCodUff && (additionalCodUff.trim().length()>0) )
	                                            ? "|" + additionalCodUff
	                                            : ""));

		if(this.defaultForm.getParam("dbTable").indexOf("interna") != -1 )
			this.defaultForm.addParam("dbTable", "@persona_interna");
		else
			this.defaultForm.addParam("dbTable", "@persona_esterna");

		this.defaultForm.addParam("physDoc_struint", this.defaultForm.getParam("physDoc"));
		this.defaultForm.addParam("pos_struint", this.defaultForm.getParam("pos"));
		this.defaultForm.addParam("selid_struint", this.defaultForm.getParam("selid"));
		this.defaultForm.addParam("physDoc", "0");
		this.defaultForm.addParam("pos", "0");
		this.defaultForm.addParam("selid", "");
	}

	/**
	 * Creazione di una nuova casella di posta elettronica (mailbox di interoperabilita'/archiviazione gestita da mailArchiver)
	 * @param cod_amm cod_amm dell'aoo di appartenenza
	 * @param cod_aoo cod_aoo dell'aoo di appartenenza
	 */
	public void insCasellaPostaElettronica(String cod_amm, String cod_aoo) {
		this.defaultForm.addParam("verbo", "docEdit");

		this.defaultForm.addParam("xverb", "@" + cod_amm + "|" + cod_aoo);
		this.defaultForm.addParam("dbTable", "@casellaPostaElettronica");

		this.defaultForm.addParam("physDoc_struint", this.defaultForm.getParam("physDoc"));
		this.defaultForm.addParam("pos_struint", this.defaultForm.getParam("pos"));
		this.defaultForm.addParam("selid_struint", this.defaultForm.getParam("selid"));
		this.defaultForm.addParam("physDoc", "0");
		this.defaultForm.addParam("pos", "0");
		this.defaultForm.addParam("selid", "");
	}
	
	public void getFilePost(String name, String title, String db){
		defaultForm.addParam("verbo", "attach");
		defaultForm.addParam("xverb", "");
		defaultForm.addParam("target", "getFile");
		defaultForm.addParam("db", db);
		defaultForm.addParam("id", name);
		defaultForm.addParam("name", title);
	}
	
	/**
	 * Ritorna la stringa relativa all'attributo della response 'toDo'.
	 * @return Valore impostato sull'attributo toDo
	 */
	public String getToDo() {
		return defaultForm.getParam("toDo");
	}
	
	/**
	 * Ritorna la stringa relativa all'attributo della response 'db'.
	 * @return Valore impostato sull'attributo db
	 */
	public String getDb() {
		return defaultForm.getParam("db");
	}
	
	/**
	 * Ritorna la stringa relativa all'attributo della response 'aclDb'.
	 * @return Valore impostato sull'attributo aclDb
	 */
	public String getAclDb() {
		return defaultForm.getParam("aclDb");
	}
	
	/**
	 * Ritorna il parametro _cd del formsAdapter (necessario all'upload di files in presenza 
	 * di multisocieta') 
	 * @return
	 */
	public String getCustomTupleName() {
		String _cd = defaultForm.getParam("_cd");
		if (_cd == null)
			_cd = "";
		
		return _cd;
	}
	
	/**
	 * Ritorna la stringa relativa all'attributo della response 'attach'.
	 * @return Valore impostato sull'attributo attach
	 */
	public String getAttach() {
		return defaultForm.getParam("attach");
	}
	
	public void inserisciSedutaSospesa(String codOrgano, String nomeOrgano){
		defaultForm.addParam("verbo", "docEdit");
		defaultForm.addParam("xverb", "insSedSosp");
		
		defaultForm.addParam("_cd",FormsAdapter.setParameterFromCustomTupleValue("cOInsSedSosp", codOrgano, defaultForm.getParam("_cd")));
		defaultForm.addParam("_cd",FormsAdapter.setParameterFromCustomTupleValue("nOInsSedSosp", nomeOrgano, defaultForm.getParam("_cd")));
	}
	
	/**
	 * Test di connessione ad una casella di posta elettronica
	 */
	public void testConnectionMailbox(String mailboxType) {
		defaultForm.addParam("verbo", "casellapostaelettronica_response");
		defaultForm.addParam("xverb", "testConnection|" + mailboxType);
	}
	
	// ERM009243
	public void abilitaUtenti(String archive, String amministratori, String archivisti, String lettori){
		defaultForm.addParam("verbo", "abilitautenti_response");
		defaultForm.addParam("xverb", "abilitaUtenti|"+archive+"|"+amministratori+"|"+archivisti+"|"+lettori);
	}
	// Rimozione lista distribuzione
	public void rimuoviListaDistribuzione(ListaDistribuzione listaDistribuzione){
		defaultForm.addParam("verbo", "docEdit");
		defaultForm.addParam("xverb", "rimuoviListaDistribuzione");
		defaultForm.addParam("nrecord", listaDistribuzione.getNrecord());
		defaultForm.addParam("nomeLista", listaDistribuzione.getNome());
	}
}
