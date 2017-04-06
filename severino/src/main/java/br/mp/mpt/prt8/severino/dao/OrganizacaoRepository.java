package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Organizacao;

/**
 * Reposit�rio para a organiza��o.
 * 
 * @author sergio.eoliveira
 *
 */
public interface OrganizacaoRepository extends BaseRepositorySpecification<Organizacao, Integer> {

	/**
	 * Buscar pelo nome.
	 * 
	 * @param organizacaoNome
	 * @return
	 */
	Organizacao findByNomeIgnoreCase(String organizacaoNome);

}
