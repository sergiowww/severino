package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;

/**
 * Reposit�rio de locais.
 * 
 * @author sergio.eoliveira
 *
 */
public interface LocalRepository extends BaseRepositorySpecification<Local, Integer> {

	/**
	 * Procurar pelo nome sem considerar a capitaliza��o.
	 * 
	 * @param localNome
	 * @param organizacao
	 * @return
	 */
	Local findByNomeIgnoreCaseAndOrganizacao(String localNome, Organizacao organizacao);

}
