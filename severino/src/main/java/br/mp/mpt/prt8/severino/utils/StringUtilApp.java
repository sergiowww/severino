package br.mp.mpt.prt8.severino.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

/**
 * Utilitários de string para dados do ldap.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class StringUtilApp {

	private static final Pattern PATTERN_CN_NOME_USUARIO = Pattern.compile("(?<=(cn\\=))([^,]*)", Pattern.CASE_INSENSITIVE);

	/**
	 * Extrair nome do usuário do cn.
	 * 
	 * @param dn
	 * @return
	 */
	public static String extrairNomeUsuario(String dn) {
		return extrairPadrao(dn, PATTERN_CN_NOME_USUARIO);
	}

	/**
	 * Extrair padrão de uma string.
	 * 
	 * @param valor
	 * @param pattern
	 * @return
	 */
	public static String extrairPadrao(String valor, Pattern pattern) {
		Matcher matcher = pattern.matcher(valor);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * Remover a máscara de campos.
	 * 
	 * @param valor
	 * @return
	 */
	public static String limparMascara(String valor) {
		if (!StringUtils.isEmpty(valor)) {
			return valor.replaceAll("\\D+", "");
		}
		return valor;
	}

	/**
	 * @param valores
	 * @return <code>true</code> se algum dados estiver preenchido.
	 */
	public static boolean algumValorPreenchido(Object... valores) {
		return Stream.of(valores).anyMatch(v -> (v instanceof String && !StringUtils.isEmpty(v)) || v != null);
	}
}
