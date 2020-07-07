package it.tredi.dw4.docway.model;

import it.tredi.dw4.model.XmlEntity;
import it.tredi.dw4.utils.XMLUtil;
import org.dom4j.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdditionalHistoryInfos extends XmlEntity {

    /**
     * Lista delle informazioni
     */
    private List<CustomHistoryInfo> infos = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public XmlEntity init(Document dom) {
        infos = XMLUtil.parseSetOfElement(dom, "/additional_infos/node()", new CustomHistoryInfo());
        // ordinamento per posizione garantito
        // infos = infos.stream().sorted(Comparator.comparing(CustomHistoryInfo::getPosition)).collect(Collectors.toList());
        return this;
    }

    @Override
    public Map<String, String> asFormAdapterParams(String prefix) {
        return null;
    }

    /**
     * Controlla se ci sono valori
     *
     * @return {@code true} se ci sono valori, {@code false} altrimenti
     */
    public boolean hasValues() {
        return infos != null && !infos.isEmpty();
    }

    /**
     * Ottiene la lista di infos
     *
     * @return La lista di infos
     */
    public List<CustomHistoryInfo> getValues() {
        return infos;
    }
}
