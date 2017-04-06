package br.mp.mpt.prt8.severino.utils;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Constantes da aplicação.
 * 
 * @author sergio.eoliveira
 *
 */
public interface Constantes {
	Locale DEFAULT_LOCALE = new Locale("pt", "BR");
	TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone("America/Fortaleza");
	String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
	String KEY_USER_SEARCH_BASE = "userSearchBase";
}
