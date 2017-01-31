package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import br.mp.mpt.prt8.severino.entity.AbstractEntityIntervaloData;

/**
 * Validação dos intervalos.
 * 
 * @author sergio.eoliveira
 *
 */
public class ValidarIntervalo {
	public static final String VALIDACAO_DATA_ENTRADA = "A data de entrada não pode ser vazia!";
	public static final String VALIDACAO_INTERVALO_ENTRADA_SAIDA = "A data/hora de saída não pode estar antes da entrada!";
	public static final String MENSAGEM_DATA_FUTURA = "Não pode ser uma data futura";

	/**
	 * Construtor.
	 */
	private ValidarIntervalo() {
		super();
	}

	/**
	 * Validar obrigatoriedade da data de entrada.
	 * 
	 * @param entidade
	 * @return
	 */
	public static boolean isDataEntradaValida(AbstractEntityIntervaloData<?> entidade) {

		return entidade.getId() != null && entidade.getEntrada() == null;
	}

	/**
	 * Validar consistência dos intervalos de entrada e saída.
	 * 
	 * @param entidade
	 * @return
	 */
	public static boolean isIntervaloEntradaSaidaValido(AbstractEntityIntervaloData<?> entidade) {
		return entidade.getSaida() != null && entidade.getEntrada() != null && entidade.getSaida().before(entidade.getEntrada());
	}
}
