package br.mp.mpt.prt8.severino.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitários de string para dados do ldap.
 * 
 * @author sergio.eoliveira
 *
 */
public class LdapStringUtil {

	private static final Pattern PATTERN_CN_NOME_USUARIO = Pattern.compile("(?<=(cn\\=))([^,]*)",
			Pattern.CASE_INSENSITIVE);

	private LdapStringUtil() {
		super();
	}

	/**
	 * Extrair nome do usuário do cn.
	 * 
	 * @param dn
	 * @return
	 */
	public static String extrairNomeUsuario(String dn) {
		Matcher matcher = PATTERN_CN_NOME_USUARIO.matcher(dn);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
