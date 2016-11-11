package br.mp.mpt.prt8.severino.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.ControleMotorista;
import br.mp.mpt.prt8.severino.entity.Fluxo;
import br.mp.mpt.prt8.severino.entity.Motorista;

/**
 * DAO para a entidade controle de motorista.
 * 
 * @author sergio.eoliveira
 *
 */
public interface ControleMotoristaRepository extends BaseRepositorySpecification<ControleMotorista, Integer> {

	/**
	 * Buscar o último controle de ponto de cada motoristas.
	 * 
	 * @return
	 */
	@Query
	List<ControleMotorista> findUltimoAgrupadoPorMotorista();

	/**
	 * Buscar motoristas pelo cargo.
	 * 
	 * @param motorista
	 * @return
	 */
	@Query
	List<Motorista> findMotoristasDisponiveis(@Param("fluxo") Fluxo fluxo);

	/**
	 * Buscar o ultimo ponto de controle de um motorista.
	 * 
	 * @param idMotorista
	 * @return
	 */
	@Query
	List<ControleMotorista> findUltimoControle(@Param("idMotorista") Integer idMotorista);

	/**
	 * Buscar a ultima data registrada daquele motorista.
	 * 
	 * @param motorista
	 * @return
	 */
	@Query("select max(c.dataHora) from ControleMotorista as c where c.motorista.id = :idMotorista")
	Date findMaxDataHoraByMotorista(@Param("idMotorista") Integer idMotorista);

}
