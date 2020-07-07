package it.tredi.dw4.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converte utilizzato per la formattazione del numero unico su tutti i procedimenti, definito nel formato seguente:
 * {{ANNO}{NUMERO_PRECEDUTO_DA_ZERO}
 * Esempio: 20200000008 
 */
public class ProcNumeroProcedimentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String result = "";
 		try {
 			Logger.debug("ProcNumeroProcedimentoConverter.getAsObject(): input value = " + value);
 			
 			if (value.contains("-")) {
				String last = value.substring(value.lastIndexOf("-")+1);
				if (last.length() > 5) {
					String anno = last.substring(0, 4);
					String num = last.substring(4);
					result = new NumConverter().getAsString(null, null, num) + "/" + anno;
				}
			}
			if (result.isEmpty())
				result = value;
 		}
 		catch (Exception e) {
 			Logger.warn("ProcNumeroProcedimentoConverter.getAsObject(): conversion failed (" + e.getMessage() + ")... use " + value);
 			result = value;
 		}
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (String) getAsObject(context, component, (String) value);
	}

}
