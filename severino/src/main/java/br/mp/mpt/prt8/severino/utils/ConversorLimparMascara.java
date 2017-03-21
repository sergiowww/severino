package br.mp.mpt.prt8.severino.utils;

import javax.persistence.AttributeConverter;

/**
 * Conversor para limpar a máscara do valor antes de gravar no banco de dados.
 * 
 * @author sergio.eoliveira
 *
 */
public class ConversorLimparMascara implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return StringUtilApp.limparMascara(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return dbData;
	}

}
