package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Veiculo;

/**
 * DAO para acesso aos dados do veículo.
 * 
 * @author sergio.eoliveira
 *
 */
public interface VeiculoRepository extends BaseRepositorySpecification<Veiculo, String> {

	/**
	 * Buscar viaturas do mp.
	 * 
	 * @param local
	 * 
	 * @return
	 */
	List<Veiculo> findByViaturaMpTrueAndAtivoTrueAndLocal(Local local);

	/**
	 * Selecionar o valor do campo viaturamp do veículo pela placa
	 * 
	 * @param placa
	 * @return
	 */
	@Query("select v.viaturaMp from Veiculo as v where v.id = :id")
	Boolean getViaturaMpById(@Param("id") String placa);

	/**
	 * Listar todos os veículos de servidores e membros.
	 * 
	 * @param cargos
	 * @param idLocal
	 * @return
	 */
	@Query("select v from Veiculo as v inner join v.motorista as m inner join v.local as loc where m.cargo in (:cargos) and v.ativo = true and loc.organizacao.id = :idOrganizacao")
	List<Veiculo> findByCargoIn(@Param("cargos") List<Cargo> cargos, @Param("idOrganizacao") Integer idOrganizacao);

	/**
	 * Buscar veículo pela placa ignorando a capitalização.
	 * 
	 * @param trim
	 * @return
	 */
	Veiculo findByIdIgnoreCase(String id);

	/**
	 * Contar veiculos válidos como veículos de visitantes.
	 * 
	 * @param id
	 * @return
	 */
	Long countByMotoristaIsNullAndViaturaMpFalseAndId(String id);

}
