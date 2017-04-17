package br.mp.mpt.prt8.severino.utils;

import br.mp.mpt.prt8.severino.entity.AbstractEntity;

/**
 * Utilit�rios para entidades.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class EntidadeUtil {
	private static final int ID_INVALIDO = -1;

	/**
	 * Retorna um identificador n�o nulo mas inv�lido.
	 * 
	 * @param entidade
	 * @return
	 */
	public static Integer getIdNaoNulo(AbstractEntity<Integer> entidade) {
		if (entidade == null) {
			return ID_INVALIDO;
		}
		Integer id = entidade.getId();
		if (id == null) {
			id = ID_INVALIDO;
		}
		return id;
	}

}
