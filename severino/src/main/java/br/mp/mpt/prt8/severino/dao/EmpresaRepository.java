package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Empresa;

/**
 * DAO para operações com a empresa.
 * 
 * @author sergio.eoliveira
 *
 */
public interface EmpresaRepository extends BaseRepositorySpecification<Empresa, Integer> {

	/**
	 * Contar ocorrências com o mesmo nome e identificador diferente do
	 * informado.
	 * 
	 * @param nome
	 * @param id
	 * @return
	 */
	Long countByNomeIgnoreCaseAndIdNot(String nome, Integer id);
}
