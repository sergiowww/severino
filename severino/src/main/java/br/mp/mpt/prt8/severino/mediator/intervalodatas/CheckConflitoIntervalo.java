package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.AbstractEntityIntervaloData;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Verificar se existe algum intervalo de data persistido no banco que se
 * sobrepõe aos valores de entrada e saída informada para o mesmo individuo.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <E>
 */
public class CheckConflitoIntervalo<T, E extends AbstractEntityIntervaloData<Integer>> {

	private final IntervaloValidator<T> validator;
	private final String mensagem;

	/**
	 * Construtor.
	 * 
	 * @param validator
	 * @param mensagem
	 */
	public CheckConflitoIntervalo(IntervaloValidator<T> validator, String mensagem) {
		this.validator = validator;
		this.mensagem = mensagem;
	}

	/**
	 * Acionar a consulta, verificar o resultado e lançar a exceção se o
	 * resultado não estiver correto.
	 * 
	 * @param entidade
	 * @param valor
	 */
	public void validar(E entidade, T valor) {
		Date saida = entidade.getSaida();
		Date entrada = entidade.getEntrada();
		Integer id = EntidadeUtil.getIdNaoNulo(entidade);
		
		List<Integer> idsConflitantes = null;
		if (entrada != null && saida != null && !(idsConflitantes = validator.findIdsConflitos(entrada, saida, valor, id)).isEmpty()) {
			String idsString = StringUtils.collectionToCommaDelimitedString(idsConflitantes);
			throw new NegocioException(String.format(mensagem, valor, idsString));
		}
	}
}
