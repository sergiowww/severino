package br.mp.mpt.prt8.severino.dao;

import br.mp.mpt.prt8.severino.entity.Visitante;

/**
 * DAO para visitante.
 * 
 * @author sergio.eoliveira
 *
 */
public interface VisitanteRepository extends BaseRepositorySpecification<Visitante, Integer> {

	/**
	 * Contar registros que tenha um documento igual ao informado onde o
	 * identificador não seja o mesmo.
	 * 
	 * @param documento
	 * @param id
	 * @return
	 */
	Long countByDocumentoIgnoreCaseAndIdNot(String documento, Integer id);

	/**
	 * Buscar visitante pelo número do documento.
	 * 
	 * @param documento
	 * @return
	 */
	Visitante findByDocumentoIgnoreCase(String documento);
}
