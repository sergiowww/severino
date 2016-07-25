package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Setor;

/**
 * DAO para setor.
 * 
 * @author sergio.eoliveira
 *
 */
public interface SetorRepository extends BaseRepositorySpecification<Setor, Integer> {

	/**
	 * Contar ocorrências com o mesmo nome e identificador diferente do
	 * informado.
	 * 
	 * @param nome
	 * @param andar
	 * @param sala
	 * @param id
	 * @return
	 */
	Long countByNomeIgnoreCaseAndAndarAndSalaIgnoreCaseAndIdNot(String nome, Short andar, String sala, Integer id);

	/**
	 * Contar ocorrências com o mesmo nome e identificador diferente do
	 * informado.
	 * 
	 * @param nome
	 * @param andar
	 * @param id
	 */
	void countByNomeIgnoreCaseAndAndarAndSalaIsNullAndIdNot(String nome, Short andar, Integer id);

}
