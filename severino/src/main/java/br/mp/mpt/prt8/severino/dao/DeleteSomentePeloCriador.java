package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Usuario;

/**
 * Indica que para esta entidade, a exclus�o deve obedecer algumas regras.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <ID>
 */
public interface DeleteSomentePeloCriador<T, ID> {
	/**
	 * Buscar o registro pelo identificador e pelo usu�rio.
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	T findByIdAndUsuario(ID id, Usuario usuario);
}
