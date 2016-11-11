package br.mp.mpt.prt8.severino.mediator.intervalodatas;

import java.util.Date;
import java.util.List;

/**
 * Interface para realizar a consulta e vericar se existe algum intervalo de
 * data se sobreponto ao informado para o registro identificado no parâmetro
 * valor.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 */
public interface IntervaloValidator<T> {

	/**
	 * Retornar uma lista com os ids que estão em conflito com o intervalo
	 * informado.
	 * 
	 * @param entrada
	 * @param saida
	 * @param valor
	 * @param id
	 * @return
	 */
	List<Integer> findIdsConflitos(Date entrada, Date saida, T valor, Integer id);

}
