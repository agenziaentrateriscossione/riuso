package it.tredi.dw4.docway.beans;

import it.tredi.dw4.docway.model.Doc;
import it.tredi.dw4.docway.model.Repertorio;
import it.tredi.dw4.docway.model.XwFile;
import it.tredi.dw4.utils.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Bean di gestione della modale di inserimento di protocolli multipli
 */
public class DocWayProtocolliMultipli {

    public DocWayProtocolliMultipli(ShowdocArrivo showdocArrivo, List<XwFile> availableFiles) {
        this.showdocArrivo = showdocArrivo;
        this.originalDraft = showdocArrivo.doc;
        this.protocolliDaCreare.add(new ProtocolloArrivo());
        this.availableFiles = availableFiles;
    }

    /**
     * InnerClass di supporto per gestione delle instanze di protocollo da creare
     */
    public class ProtocolloArrivo {
        /**
         * Repertorio selezionato
         */
        private String selectedRepertorio = "";
        /**
         * File selezionati
         */
        private List<String> selectedFiles = new ArrayList<>();
        /**
         * Flag per validazione
         */
        private boolean notValid;

        public String getSelectedRepertorio() {
            return selectedRepertorio;
        }

        public void setSelectedRepertorio(String selectedRepertorio) {
            this.selectedRepertorio = selectedRepertorio;
        }

        public List<String> getSelectedFiles() {
            return selectedFiles;
        }

        public void setSelectedFiles(List<String> selectedFiles) {
            this.selectedFiles = selectedFiles;
        }

        public boolean isNotValid() {
            return notValid;
        }

        public void setNotValid(boolean notValid) {
            this.notValid = notValid;
        }
    }

    /**
     * Bean showdocArrivo che ha invocato il bean della modale di inserimento di protocolli multipli, per mantenere la
     * gestione delle richieste e della loadingbar con la page di showdocArrivo e non stare ad estendere Page in questo
     * bean
     */
    private ShowdocArrivo showdocArrivo;
    /**
     * Attiva/disattiva la visualizzazione della modale di inserimento dei protocolli multipli
     */
    private boolean active;
    /**
     * Lista di file selezionabili
     */
    private List<XwFile> availableFiles;
    /**
     * Lista di repertori arrivo selezionabili
     */
    private List<Repertorio> availableRepertori = new ArrayList<>();
    /**
     * Documento protocollo in bozza originale
     */
    private Doc originalDraft;
    /**
     * Lista dei protocollo arrivo da creare
     */
    private List<ProtocolloArrivo> protocolliDaCreare = new ArrayList<>();
    /**
     * Flag di validazione dell'intero form
     */
    private boolean notValid;
    /**
     * Mappa contenente le coppie id/valore dei file già utilizzati in precedenti tentativi di protocollazione multipla
     */
    private Map<String, String> alreadyUsedFiles = new HashMap<>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<XwFile> getAvailableFiles() {
        return availableFiles;
    }

    public void setAvailableFiles(List<XwFile> availableFiles) {
        this.availableFiles = availableFiles;
    }

    public Doc getOriginalDraft() {
        return originalDraft;
    }

    public void setOriginalDraft(Doc originalDraft) {
        this.originalDraft = originalDraft;
    }

    public List<Repertorio> getAvailableRepertori() {
        return availableRepertori;
    }

    public void setAvailableRepertori(List<Repertorio> availableRepertori) {
        this.availableRepertori = availableRepertori;
    }

    public List<ProtocolloArrivo> getProtocolliDaCreare() {
        return protocolliDaCreare;
    }

    public void setProtocolliDaCreare(List<ProtocolloArrivo> protocolliDaCreare) {
        this.protocolliDaCreare = protocolliDaCreare;
    }

    public boolean isNotValid() {
        return notValid;
    }

    public void setNotValid(boolean notValid) {
        this.notValid = notValid;
    }

    public Map<String, String> getAlreadyUsedFiles() {
        return alreadyUsedFiles;
    }

    public void setAlreadyUsedFiles(Map<String, String> alreadyUsedFiles) {
        this.alreadyUsedFiles = alreadyUsedFiles;
    }

