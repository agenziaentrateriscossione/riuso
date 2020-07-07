package it.tredi.dw4.docway.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import it.tredi.dw4.acl.beans.UserBean;
import it.tredi.dw4.model.Titolo;
import it.tredi.dw4.utils.XMLDocumento;
import it.tredi.dw4.utils.XMLUtil;

public class DocWayLookupListaDist extends DocWayLookup {

	private List<Titolo> allTitles = new ArrayList<>();
	
	public List<Titolo> getAllTitles() {
		return allTitles;
	}
	public void setAllTitles(List<Titolo> allTitles) {
		this.allTitles = allTitles;
	}
	public DocWayLookupListaDist() throws Exception {
		super();
	}
	/**
	 * Aggiunta di tutti i titoli di tutte le pagine a collection allTitles
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void confermaTutti() throws Exception {
		allTitles.clear();
		List<Titolo> tempTitoli = new ArrayList<>();
		int count = getFormsAdapter().getDefaultForm().getParamAsInt("count");
		int pageCount = getFormsAdapter().getDefaultForm().getParamAsInt("pageCount");
		int num=0;
		while(num<count) {
			getFormsAdapter().getDefaultForm().addParam("verbo", "lookup_response");
			getFormsAdapter().getDefaultForm().addParam("pos", num);
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			UserBean userBean = (UserBean) session.getAttribute("userBean");
			
			XMLDocumento response = getFormsAdapter().getDefaultForm().executePOST(userBean);
			tempTitoli = (List<Titolo>) XMLUtil.parseSetOfElement(response.getDocument(), "//titolo", new Titolo());
			if(tempTitoli!=null && !tempTitoli.isEmpty()) {
				tempTitoli.forEach( titolo -> {
					if(allTitles.stream().noneMatch( t -> t.getIndice().equals(titolo.getIndice()))) {
						allTitles.add(titolo);
					}
				});
			}
			num+=pageCount;
		}
	}
	@Override
	public String confirm(Titolo titolo) throws Exception {
		String confirmValue = super.confirm(titolo);
		return confirmValue;
	}
	/**
	 * Override del cambio pagina per mantenere lookupType
	 */
	@Override
	public String paginaSuccessiva() throws Exception {
		String lookupType = getLookupType();
		String paginaSuccessiva = super.paginaSuccessiva();
		setLookupType(lookupType);
		return paginaSuccessiva;
	}
	@Override
	public String paginaPrecedente() throws Exception {
		String lookupType = getLookupType();
		String paginaPrecedente = super.paginaPrecedente();
		setLookupType(lookupType);
		return paginaPrecedente;
	}
	@Override
	public String ultimaPagina() throws Exception {
		String lookupType = getLookupType();
		String ultimaPagina = super.ultimaPagina();
		setLookupType(lookupType);
		return ultimaPagina;
	}
	@Override
	public String primaPagina() throws Exception {
		String lookupType = getLookupType();
		String primaPagina = super.primaPagina();
		setLookupType(lookupType);
		return primaPagina;
	}
}
