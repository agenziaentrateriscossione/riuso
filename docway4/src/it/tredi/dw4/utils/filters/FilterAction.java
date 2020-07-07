package it.tredi.dw4.utils.filters;

/**
 * Enumeratore che indica al {@link LoginFilter#doFilter}
 * che operazione occorre intraprendere una volta caricato il bean di
 * applicazione
 */
public enum FilterAction {
	/** Procede con la richiesta originale */
	PROCEED,
	/** Reindirizza la richiesta a home.jsf */
	HOME,
	/** Reindirizza la richiesta ad init.jsf per la gestione della selezione utente/società o per la gestione dell'errore */
	INIT,
	/** Reindirizza la richiesta per il cambio password obbligatorio */
	CHANGE_PASSWORD,
	/** Procede con la richiesta se l'url è gestito con pretty faces, altrimenti rimanda alla home */
	PRETTYFACES_CHECK
}