    /**
     * Inizializzazione dei repertori arrivo selezionabili ottenuti tramite richiesta al service
     * e dei file già utilizzati in precedenti tentativi di protocollazione multipla. Quest'ultimo valore è presente
     * solo se ci sono stati errori precedenti durante il salvataggio.
     *
     * @param document Document contenente le informazioni sui repertori arrivo selezionabili e i possibili file già gestiti
     *                 correttamente in precedenti tentativi di protocollazione multipla
     */
    public void init(Document document) {
        // repertori
        this.availableRepertori = XMLUtil.parseSetOfElement(document, "response/protocolliMultipli/listof_rep/repertorio", new Repertorio());
        // gestione file già salvati
        List<?> handledFiles = document.getRootElement().selectNodes("/response/protocolliMultipli/handledFiles/handledFile");
        if (handledFiles != null && !handledFiles.isEmpty()) {
            for (Object handledFileObj : handledFiles) {
                Element handledFile = (Element) handledFileObj;
                String id, nomeFile;
                id = handledFile.attributeValue("id");
                nomeFile = handledFile.attributeValue("nomeFile");
                if (id != null && !id.isEmpty())
                    this.alreadyUsedFiles.put(id, nomeFile);
            }
        }

        // ulteriore filtro sui file selezionabili
        this.availableFiles = this.availableFiles
                .stream()
                .filter(file -> this.alreadyUsedFiles.get(file.getName()) == null)
                .collect(Collectors.toList());
    }

    /**
     * Cancella l'operazione di creazione di protocolli multipli
     */
    public void annulla() {
        this.active = false;
    }

    /**
     * Controlla se almeno un file è selezionabile
     *
     * @return TRUE se almento un file è selezionabile, FALSE altrimenti
     */
    public boolean isAnyFileSelectable() {
        return this.availableFiles != null && !this.availableFiles.isEmpty();
    }

    /**
     * Controlla se sono stati utilizzati tutti gli allegati, in caso affermativo, avvia la loadingbar di salvataggio
     * di tutti i protocolli richiesti, altrimenti controlla se è necessario mostrare un messagio di errore indicante
     * la necessità di utilizzare tutti gli allegati
     */
    public void conferma() throws Exception {
        if (!this.validate()) {
            return;
        }
        this.showdocArrivo.handleRichiestaProtocolliMultipli(this.protocolliDaCreare, this.originalDraft);
        this.active = false;
    }

    /**
     * Validazione, controlla che tutti i file sono stati utilizzati e che ogni protocollo abbia almeno un allegato
     *
     * @return TRUE se il form di inserimento dei protocolli è valido, FALSE altrimenti
     */
    private boolean validate() {
        boolean valid = this.countProtocolliDaCreareSenzaFileSelezionati() == 0;
        this.notValid = this.stillFilesToUse();
        return valid && !this.stillFilesToUse();
    }

    /**
     * Controlla se tutti gli allegati sono stati utilizzati
     *
     * @return TRUE se tutti i file sono stati utilizzati, FALSE altrimenti
     */
    public boolean stillFilesToUse() {
        return this.filesStillToUseNumber() > 0;
    }

    /**
     * Calcola il numero di file rimasti da utilizzare
     *
     * @return numero dei file rimasti da utilizzare
     */
    public int filesStillToUseNumber() {
        return this.availableFiles.size() - this.getAlreadyPickedFilesCods().size();
    }

    /**
     * Controlla se è ancora possibile aggiungere protocolli, ovvero se il numero dei file non ancora uilizzati
     * è superiore ai protocolli inseriti
     *
     * @return TRUE se il numero dei file non ancora utilizzati è superiore al numero dei protocolli
     */
    public boolean canAddProtocollo() {
        return this.protocolliDaCreare.size() < this.availableFiles.size() &&
                (this.filesStillToUseNumber() - this.countProtocolliDaCreareSenzaFileSelezionati()) > 0;
    }

    /**
     * Conta il numero di protocolli con nessun file selezionato
     *
     * @return il numero di protocolli senza nessun file selezionato
     */
    private int countProtocolliDaCreareSenzaFileSelezionati() {
        return (int) this.protocolliDaCreare
                .stream()
                .filter(protocolloArrivo -> protocolloArrivo.getSelectedFiles().isEmpty())
                .count();
    }

    /**
     * Rimozione di un protocollo
     *
     * @param index indice del protocollo da rimuovere
     */
    public void removeProtocollo(int index) {
        this.protocolliDaCreare.remove(index);
        if (this.protocolliDaCreare.isEmpty())
            this.protocolliDaCreare.add(new ProtocolloArrivo());
    }

    /**
     * Aggiunta di un protocollo successivo ad una determinata posizione
     *
     * @param index indice del protocollo da aggiungere
     */
    public void addProtocollo(int index) {
        if (this.canAddProtocollo()) {
            this.protocolliDaCreare.add(++index, new ProtocolloArrivo());
        }
    }

    /**
     * Ritorna la lista di tutti i file già utilizzati
     *
     * @return Lista di tutti i file utilizzati
     */
    public List<String> getAlreadyPickedFilesCods() {
        List<String> result = new ArrayList<>();
        this.protocolliDaCreare.forEach(protocolloArrivo -> result.addAll(protocolloArrivo.selectedFiles));
        return result;
    }

}
