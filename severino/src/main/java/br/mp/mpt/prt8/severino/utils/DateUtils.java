package br.mp.mpt.prt8.severino.utils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utilitários de data.
 * 
 * @author sergio.eoliveira
 *
 */
public final class DateUtils {
	private DateUtils() {
		super();
	}

	/**
	 * Converter data para localdate.
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Converter localdate para javautildate.
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Formatar data.
	 * 
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Constantes.DEFAULT_LOCALE).format(date);
	}

}
