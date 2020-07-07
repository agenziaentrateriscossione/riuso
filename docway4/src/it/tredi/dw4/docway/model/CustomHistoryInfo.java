package it.tredi.dw4.docway.model;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;
import org.dom4j.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomHistoryInfo extends XmlEntity {

    /** Controlla se deve gestire una lista di valori */
    private boolean list = false;

    /** Separatore nel caso di valore lista */
    private String divider;

    /** Header dell'informazione */
    private String header;

    /** Valore serializzato proveniente dal service */
    private String originalSerializedValue;

    /** Posizione */
    private Integer position;

    @Override
    public XmlEntity init(Document dom) {
        header = dom.getRootElement().getName();
        originalSerializedValue = XMLUtil.parseStrictAttribute(dom, header + "/@value");
        divider = XMLUtil.parseStrictAttribute(dom, header + "/@divider");
        if (divider != null && !divider.isEmpty())
            list = true;
        position = Integer.parseInt(XMLUtil.parseStrictAttribute(dom, header + "/@position", "0"));
        return this;
    }

    @Override
    public Map<String, String> asFormAdapterParams(String prefix) {
        return null;
    }

    public String getHeader() {
        return header;
    }

    public boolean isList() {
        return list;
    }

    public String getDivider() {
        return divider;
    }

    public String getOriginalSerializedValue() {
        return originalSerializedValue;
    }

    public Integer getPosition() {
        return position;
    }

    /** Ritorna il valore deserializzato */
    public List<String> getValue() {
        if (list) {
            return Arrays.asList(originalSerializedValue.split("\\Q" + divider + "\\E"));
        } else {
            List<String> result = new ArrayList<>();
            result.add(originalSerializedValue);
            return result;
        }
    }
}
