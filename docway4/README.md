# DocWay4

### URL di Accesso

##### URL classico con indicazione del DB

###### DocWay

Formato URL:
```
http://HOST[:PORT]/DocWay4/docway/index.jsp[?db=xdocwaydoc]
```

* Il parametro **db** non è obbligatorio, se non specificato viene valorizzato di default con '*xdocwaydoc*'. Occorre specificare il parametro nel caso in cui ci si debba collegare ad un archivio differente da xdocwaydoc (es. archivio periferico).

E' possibile specificare altri parametri nell'URL in modo da attivare determinate funzioni.
* **debug=true**, per attivare il debug dell'interfaccia utente (vengono mostrati gli XML restituiti dal service all'interno delle pagine dell'applicazione)
* **forceLogin=true**, per forzare il login (viene prima eseguito il destroy della sessione corrente)

Da file docway.properties è possibile specificare il nome dell'archivio docway da utilizzare se non specificato un differente archivio tramite parametro 'db'. La property da settare è la seguente:
* **xdocway.dbName.default** (impostare il valore sul configuratore)

###### ACL

Formato URL:
```
http://HOST[:PORT]/DocWay4/acl/index.jsp
```

##### URL di login multiarchivio

Formato URL:
```
http://HOST[:PORT]/DocWay4/docway/indexmultiarch.jsp
```

##### URL per accesso ADM

Formato URL:
```
http://HOST[:PORT]/DocWay4/docway/indexadm.jsp
```
##### URL per test stack applicativo

Formato URL:
```
http://localhost:8080/DocWay4/stacktester?login=USERNAME[&matricola=MATRICOLA]
```

* Il parametro **login** è obbligatorio ed identifica l'utente da utilizzare per il test
* Nel caso in cui lo username sia associato ad una multilogin occorre specificare anche il parametro **matricola** in modo da identificare un'utente specifico

I possibili valori di ritorno della chiamata sono i seguenti:
* **OK** in caso di stack completamente funzionante (DocWay4 -> DocWay4-service -> eXtraWay)
* **KO** in caso di errori ad un qualsiasi livello dello stack
