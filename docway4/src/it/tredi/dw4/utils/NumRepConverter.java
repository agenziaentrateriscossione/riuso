package it.tredi.dw4.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import it.tredi.dw4.i18n.I18N;
 
public class NumRepConverter implements Converter {
 
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
 		String result = "";
 		try {
 			Logger.debug("NumRepConverter.getAsObject(): input value = " + value);
 			
			if (value.contains("-")) {
				String last = value.substring(value.lastIndexOf("-")+1);
				String anno = last.substring(0, 4);
				String num = last.substring(4);
				result = new NumConverter().getAsString(null, null, num) + "/" + this._evaluateAnno(anno);
			}
			else
				result = value;
 		}
 		catch (Exception e) {
 			Logger.warn("NumRepConverter.getAsString(): conversion failed (" + e.getMessage() + ")... use " + value);
 			result = value;
 		}
		return result;
	}
 
	/**
	 * Valuta la parte 'anno' del numero di repertorio passato cercando di interpretare il tipo (gestione di repertori
	 * per legislatura)
	 * 
	 * @param anno
	 * @return
	 */
	private String _evaluateAnno(String anno) {
		if (anno != null && anno.toUpperCase().startsWith("L")) {
			// si tratta di un repertorio per legislatura
			
			String descrLegislatura = "";
			String numLegislatura = StringUtil.trimzero(anno.substring(1));
			if (!numLegislatura.isEmpty())
				descrLegislatura = RomanConversion.binaryToRoman(Integer.parseInt(numLegislatura)) + " " + I18N.mrs("dw4.legislatura");
			
			return descrLegislatura;
		}
		return anno;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
			return (String) getAsObject(context, component, (String)value);
 	}	
}