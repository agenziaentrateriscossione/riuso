Change Log - DocWay4
====================

[4.16.2] - SNAPSHOT
-------------------

### Changed
- Controllo su tipologia obbligatoria (in caso di attivazione della gestione procedimenti) solo se non si sta creando una fatturaPA attiva (campo tipologia non presente)
- Modificata la label P.IVA in caso di C.F. mancante (C.F./P.IVA) su preview PDF di una fatturaPA

### Fixed
- Corretta la formattazione di indirizzi di mittente e destinatario di una fatturaPA in preview PDF della fatturaPA



[4.16.1] - 2020-06-09
---------------------

### Added
- Preview tramite XSLT dei file XML relativi a NSO (Nodo di smistamento degli ordini di acquisto delle amministrazioni pubbliche) (Task #20883)


[4.16.0] - 2020-05-29
---------------------

### Added
- Integrazione con riconciliazione API (CRV) (Task #19486)
- Aggiunta property atta a disabilitare l’identità di classificazione fra fascicolo e documento e quindi consentire 
  la fascicolazione di un documento in ogni fascicolo (Task #20642)
- Aggiunto job per la gestione automatizzata delle notifiche differite (Task #20641)
- Aggiunto diritto per consentire e storicizzare le modifiche ai rif_esterni di un documento protocollato (Task #20449)
- Introdotta la possibilità di aggiungere i destinatari nei protocolli in partenza attraverso una nuova entity lista di distribuzione (Task #19969)
- Aggiunti fogli di stile XSLT per gestione FatturePA ver. 1.2.1 (Task #20584)
- Possibilità di recuperare gli assegnatari CC di un documento dagli indirizzi email del mittente e/o destinatario di un messaggio di posta (configurazione su ACL) (Task #20668)

### Fixed
- Corretti bug in costruzione del filtro di ricerca in "Ricezione Posta" di DocWay (Task #20757)
- Rimosso il filtro di ricerca su range di nrecord (con un elevato numero di documenti rischia di compromettere l'archivio eXtraWay) da pagina di ricerca documenti (Task #20849)


[4.15.0] - 2020-04-03
---------------------

### Added
- Gestione della marcatura temporale per la firma digitale OTP Uniserv - Unimatica
- Gestione della creazione di protocolli multipli a partire da una bozza in arrivo (CRV Task #19606)
- Avvio Procedimenti su documenti di DocWay con integrazione ad API (CRV) (Task #19484)

### Fixed
- Aggiunto parametro physDoc alle richieste js di iwx per corretta visualizzazione degli audit di DOWNLOAD_FILE
- In caso di trasformazione di un doc non protocollato in protocollo in arrivo non venivano correttamente compilate le opzioni relative a mezzo e tipologia del doc in arrivo


[4.14.4] - 2020-01-31
---------------------

### Fixed
- Cambiata gestione attivazione tasto salva (Task #19979)
- Uniformato comportamento valorizzazione checkbox per lookup e click su lente(Task #20065)
- Risoluzione problema flag ruoli per notifica capillare (Task #20008)
- Possibilita' di definire lunghezze custom per il seriale del numero di fascicolo. Gestiti errori in navigazione fascicoli in caso di cambio della lunghezza del seriale num_fascicolo in ambiente di produzione (Task #19948)

[4.14.3] - 2020-01-08
---------------------

### Added
- Gerstione flusso PEC MANAGER (Task #19804).
- Aggiunto il filtro su ID (nrecord) su tutte le pagine di ricerca di record di ACL (persone interne, strutture interne, ruoli, etc.) (Task #20130). 

### Fixed
- Corretti bug in encoding su preview delle fatture su server Windows (Task #19941).


[4.14.2] - 2019-11-08
---------------------

### Added

- Aggiunto controllo scadenza password DW3 e ricreata funzionalità durante la login di DW4
- Integrazione Firma Remota Aruba (OTP) (Task #19476)
- Aggiunta funzionalità per abilitare la sezione Stampe solo ad utenti aventi diritto (DW-DOC-STMP) abilitato in ACL.
- Aggiunta funzionalità per permettere la modifica dell'oggetto di un documento protocollato, attraverso specifico diritto
  e inserimento di motivazione (post-it e storia documento)

### Fixed

- Possibilità di configurare i repertori in partenza che non hanno destinatari (patch 4.14.1b)
- Correzione nullPointerException template showthesNodoGerarchia (docwayproc - patch 4.14.0b / 4.14.1a)
- Corretto bug in valutazione delle regole di relazione fra campi custom
    * L'azzeramento del valore dei campi custom non visualizzati deve essere fatta al termine della fase di valutazione delle regole derivanti da relazioni fra campi
- Checkbox della selezione delle notifiche capillari settato default a true (patch 4.11.1c)
- Allegati pdf dei postit senza segnatura (patch 4.11.1c)


[4.14.1] - 2019-10-03
---------------------

### Added

- Integrazione Firma Remota OTP Uniserv (UNIMATICA)  (Task #18017).
- Smistamento documenti da MSA in base all'indirizzo email del mittente (Task #19514).
- Reso capillare l'invio di notifiche ai riferimenti interni, introducendo una nuova voce di profilo nel file it.highwaytech.apps.xdocway.properties, "CheckboxEmailNotificaCapillare".
  Il valore 'si' attiva la modalità capillare che inibisce il checkbox "Invio Mail di Notifica" ed accende un checkbox alla sinistra di ogni riferimento interno.
  L'invio di mail è condizionato comunque dagli altri fattori di filtro (non si manda la mail a chi edita il record, non si manda la mail a chi ha notifica differita) (Task #12596).
- Aggiunta la possibilità di inserire allegati nelle annotazioni / postit (Task #11687)
  N.B. richiede aggiornamento dei conf di archivio.
- Ricerca e visualizzazione di documenti in attesa di rifiuto a causa di allegati non supportati (documenti registrati in 
  bozza tramite MSA) (Task #18041).

### Fixed

- Reso visibile il menù delle vaschette anche per le risoluzioni inferiori ai 988px (bootstrap xs)

[4.14.0] - 2019-09-23
---------------------

### Added

- Nuove implementazioni su campi custom (REALIZZATO PARZIALMENTE SULLA VERSIONE 
  PRECEDENTE, MA A LIVELLO UFFICIALE DEVE ESSERE RILASCIATO CON QUESTA RELEASE) (Task #18067): 
  * Validazione di campi numerici e di tipo data tramite controllo sul valore minimo e massimo previsti;
  * Possibilità di abilitare il prepend di un carattere su un campo testo fino al raggiungimento di una lunghezza massima;
  * Possibilità di rendere obbligatori o visibili alcuni campi in base alla selezione di specifici valori da una tendina custom (relazione fra campi custom);
  * Possibilità di cambiare il valore della classificazione in base alla selezione di determinati valori da una tendina su campi custom;
  * Possibilità di definire lookup misti su campi custom (lookup che permettono la valorizzazione di campi custom e non, es. RPA del documento);

### Fixed

- Apertura del dialog di richiesta di scansione di ulteriori documenti su IWX sempre in primo piano (Task #19464).
- Eliminati il pulsante di notifica esito della fatturaPA in caso di destinatario privato (Task #19191).
- Rimosso escape degli URL di caricamento dei manuali online di DocWay e ACL.

### Changed

- Esecuzione automatica dell'applet di firma digitale mediante chiamata con protocollo jnlp. (Task #19479)
- Supporto ad Apache Tomcat ver. 8.5 (Task #19294).
- Eliminati gli stati del documento relative a fatturePA in caso di destinatario privato (le fatture vanno considerate implicitamente 
  accettate) (Task #19191).

[4.13.2] - 2019-07-22
---------------------

### Added

- Aggiunto campo custom di tipologia numero-anno
- Integrata la liberia DSS per l'estrazione dei dati e la validazione delle firme digitali
- Gestione TAGS di archiviazione: Archiviazione documenti tramite TAGS presenti sull'oggetto del messaggio 
  elaborato da MSA (Task #18039).

### Fixed

- Corretti i controlli di obbligatorietà su destinatari di documenti e repertori in partenza (Task #18090).
- Abilitato il pulsante per la stampa senza segnatura anche per i repertori di tipo varie se la property abilitaSegnaturaRepertori=si

[4.13.1] - 2019-06-07
---------------------

### Added

- Implementazione delle caselle di import: Caselle per le quali i messaggi da elaborare non corrispondono ai messaggi letti 
  dalla casella ma risultato allegati ad essi - messaggi con allegato un altro eml. (Task #17664).

### Fixed

- Ripristino del flag "Doc non fascicolati" in caso di reset del form di ricerca di documenti (Task #17962).
- Corretta etichetta property associata alla funzione di ricerca documenti non fascicolati (Task #18030).

[4.13.0] - 2019-05-27
---------------------

### Added

- Aggiunto tema grafico per "Fondazione Scuola Beni Attività Culturali"
- Aggiunta la ricerca di documenti non fascicolati su pagina di ricerca documenti (Task #17658).

### Changed

- Modificato il titolo nella lista alegati del fascicilo del personale
- Rispondi/Risposta abilitato anche per i repertori
- Possibilità di disabilitare tutte le stampe o esportazioni (funzioni di lunga durata con loadingbar) nella fascia oraria di sovraccarico del
  sistema (Task #17652).

[4.12.5] - 2019-04-04
---------------------

### Added

- Ricerca di documenti attraverso i dati del registro di emergenza (Task #17333).
- Smistamento fatturaPA agli assegnatari in base all'indirizzo email presente sulla fattura e non alla mailbox alla 
  quale è stata spedita la fattura da parte dello SdI (Task #17297)

### Fixed

- Fix dei css in Genera Anteprima di Ricerca per IE11 per CorteCostituzionale
- Aggiunti css personalizzati Corte Costituzionale
- Corretta disposizione pulsante elimina tutti i selezionati nello showdoc@raccoglitore@INDICE
- ERM017280 : - iwx.runtime.js agiustato scan multiplo con QRCode

[4.12.4] - 2019-02-25
---------------------

### Added

- Alcuni sviluppi per Corte Costituzionale:
  * Aggiunta logica e properties specifiche per l'idividuazione e per la stampa dell'indice del raccoglitore Ufficio Ruolo
  * Cambiata la logica con la quale viene mostrato l'oggetto per la stampa e corretto il fatto che non si vedesse in showdoc-preview
  * Implementata l'eliminazione massiva di documenti del raccoglitore indice.
- Aggiunti i pulsanti di inserimento sottofascicolo/inserto/annesso sul popup di fascicolazione di un documento (Task #17163).
- Merge con il branch di sviluppo di Corte Costituzionale:
  * Puntatore modifica rapida alla voce d'indice in lavorazione
  * Evidenziazione della voce d'indice in lavorazione
  * Pulsantiera replicata in alto nella modifica rapida
  * Glyphicon modificate per apertura/chiusura metadati
  * Aggiunta opzione colore nella strumentazione dell'editor web
  * Riduzione ampiezza menù contestuale raccoglitore (portare su 2 righe invece che 3)
  * Eliminato pulsante link per i raccoglitore di tipo indice.
  * Riduzione altezza header (tema specifico custom per corte)
  * Selezione multipla su voci d'indice per operazione massiva di setting dello stato del documento
  * Aggiunta sottovoci in numero definito dall'utente
  * Correzione label 'Visualiza'
  * Preview documento automatica dopo inserimento di documenti nel raccoglitore Indice
  * Correzione dei refresh AJAX
  * Funzionalità di duplicazione di un raccoglitore di tipo Indice
  * Pulsante per lo scrolling al documento correntemente in preview
  * Funzionalità di rimozione del raccoglitore Indice e di tutti i suoi docs
  * Funzionalità di riapertura di una ricerca chiusa a seguito della generazione della ricerca
  * Info per inserimento massivo di docs
  * Adattamento apertura menù contestuale per contenere più voci
  * Pulsanti per la cancellazione della formattazione di TinyMCE
  * Correzione bug TinyMCE (mancanza di una icona per la funzione "seleziona tutto")

### Fixed

- Rimosso pulsante di cambio di RPA da azioni rapide su un documento in caso di documento fascicolato (Task #17233).
- Sistemata visualizzazione delle voci d'Indice durante la selezione per la stampa
- Mantenimento dello stato 'popup' in caso di navigazione gerarchia fascicolo in popup di fascicolazione di un documento (Task #17163).
- Corretto il formato dei titoli di tutti i lookup verso ACL (Task #17081).

### Changed

- Inibita la visualizzazione di file allegati ad un documento per il quale è in corso la verifica di virus per ogni utente che non risulta
  essere l'incaricato dell'analisi di virus (Task #17267).
- Assegnazione del flusso di verifica virus ad un ufficio (UOR) anziché ad una persona (RPA).

[4.12.3] - 2019-01-28
---------------------

### Added

- ERM016747 : 
  - modifica principalmente iwx.runtime.js per gestire le stampe in bulk con aggiunta dei file e download dei file via IWX
- Flusso operativo di verifica di virus su file allegati ad un documento (Task #16758).
- Aggiunta della colonna Società in caso di ricerca su archivi multisocietà (Task #16744).

### Fixed

- ERM016747 : 
  - setup_iwx.msi added link to auto-startup folder
- ERM016747 : 
  - disabilita sstunel
  - diminuisce numero di eventi del progress, e cambia modalita di gestione della coda dei eventi
  - polyfill per findIndex in docway.js
- ERM016747 : 
  - bug del IWX per il download massivo, aggiunto sync su file del job, e limitazione a max 10 file in stesso momento.
- Controllo validità della voce di indice in 'Ripeti Nuovo' di un documento. Eventuale warning in caso di voce indice non presente (Task #16837).

[4.12.2] - 2018-11-28
---------------------

### Added

- ERM016618 : - midificate le i18n
  - aginuto pulsate smartActionsFasc.xhtml
  - modificati e tooltip in smartActionsFascTree.xhtml
- Aggiunto un alert di conferma sull'operazione di trasformazione di una bozza in documento non protocollato (Task #16376).
- Aggiunta di un link prettyFaces su ACL per il caricamento di una generica risorsa (struttura interna/esterna, persona interna/esterna).
- ERM015806 : visibilità del  pulsante 'albero fascicolo' e legata alla property 'pfolders.active'
- ERM015806 : - Aggiunto un pulsante visibile su Fascicolo del personale principale 
  che consenta di accedere a una maschera di visualizzazione della struttura completa del fascicolo (e quindi dei sottofascicoli e degli inserti) 
  in cui sono evidenziati gli inserti che contengono almeno un documento; 
  inoltre è presente l’indicazione del numero dei documenti contenuti per ogni livello (fascicolo, sottofascicolo e inserto). 
  A livello del Fascicolo principale, è conteggiato il numero totale dei documenti contenuti nel fascicolo, sottofascicoli/inserti inclusi. 
  - Aggiunto un pulsante, visibile sulla maschera di visualizzazione dell’albero del fascicolo del personale, 
  che consenta di avviare la procedura di esportazione dei file allegati ai documenti contenuti nel fascicolo e nei relativi sottofascicoli e inserti.

### Fixed

- ERM016618 : bug del messagio del loading bar
- Ripristinato il funzionamento della procedura di rigenerazione della gerarchia fascicoli da strumenti di amministrazione
  di DocWay (Task #16428).
- Ripristinato il funzionamento del check bozza di default su repertori in partenza (Task #16395).

[4.12.1] - 2018-09-25
---------------------

### Added

- Piccoli sviluppi corte costituzionale
  * Aggiunta label esplicativa su flag "pubblico" nei raccoglitori di tipo indice
  * Aggiunta label personalizzata su nuovo CC
  * Aggiunto pulsante per salvare direttamente come completato per la stampa

### Fixed

- Nascosti i check di filtro su documenti 'annullati' o 'non annullati' in caso di stampa di una selezione di documenti. I filtri sull'annullamento
  vengono applicati solo nel caso in cui la stampa non derivi da una precedente selezione (costruzione della query in base ai filtri impostati
  sul form di stampa) (Task #16250).
- Migliorato il livello di errore (non FATALE) in caso di eccezione in fase di assegnazione di un documento (Task #16227).

### Changed

- (*) Disabilitato il forceParse del widget js DatePicker.
  * Aggiunto un js per la gestione della chiusura degli showdoc-section in indici.

[4.12.0] - 2018-09-03
---------------------

### Added

- Possibilità di configurare tramite property l'inibizione della protocollazione di una bozza che non abbia specificato
  il secondo livello di Classificazione.
- Aggiunto il campo Partita IVA sulle persone esterne in ACL per la gestione di ditte individuali (Task #15912).
- Aggiunta possibilità di scaricare watermark di copia conforme anche per le immagini convertite con FCA
- Aggiunto modulo di audit delle azioni svolte dagli operatori (richiede configurazione sul broker e aggancio ad un database MongoDB).
- Aggiunte a formaAdapter la login e la matricola dell'utente reale (azione svolta in delega) per registrazione dell'audit applicativo.

### Fixed

- Corretto bug nella procedura di annullamento di una fascicolazione massiva
- Corretto bug accesso in  delega in audit e bug per il quale i dati di delega rimanevano anche una volta rilasciati
- Corretto bug in applicazione del foglio di stile XSLT in preview di fatturePA in formato PDF su ambiente Windows (Task #15814).
- Corretto bug in visualizzazione del pulsante di assegnazione del numero di protocollo: Numero di protocollo assegnabile sui documenti
  in bozza e sui documenti non protocollati.

### Changed

- Definizione delle tipologie di font e relative dimensioni su editor TinyMCE.
- Aggiunti i title alle etichette relative ai campi custom in showdoc.

[4.11.4] - 2018-06-01
---------------------

### Added

- Aggiunti nuovi flag per la ricerca dei documenti in partenza in base ai file di notifica PEC
- Aggiunta possibilità di ricerca sui file di notifica PEC per i doc. in partenza in query globale: 
  * Accettazione
  * Consegna
  * Avviso di non accettazione
  *	Avviso di non accettazione per virus
  *	Avviso di mancata consegna
  *	Avviso di mancata consegna per sup. tempo massimo
  *	Problema di sicurezza
  *	Avviso di mancata consegna per virus
  *	Errore
- Personalizzazione dei fogli di stile XSLT per le fatturePA:
  * Personalizzazione dei fogli di stile XSLT per ogni tipologia
  * Generazione di un PDF con Apache FOP invece che una pagina HTML
  * Aggiunta al PDF dei dati di segnatura
- Introdotto nuovo pannello di controllo per la gestione delle deleghe in ACL

### Fixed

- Abilitata la protocollazione di bozze prive di classificazione da pagina di modifica nel caso in cui è attiva la 
  property 'docClassifEreditabile' (Task #15585).
- Richiamato il refresh del raccoglitore di tipo indice dopo il salvataggio, per mostrare il corretto stato dei documenti
- Corretto il template di modifica (e relativo salvataggio) di sottofascicoli custom (personale o studente) (Task #15226).
- Corretto l'import della modale per il rifiuto delle bozze in arrivo da PEC sui vari repertori
- Inserita property per inserire gli assegnatari CC direttamente con diritto di intervento
- Funzione che setta tutti completati per la stampa negli indici di ricerca (Corte)

[4.11.3] - 2018-04-18
---------------------

### Added

- Aggiunto nella storia del documento anche l'operazione di transformazione della bozza in non protocollato. (Task #1309)
- Abilitazione diritto in ACL per rimuovere una bozza in arrivo da PEC
- Miglioramenti gestione PEC archiviate automaticamente:
  * Implementazione nuova funzionalità rigetta bozza PEC abilitata da property
  * Alla richiesta di rigetto della bozza si apre un popup in cui vengono richieste le motivazioni del rifiuto
  * Marca la bozza in arrivo come rigettata nello stato del documento
  * Prepara un doc. in partenza e gestisce l'invio della notifica di rifiuto via PEC
  * Gestita la ricerca (query globale) di documenti in partenza generati dalla richiesta di rifiuto di una bozza in arrivo da PEC
- Aggiunto il pulsante per formattazione underline nella toolbar di TinyMCE
- Inseriti due nuovi attributi per i repertori.xml per la non fascicolazione e la non cancellazione (Task #13640).
- Aggiunta modalità di presa in carico di tipo "gruppo", basta un utente qualsiasi ad effettuare la presa in carico per tutto l'ufficio/ruolo

### Fixed

- Corretta la costruzione della query di ricerca per SoginSAP (Task #14698).
- Controllo su property 'repertorioInBozzaByDefault' fatto solo in fase di inserimento di documenti (Task #13987).
- Aggiustata la gestione della property disabilitaModificaInBozza:
  * Creata la funzionalità 'protocollaRight' che individua solamente se l'utente ha diritto di protocollazione sul documento.
  * Nello showDoc viene controllato se l'utente ha i diritti di protocollazione, ma non che la classificazione sia stata inserita.
  * Nel docEdit viene inserito il pulsante di protocollazione in ogni caso, ma è disabilitato se la classificazione non è stata ancora inserita.
- Rimossa data di default dal protocollo invio differito
  Aggiornata funzionalità copia nella clipboard del link del documento anche al fascicolo e al raccoglitore, più piccole implementazioni correttive

[4.11.2] - 2018-02-23
---------------------

### Added

- Possibilità di attivare la trasformazione doc. in Repertorio tramite DocEdit (Task #13450).
- Aggiunta possibilità di fare l'upload di archivi .zip. I file vengono decompressi e inseriti nel form di modifica come fosse stato fatto un upload multiplo
- Aggiunta possibilità di inserire un watermark di copia conforme per i documenti PDF o convertiti PDF.
- Aggiunta funzionalita per forzare la visibilita del documento in base all'uffico dell'RPA, configurabile da properties

### Fixed

- Aggiornamento della query per doc in partenza di interoperabilità effettuati con invio multiplo
- Ricarica documento dopo visualizzazione warning per destinatario assente/malformato (Task #13849)
  L'aggiornamento del documento dopo la chiusura del pop-up warning fixed.
- Aggiunto controllo per evitare un'eccezione se la visibilità da forzare con la property forzaVisibilitaUfficio=si non corrisponde ad un codice visibilità sensato.
- Corretti bug su visibilità forzata in caso di cambio RPA da pagina di showDoc e visualizzazione info.
- Corretti bug in caricamento delegante con utenti aventi la stessa login impostata come principale (prima login della lista).
- L'inibizione del pulsante di Modifica nel menu di Azioni sul doc. per i casi quando l'unico pulsante disponibile quando si va in Modifica sia il pulsante "Abbandona".
- Sostituito indexOf con lastIndexOf per problemi di caricamento di personalView interne alla directory 'rep_standard'.
- Aggiunto il messaggio di conferma al termine dell'attività di upload del file repertori.xml e dei file di segnatura.xml dalla pagina di Amministrazione di Docway.(Task #4262)
- Corretto il controllo di attivazione del componente DragAndDrop su immagini nelle maschere di docEdit in caso di IWX disabilitato
- Forzato il caricamento del componente DragAndDrop su checkin di files in caso di IWX disabilitato per l'utente.
- Controllo che sia almeno un destinatario diretto (in To) prima della registrazione del documento in partenza (Task #13369)
  Validazione sul flag CC (Copia_conoscenza), non si puo salvare il documento nel caso quando tutti i destinatari sono flaggati in CC.
- Disabilitazione di vecchi tab del browser aperti sull'applicazione (solo l'ultimo aperto risulta attivo). Si evitano problemi derivanti da un 
  utilizzo in multischeda dell'applicazione. Può essere disabilitato tramite la property 'abilitaCheckClientWindowId' del file docway.properties. (Task #13376)
- Limitata la possibilità di lavorare in multischeda su singolo browser con DocWay. Disabilitati tutti i menù contestuali di apertura
  link in nuova scheda o finestra tramite Override del renderer su CommandLink (Task #13376).
- Validazione su i campi D.Model e DB per inserimento e modifica di caselle di posta di interoperabilita (Task #13325)
  Nel caso di posta di interoperabilita (quando Interoperabilita=Si) questi due campi sono obligatori e devono essere compilati

[4.11.1] - 2018-01-11
---------------------

### Added

- Aggiunta funzionalità per contrassegnare come letto una selezione di documenti dalla lista titoli, utilizzando la loadingbar. Attivabile da property.
- Aggiunta property e implementato sistema per inibire la protocollazione di bozze di documenti in partenza se il documento contiene file in libro firma
- Aggiunte nuove modalità di validazione su campi custom (Task #13224):
  * Controllo formale del valore inserito tramite regex;
  * Controllo sulla lunghezza del valore inserito (num. min e max di caratteri richiesti);
- Sistemati diritti per effettuare Rigetta.
  Aggiunta nuova funzionalità Restituisci di tipo Reso e diversificata la storia dal Rigetta.
  Ripristinata possibilità di aggiungere PostIt in operazioni di tipo Reso.
- Aggiunta una property per la gestione di tutti i fascicoli speciali anche in fascicolazione massiva e in ricerca fascicoli
- Aggiunta property in xdocway per avere i repertori checkati in bozza di default

### Fixed

- Trim di tutti i codici su formAdapters in ACL (Task #13342).
- Corretto bug per inserimento repertori in bozza by default con property repertoriInBozzaByDefault=si
- Corretto bug che permetteva a chi non ha diritto di inserimento di documenti protocollabili di protocollare le bozze in caso di assegnazione.
- Problema su selezione multipla di documenti (Task #13253): 
  * Impossibilità di deselezionare alcuni documenti una volta fatto il check su "Seleziona tutti"; 
  * Il comportamento indicato è corretto in caso di integrazione con Elasticsearch.
- Corretto bug in upload immagini relativi a workflow Bonita ver. 5 su archivio procedimenti.
- Modificata integrazione firma digitale tramite applet jnlp (problema troncamento url GET Internet Explorer)
- Sistemati i template per firma digitale tramite applet jnlp (problema doppia inclusione elemento jsf)

### Changed

- Aumentato il log di eventuali errori sulla lettura di risorse da parte del configuratore.

[4.11.0] - 2017-11-09
---------------------

### Added

- Implementazione di azioni massive tramite stored procedure LUA (Task #8783).
- Implementata la gestione di accesso delega (Task #11306). Prevede:
  PARTE PERSONALE (DOCWAY)
  * Menù di gestione deleghe (inserimento, modifica, eliminazione)
  * Interfaccia (css e labels) per distinzione login normale da delega
  * Menù di selezione deleghe attive e valide (query su persona_interna)
  * Swap degli utenti in sessione
  * Menù di rilascio delega
  PARTE ADMIN (ACL)
  * Nel form di modifica persona_interna parte di gestione delle deleghe
  * Visualizzazione delle deleghe in show di una persona_interna
  * Gestione dei diritti di delega sia personali che admin
  * Query di ricerca su campo "deleganti" delle persone interne

### Fixed

- Risolto problema all'invio telematico a più destinatari con property per l'invio di una sola PEC:
  * Ricollocati tutti i messaggi relativi all'invio multiplo in una sezione apposita
- DragAndDrop su immagini in docEdit di documenti possibile solo se IWX disabilitato. Se si utilizza IWX ci sarebbero problemi nel caricamento
  dell'anteprima delle immagini caricate tramite DragAndDrop.
- Disattivato il caricamento files tramite IWX in caso di modifica rapida di documenti da raccoglitore di tipo
  indice per problemi di caricamento del componente: Viene utilizzato il modulo di upload files classico.
- Aggiunta assegnatari su casella di posta elettronica in ACL inibita a tutti gli utenti senza permessi
  di amministrazione della casella (Task #12943).
- Corretto vocabolario in campo "Protocollazione a cura di" su maschera di ricerca documenti (Task #12941).
- Corretto errore IWX in seguito a click su 'Copia Collegamento' su un documento (Task #12928).
- Corretto bug in upload file da edit rapido su raccoglitore di tipo Indice: Eliminati i commenti dal javascript di attivazione
  dell'upload DragAndDrop.
- Aggiunto check di notifica per CC su configurazione caselle PEC in ACL (Task #12848)

### Changed

- Gestione fascicoli e raccoglitori in preview HomePage
  Implementata possibilità di configurare se avere la barra laterale del menù aperta di default o meno

[4.10.2] - 2017-10-06
---------------------

### Fixed

- Eliminati da DocWay i componenti swfupload (file upload tramite Flash Player) per problemi di compatibilità su nuove versioni di
  Firefox (e forse Chrome): Componente sostituito da iframe con upload classico o Drag and Drop.
- Corretto bug in recupero info di assegnazione/visto (Task #12739): Non è sufficiente verificare il
  nome della persona, occorre controllare anche l'ufficio (potrebbe essere stata aggiunta in CC la stessa persona più volte con associato
  l'ufficio di appartenenza o responsabilità).
- Corretto bug su template home di SoginSAP.
- Corretta la visualizzazione del fascicolo in caso di protocolli interni privi di RPAM (modifica a controlli in template
  showdoc@datiarchivio) (Task #12558).

[4.10.1] - 2017-09-18
---------------------

### Added

- Abilitazione della modifica dei campi custom in caso di archivio settato come ReadOnly (la modifica dei campi custom viene concessa a tutti
  gli utenti che hanno la visibilità del documento).

### Fixed

- Eliminati i campi customSelect1 e customSelect2 sui doc in partenza derivanti da 'Rispondi' o 'Inoltra' su
  protocolli in arrivo (Task #12669).
- Aggiornata la visualizzazione dei campi 'date' sul widget di caricamento form di Bonita5 (Task #12117).
- Ripristinata la versione 1.0.2 del configuratore per problemi su caricamento di property tomcat 'TREDI_CONF_ROOT' custom per applicazione
  in base a suffisso webapp.

### Changed

- Visualizzazione dei campi customSelect1 e customSelect2 in showdoc di un documento solo se effettivamente abilitati da file di properties.

[4.10.0] - 2017-09-05
---------------------

### Added

- Implementata la logica e i template per l'inserimento dei documenti di repertorio in bozza
- Implementata la logica di presa in carico sul documento:
  * Richiesta presa in carico
  * Accettazione presa in carico
  * Filtro sui documenti presi in carico (anche su singolo utente) o da prendere in carico
  * Modale di richiesta presa in carico
  * Modale status presa in carico rispetto a tutti gli incaricati
  * Status del documento relativo alla presa in carico
  * Storicizzazione delle operazioni di presa in carico
  * Vaschetta documenti da prendere in carico (da parte dell'utente loggato)
- Corretto bug di assegnazione CC in docEditPartenza.
- Miglioramento del PDF di aggregazione dei documenti del raccoglitore:
  * Inserito il numero di pagina anche nell'indice del pdf dei raccoglitori di indice.
  * Tradotto il plug-in di formattazione testuale TinyMCE in lingua italiana.
  * Introdotto campo "oggetto per la stampa" attivato tramite property, che consente una formattazione testuale anche all'oggetto da visualizzare nel PDF delle voci di indice.
  Fix vari ai raccoglitori d'indice:
  * Aggiornamento della voce di indice al salvataggio della preview tramite "Modifica rapida"
  * Mantenimento della scrollbar all'apertura/chiusura/salvataggio del popup di modifica
  * Mantenimento della scrollbar all'apertura/chiusura/salvataggio del popup di inserimento tramite url
- Definizione dei CC sulle caselle di posta elettronica (sia archiviazione che interoperabilità) in ACL.
  La sezione "Responsabilità" è stata ampliata con la sezione "Assegnatari";
- Integrazione applet firma versione 4.0 (JNLP).
- Aggiunti i filtri su nrecord in maschera di ricerca fascicoli.
- Possibilità di assegnazione di indirizzi PEC a ruoli in ACL (in questo modo e' possibile supportare l'invio telematico di documenti assegnati in RPA ad un ruolo) (Task #11299).
- Nuova gestione watermark di segnatura su file PDF firmati PADES (Task #11298).
- Ulteriore miglioramento alle preview dei raccoglitori di tipo indice:
  * Classi css e toggle tramite ajax del fullscreen delle voci di indice;
  * Aggiunta property per applicazione automatica delle modifiche alle voci di indice (attivazione/disattivazione da file it.highwaytech.apps.xdocway.properties -> indiceRaccoglitori.autoApplyOrderChanges);
  * Mantenimento dello scroll nelle voci di inidice dopo qualsiasi chiamata ajax;
  * Miglioramento della visibilità del menù contestuale alle voci;
- Integrazione Elasticsearch per gestione indici (attivazione/disattivazione da file it.highwaytech.broker.properties).

### Fixed

- Controllato l'effettivo completamento del lookup sulla classificazione in caso di cambio classificazione su fascicolo o documenti (Task #12588).
- Migliorato l'aggiornamento di diritti di tipo 'ALFA' in ACL (Task #12554).

[4.9.5] - 2017-05-17
--------------------

### Added

- Implementata la copia degli allegati da un altro documento
- Implementata la shortcut nel menù delle voci di indice per settare il documento a "completato";
- Miglioramento alle preview dei raccoglitori di tipo indice:
  * Gestione multilivello delle voci dell'indice;
  * Ordinamento tramite drag and drop;
  * Differenti colorazioni sulle voci completate (distinzione con e senza allegati);
  * Anteprima del documento sempre visibile sulla parte destra del widget;
  * In cancellazione di una voce dall'indice impostata di default la cancellazione del documento dall'archivio;
- In generazione della distribuzione di un raccoglitore di tipo indice:
  * Aggiunta a tutti i documenti contenuti al suo interno (voci dell'indice) di eventuali CC specificati da file XML di definizione del raccoglitore di tipo Indice;
  * Impostazione di tutti i documenti contenuti al suo interno (voci dell'indice) come READ ONLY;

### Fixed

- Corretto bug in apertura di lookup su campi custom da pagina di modifica di raccoglitori.
- Corretto problema derivante da doppio click su pulsante di protocollazione remota tramite comunicazione intraAOO (Task #11457).

### Changed

- Modifica di un raccoglitore possibile solo se il raccoglitore non risulta già chiuso.
- Ottimizzata la query di ricerca dalla home di ACL in modo da migliorare le performance di esecuzione su eXtraWay (Task #11430).
- Sostituiti sul log di DocWay4 tutte gli errori in conversione dei dati con dei warning: Si tratta di conversioni richieste da componenti JSF che, in
  caso di fallimento, mostrano il dato non formattato.

[4.9.4] - 2017-02-28
--------------------

### Added

- Aggiunta servlet di controllo dello stack applicativo (DocWay4 -> DocWa4-service -> eXtraWay). Possibili risposte: OK in caso di stack
  funzionante, KO altrimenti. URL: http://HOST[:PORT]/DocWay4/stacktester?login=USERNAME[&matricola=MATRICOLA]
- Protocollazione massiva di documenti: Richiede l'attivazione della property 'protocollazioneMassivaSuSelezione' sul
  file it.highwaytech.apps.xdocway.properties.
- Assegnazione massiva di una selezione di documenti ad un Operatore Incaricato da lista titoli.
- In caso di cancellazione di immagini post conversione in PDF (property 'deleteImagesAfterPDF'), viene inibito l'invio telematico
  di protocolli in partenza fino al termine dell'operazione di conversione di tutte le immagini.

### Fixed

- In caso di property 'visualizzaScarto=No' viene comunque visualizzato il campo scarto sul documento (Task #11114).
- Corretto bug in caricamento di fascicoli personalizzati (con campi custom) in baso di interfaccia fascicoli specifica
  per un cliente (es. sottodirectory sogin).
- Corretto bug in aggiornamento di documenti contenuti in raccoglitore di tipo Indice da preview del documento. Dopo l'aggiornamento del doc. non
  risultava possibile procedere con il checkout/checkin dei file allegati.

### Changed

- Migliata l'interfaccia di generazione dell'aggregato di documenti inclusi all'interno di un raccoglitore di tipo Indice.
- Disattivazione di tutti i diritti su DocWay (ad esclusione di annotazioni e aggiunta CC) per blocco vecchi archivi di Equitalia (Task #11085).
- Sostituzione del pulsante 'Importa da Sigico2' con il pulsante 'Importa da URL' che permette di caricare contenuto da un URL esterno a DocWay
  recuperato da una lista predefinita di siti web (Task #9335).

[4.9.3] - 2017-02-01
--------------------

### Added

- Porting da DocWay3 dell'importazione di fascicoli in base a file excel (XLS) allegato ad un documento in arrivo (Task #9081).
- Aggiunto nuovo pulsante 'Stampa Indice'.
- Lo stato dei documenti caricati come voci dell'indice di un raccoglitore di tipo INDICE è settato di default a 'In lavorazione'.
- Possibilità di sostituire la generazione automatica della copertina di un raccoglitore di tipo INDICE con l'utilizzo del primo file (convertito in PDF)
  caricato sul primo nodo dell'indice (primo documento incluso nel raccoglitore).
- Aggiunta gestione account di ACL (xDams EC) a pacchetto standard di DocWay: Modificato campo ICO da text a select in pagina
  di modifica di un account (Task #9240).
- Porting della personalView 'ALBO' da DocWay3 (repertorio personalizzato).
- Porting dei fascicoli degli studenti da DocWay3 (fascicoli speciali).

### Fixed

- Corretto bug in AclCrawler nella costruzione delle query su mancanza di visualizzazione completa su repertori (Task #9337).
- Corretto BUG in risposta da doc. in partenza con corpo mail: Registrazione del corpo della mail anche nel doc in arrivo generato (Task #9292).
  Lo stesso fix è stato applicato sulla generazione di doc. non protocollati da protocollo in partenza con corpo mail valorizzato.
- Corrette le pagine di destinazione dei pulsanti di inserimento da showdoc di fascicoli personalizzati (nuovo, ripeti nuovo, ecc.).
- Corretta la formattazione in jsf della data delle decretazioni nella console APV che mostrava il giorno precedente a quello corretto.

### Changed

- Aggiunta la possibilità di modificare (aggiungere/rimuovere) i file associati a fatturePA passive (Task #9312). Richiede l'abilitazione
  della property 'fatturepa.editAttachments' sul file docway.properties.
- Ripristinato il supporto a formati di classificazione differenti da R/D (configurazione disponibile su DocWay3) (Task #9136).

[4.9.2] - 2016-12-15
--------------------

### Added

- Aggiunto il pulsante 'Protocolla' alle azioni rapide su un documento.
- Rifiuto di bozze in arrivo (invio email al mittente, assegnazione a specifico RPA, fascicolazione).
- Aggiunto pulsante per lo scaricamento in formato ZIP di tutti gli allegati di un documento.
- Gestione di raccoglitori di tipo 'indice' (raccoglitori per i quali è definito un set di documenti da generare).

### Fixed

- Ripristinato il caricamento dei titoli relativi a "Documenti non disponibili" (BUG segnalato sul Task #8914).
- Inibita la protocollazione di bozze in arrivo rifiutate da pagina di modifica del documento (Task #9123).
- In visualizzazione di documenti, corretto bug in visualizzazione degli assegnatari in caso di reso
  al protocollista (pulsante 'Rigetta').
- Corretto bug in modifica di una casella di posta elettronica nel caso di utente non amministratore di ACL (Task #8648).
- Corretti bug in inserimento/modifica di campi custom su multi-istanza di gruppo (Task #8713).
- In inserimento di documenti, corretto controllo di obbligatorietà sul campo 'allegato' in caso di inserimento di uno o più spazi (Task #9101).
- Corretto bug (problema JSF) nel caricamento di una pagina di showdoc tramite 'reload()', se richiesto dopo la visualizzazione di una personalView gestita
  tramite sottodirectory su docway (es. fatturepa, rep_standard, ecc.) (Task #8674).

### Changed

- Gestione delle restrizioni su aoo tramite extraquery su lookup (Task #8713).
- Spostati dal menù 'Nuovo' al menù 'Azioni sul doc.' i comandi 'Richiesta di pubblicazione' e 'Pubblica' riferiti alle
  rispettive funzionalità dell’Albo on-line (AOL).
- In ACL, gestione "mailbox out" per le caselle di archiviazione (oltre che per le caselle PEC): Compilazione non obbigatoria, ma
  necessaria in caso di attivazione di specifiche funzioni sull'archiviatore (es. rifiuto email in base agli allegati contenuti).
- Modifiche al template di Richiesta Pubblicazione AOL:
  * Campo "Tipo documento" obbligatorio;
  * Controlli su date di Pubblicazione (data di inizio pubblicazione successiva alla data odierna, data di fine pubblicazione successiva alla data di inizio pubblicazione);
  * In modifica del documento di RAOL possibilità di variare l'indice di classificazione.

[4.9.1] - 2016-11-18
--------------------

### Added

- Aggiornato IWX a ver. 3.0.11.0 e ver. 2.2.10r:
  * Supporto a dimensione massima dei file in upload.
- Comunicazione Intra-AOO: Protocollazione di un documento in arrivo su una differente AOO (selezione dell'RPA e protocollazione di un nuovo documento
  attraverso chiamate ai 3diWS). Richiede la configurazione della property 'intraAoo.codiciAmmAoo' (e di tutte le altre annesse) sul
  file it.highwaytech.apps.xdocway.properties.

### Fixed

- Ripristinato controllo su dimensione massima dell'allegato in upload di files.
- Corretto bug su encoding di caratteri speciali (es. accenti) in caso di filtro di autenticazione SQL.
- Corretto bug in visualizzazione dei flussi completati su Bonita 7.
- Corretto bug in visualizzazione del campo 'Stato Avanzamento' (SAL) di una fatturaPA (Task #8679).

### Changed

- Gestito il forceLogin sul filtro di autenticazione SQL (SqlAuthenticationServlet).

[4.9.0] - 2016-09-13
--------------------

### Added

- Integrazione nuova release IWX (con supporto a tutti i browser, ver. 3.x):
  * Possibilità di passare dalla ver. 2.x di IWX alla ver. 3.x e viceversa;
  * Necessaria configurazione del file docway.properties; Impostare 'iwx.legacyMode=false' e configurare i corretti valore per 'requiredVersionIWX' e 'currentVersionIWX'.
- Possibilità di attivare la gestione dei CC sui raccoglitori (inclusione dei CC anche su fascicoli e documenti inclusi nel raccoglitore). Richiede
  l'attivazione della property 'abilitaCCRaccoglitori' sul file it.highwaytech.apps.generic.properties.
- Eliminato UPPERCASE sull'oggetto all'interno della sezione 'Dati del Documento' in showdoc.
- Possibilità di attivare lo scarto di un documento su singoli ruoli assegnatari del doc. stesso. Richiede l'attivazione della
  property 'abilitaScartoSingoloRuolo' sul file it.highwaytech.apps.xdocway.properties.
- Possibilità di sostituire la vaschetta 'Ruoli' con una tendina che mostra tutti i ruoli ricoperti dall'operatore e per ogni ruolo la cardinalità
  dei documenti assegnati. Richiede l'attivazione della property 'abilitaSplitVaschettaRuoli' sul file it.highwaytech.apps.xdocway.properties.
- Aggiunto un alert di conferma di generazione di un documento non protocollato tramite Drag And Drop di files da homepage di DocWay.

### Fixed

- Corretto bug in svuotamento della password sulle caselle di posta elettronica e migliorata la visualizzazione del campo in modifica (Task #8373).
- Corretto bug in svuotamento dell'elenco di gestori di una casella di posta elettronica (Task #8347).
- Corretto bug in lookup responsabile in modifica di una casella di posta elettronica su ACL (Task #8260).

### Changed

- Aggiornato il componente javascript di upload tramite drag and drop di files da dropzone.js a jQuery fileUplaod (maggiore libertà su interfaccia utente).
- Migliorato l’aspetto grafico dei campi di ricerca su assegnatari all'interno delle pagine di ricerca di documenti e fascicoli (passaggio
  da uffici/persone a ruoli e viceversa).
- In inserimento/modifica del responsabile delle caselle di posta elettronica aggiunta la visualizzazione di matricola, cod. ufficio, cod. ruolo come
  campi non modificabili (retrocompatibilità con vecchia gestione caselle interne all'AOO).
- Distinzione fra caselle di archiviazione ed interoperabilità in lista titoli e showdoc di caselle di posta elettronica in ACL. Richiede l'aggiornamento
  del file conf di acl e la rigenerazione dei titoli (Task #7049).
- Possibilità di definire l'archivio xdocway di default da utilizzare (se differente da xdocwaydoc). Richiede la configurazione
  della property 'xdocway.dbName.default' su docway.properties (Task #8100).

[4.8.0] - 2016-06-14
--------------------

### Added

- Aggiunti filtri a vaschette custom: documenti letti/non letti, documenti scartati/non scartati (richiede l'abilitazione dei filtri su documenti
  letti/non letti, scartati/non scartati).
- Da maschera di ricerca dei documenti, possibilità di attivare i filtri su documenti letti/non letti, scartati/non scartati. Richiede l'attivazione
  della property 'abilitaFiltriDocNonLettiNonScartati' sul file 'it.highwaytech.apps.xdocway.properties'.
- Possibilità di visualizzare le info esplose sulla lista titoli di documenti. Richiede l'attivazione della property 'titles.info.expanded'
  sul file docway.properties.

### Fixed

- Visualizzazione di un alert di errore in caso di problemi durante il salvataggio di files su eXtraWay (SWFUpload, IWX) (Task #8133).
- Corretto bug nel riconoscimento del numero di protocollo da ricerca globale in altro a dx (Task #8119).
- Corretto bug in visualizzazione dell'ultima modifica nella storia di tutte le risorse (persone, strutture, ecc.) in ACL (Task #8113).

### Changed

- Costruzione dinamica (senza nome app predefinita) dell'URL di caricamento di files javascript relativi al locale (Task #8127).
- In ACL aggiunti i codici relativi al responsabile in visualizzazione di una casella di posta elettronica (Task #8120).
- In caso di aggiornamento delle vaschette tramite pulsante 'refresh' viene forzato l'aggiornamento delle vaschette anche nel caso in cui il timeout di cache
  non sia trascorso.

[4.7.0] - 2016-05-18
--------------------

### Added

- Login a DocWay tramite Slider CAPTCHA anzichè classico pulsante di submit.
- Test di connessione alle mailbox definite sulle caselle di posta elettronica in ACL.
- Separazione delle caselle di interoperabilità e archiviazione (gestite tramite archiviatore) dall'information unit 'aoo'. Creata
  una nuova Infomation Unit 'casellaPostaElettronica'.
- Possibilità di registrare destinatari non vincolati (inserimento libero senza lookup in ACL) sui documenti in partenza. Richiede l'attivazione
  della property 'destinatariLiberi' nel file 'it.highwaytech.apps.xdocway.properties'.
- Possibilità di assegnare più indirizzi PEC ad una struttura interna con selezione dell'indirizzo da utilizzare in
  fase di invio telematico di un documento con quella struttura in RPA

### Fixed

- Corretto bug in modifica caselle di posta elettronica: Impossibile rimuovere la spunta di "Salvataggio allegati su documenti differenti".
- Corretto bug in caricamento vocabolari in maschera di ricerca su campi extra dei fascicoli: Fornitore, Cod-SAP Fornitore.
- Visualizzazione dell'esito dell'operazione di inserimento documenti/fascicoli in raccoglitore (loadingbar con dettagli dell'attività) (Task #5292).
- Corretto bug in verifica permessi su azione di rispondi/risposta/inoltra (Task #5256).
- Disallineamento query fra Docway4 e Docway3 in caso di fascicolazione di documenti in fascicoli speciali (Task #5211).
- Corretta la mancata visualizzazione del campo 'Spesa' all'interno della sezione 'Altro' in showdoc di un Documento (Task #5205).

### Changed

- La protocollazione di documenti in bozza è stata vincolata al permesso di modifica sul documento stesso.

[4.6.3] - 2016-04-26
--------------------

### Added

- Upload file tramite Drag And Drop (caricamento allegati in documenti, creazione di documenti non protocollati tramite upload di un file). Richiede la configurazione delle seguenti
  properties sul file 'it.highwaytech.apps.xdocway.properties': abilitaDragAndDropUploadFiles, abilitaDragAndDropCreateDocVarie, dragAndDrop.templateDoc.
- Possibilità di attivare lo scarto CC massivo di documenti assegnati ad una persona o su ruoli. Richiede la configurazione delle seguenti properties sul file 'it.highwaytech.apps.xdocway.properties':
  * 'abilitaScartoMassivoDocs', abilitazione dello scarto massivo di documenti da lista titoli (visualizzazione del pulsante) ('si', 'no' - Default = 'no');
  * 'scartoMassivoDocs.abilitaVisto', abilita il set del visto in caso di attivita' di scarto massivo di documenti da lista titoli ('si', 'no' - Default = 'si').
- Pagina di login multiarchivio su DocWay. Per il funzionamento richiede la configurazione delle relative properties
  sul file it.highwaytech.apps.xdocway.properties ('multiarchLogin.mode' e correlate).
- Caricamento della homepage di DocWay personalizzata per singolo utente tramite 'Profilo Personale'. Richiede le seguenti properties attive sul file 'it.highwaytech.apps.xdocway.properties':
  * customUserHomePage, per il caricamento dei documenti recenti sull'homepage di docway;
  * abilitaHomePageDocWayCustom, per la definizione (da profilo personale di ogni utente) della tipologia di lista da caricare sulla homepage di docway.
- Formato della lista titoli (lista o tabella) personalizzata per singolo utente tramite definizione da 'Profilo Personale': Richiede l'attivazione della property 'titles.mode.switch.enabled'
  sul file docway.properties della webapp DocWay4.

[4.6.2] - 2016-03-21
--------------------

### Added

- Possibilità di specificare una query custom (in formato eXtraWay) dalla pagina di ricerca globale di documenti. Richiede l'attivazione
  della property abilitaExtraQueryOnRicercaGlobale sul file it.highwaytech.apps.xdocway.properties (disattivata di default).
- Gestione del check di 'Controlla variazione nome ufficio/persona' in trasferimento RPA di un documento o selezione di documenti.
- Sincronizzazione utenti con DB di Bonita BPM in fase di login su DocWay. Registrazione utenti su Bonita con password recuperata
  da file di properties e identica per tutti gli utenti (accesso a Bonita Portal non consentito, se non per gli amministratori -
  autenticazione integrata fra DocWay e Bonita BPM tramite recupero del COOKIE di Sessione di Bonita).
- Integrazione con Bonita7.
- Aggiunta la funzionalità di controllo del certificato di firma digitale su file P7M e PDF (firma PADES): Estrazione delle
  informazioni in esso contenuto in modalità OFFLINE.
- Definiti due nuovi tipi di campi custom in visualizzazione di documenti (solo showdoc, non previsti in inserimento/modifica):
  * tipo 'gmap' per la visualizzazione di mappe di google;
  * tipo 'html' per l'inclusione di codice html.
- Aggiunta la gestione dei campi custom ai repertori personalizzati (PersonalView).

### Fixed

- Corretto bug in controllo delle ripetizioni su campo oggetto in modifica di un documento con oggetto non
  modificabile (Task #5009).
- Corretti bugs in focus di un campo input dopo un'azione di lookup (fix JavaScript):
  * Problema di focus su IE11;
  * Problema nel caso in cui il campo dopo il lookup sia readonly.
- Corretto bug in aggiornamento del nome ufficio/persona in trasferimento RPA di un fascicolo: check
  'Controlla variazione nome ufficio/persona' (Task #4956).
- Corretto escape del corpo dei messaggi di posta per il modulo 'Ricezione Posta' (Task #4910).

[4.6.1] - 2016-02-04
--------------------

### Added

- In stampa repertorio fascicoli, in caso di lookup su campo Proprietario sono stati esclusi i profili dai risultati ottenuti (ricerca delle sole persone interne).
- Aggiunta favicon all'applicazione DocWay.
- Porting della personalView 'Contratti' da DocWay3 (repertorio personalizzato).
- Aggiunti i protocolli IMAP e IMAP-SSL alla configurazione delle mailbox per lo scaricamento posta tramite MailArchiver (sezione PEC in ACL).

### Fixed

- In caso di protocollazione di una bozza da pagina di modifica, invio del parametro '*sendMailRifInterni' in modo da forzare l'invio delle
  mail di notifica in caso di protocollazione di bozze (property protocollazioneBozzeInvioEmailNotifica attiva).
- Ripristinata la visualizzazione di etichette personalizzate per 'Dati Attività' e 'Dati Personali' in visualizzazione
  di una persona esterna.
- Corretto bug in caricamento nuova immagine tramite IWX dopo un'attività di eliminazione pagine da tiff multipagina (sempre
  tramite IWX) (Task #4270)
- Applicato l'escape su tutti i campi di output di DocWay e ACL in modo da evitare HTML e Javascript Injection.
- Corretto bug in invio dati fatture a NAV (casi di doppia invocazione dei WS di NAV) (Task #3547).
- Modificata chiamata alla funzione javascript di init di IWX per problemi di escape sul valore di customTupleName.
- Ripristinato il dialog di richiesta aggiunta/sovrascrittura CC in caso di lookup su voce di indice (Task #3747).

### Changed

- Migliorata la selezione delle opzioni di stampa del controllo di gestione (Task #4653):
  * In caso di selezione di 'Stampa mittenti per UOR' viene automaticamente settata la spunta su 'Stampa dettaglio per UOR' (altrimenti il flag sarebbe ignorato);
  * In caso di selezione di 'Stampa mittenti per protocollista' viene automaticamente settata la spunta su 'Stampa dettaglio per protocollista' (altrimenti il flag sarebbe ignorato).
- Migliorato il messaggio di ritorno in caso di errore nel caricamento di una selezione documenti SCADUTA (su eXtraWay) da vaschette di DocWay. Segnalazione
  di errore 'File non trovato' che provocava il logout da DocWay.
- Modificato il livello di errore da fatale a warning in caso di SQLException sulle pagine di ricerca di DocWay.
- Integrazione di IWX versione 2.2.6r:
  * Gestione multiriga in stampa segnatura e info (elenco CC con ritorni a capo);
  * Corretto bug in salvataggio delle preferenze di stampa (profilo personale).
- Migliorato il controllo del formato numerico di valuta (decimali con punto) nei template di fatturePA attive/passive.

[4.6.0] - 2015-12-01
--------------------

### Added

- Aggiunta delle console Decretazioni, Ordini e Fatture per APV.
- Aggiunto un messaggio di warning nelle pagine di ricerca in ACL in caso di restrizioni parziali su anagrafica. Per restrizioni parziali si intende
  il fatto che un operatore sia privo di restrizioni su anagrafica interna ma non sull'esterna o viceversa. Richiede l'abilitazione dal file docway.properties
  (abilitaWarningSuRestrizioniParziali=si).
- Aggiunto un tema specifico per Equitalia in modo da facilitare il riconoscimento dell'archivio al quale è collegato l'operatore.
- Possibilità di definire dei livelli di visibilità personalizzabili tramite i quali esterndere quelli classici di DocWay:
  * I livelli custom devono essere definiti attraverso la property 'livelliRiservatezzaPersonalizzati' all'interno del file it.highwaytech.apps.generic.properties
  * Per completare la configurazione occorre seguire le istruzioni specificate all'interno del file di properties (completamento file dei diritti, etichette da utilizzare per le visibilità custom, ecc.)
- Gestione della motivazione di rifiuto di una fatturaPA attiva/passiva: notifiche di esito committente (EC) e notifiche
  di esito cedente (NE).
- In ACL possibilità di attivare, per gli utenti privi di restrizioni alla propria AOO, una select per selezionare l'AOO sulla quale
  filtrare i risultati delle ricerche in ACL (solo se attiva la configurazione di archiviaoo.xml).
- Definizione dei gestori di caselle di archiviazione e interoperabilità in ACL (modifica di PEC):
  * Possibilità di specificare, per ogni casella, gli utenti che possono modificare i dati della casella stessa;
  * Sono stati definiti due livelli: "Titolare" e "Cambio Password". Un utente con assegnato il livello "Titolare" può modificare
  ogni dato relativo alla casella, mentre un utente con livello "Cambio Password" può solamente modificare lo username e la password da utilizzare
  per la connessione alla casella.
- Aggiunto alle Smart Actions di un documento/fascicolo il pulsante di cambio proprietario ("Proprietario" sul documento e "Trasferisci"
  sul fascicolo).
- Eliminati tutti i riferimenti a broker.jar e common4.jar. Spostati i metodi chiamati in specifici JAR di utilities.
- Passaggio a Maven.
- Encoding dei contenuti (documenti xml) da ISO-8859-1 a UTF-8.

### Fixed

- Rimosso dal progetto il componente server dei WS di SoginSAP per problemi di compatibilità e maggiore difficoltà di
  manutenzione in fase di installazione (la versione attualmente inclusa nel progetto non risulta funzionante neppure
  sul cliente, viene utilizzata per la chiamata la vecchia webapp 'SoginSAP').
  Occorre scaricare ed installare lo specifico modulo "SoginSAPDocWS".
- Caricamento del file di properties di soginSAP (da url PrettyFaces) tramite modulo configuratore (riscontrato durante la verifica del Task #4289).
- Corretto bug in lookup su ruoli in rif interni: Non venivano filtrati i risultati in base all'AOO di appartenenza del documento/fascicolo.
- In caso di lookup su mittente/destinatario (inserimento di doc in arrivo/partenza), se si seleziona una persona esterna non deve essere possibile
  inserire una persona da lookup su firmatario/cortese attenzione. L'inserimento del firmatatario/cortese attenzione ha senso solo se è stata
  selezionata una struttura esterna come mittente/destinatario. (Task #3960)
- Corretto bug in selezione di gruppo di diritti con nome duplicato su più archivi (es. Riservatezza) in inserimento/modifica/visaulizzazione
  di persone interne/profili.

### Changed

- Limitato (come da specifiche SdI) a 255 il numero di caratteri nella motivazione di rifiuto di una fatturaPA passiva.
- Applicate le restrizioni su AOO in ricerche su anagrafica interna/esterna in modo da
  supportare installazioni multi-aoo con anagrafiche esterne non condivise (caso di archivi ACL accorpati in
  uno unico con conflitti su codici).
- Aggiunto cod_amm_aoo nel caricamento di rif esterni dal documento.
- In ACL, anche per utenti privi di restrizioni sulla propria AOO, in fase di inserimento persone/strutture interne, profili, gruppi, ruoli o AOO
  non è possibile modifica i cod_amm e cod_aoo di default (in questo modo si evita un utilizzo potenzialmente "pericoloso" di ACL).

