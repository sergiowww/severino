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
	 * @param idLocal
	 * 
	 * @return
	 */
	@Query
	List<ControleMotorista> findUltimoAgrupadoPorMotorista(@Param("idLocal") Integer idLocal);

	/**
	 * Buscar motoristas pelo cargo.
	 * 
	 * @param idLocal
	 * 
	 * @param motorista
	 * @return
	 */
	@Query
	List<Motorista> findMotoristasDisponiveis(@Param("fluxo") Fluxo fluxo, @Param("idLocal") Integer idLocal);

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

	/**
	 * Buscar o controle de ponto anterior em relação a data informada para o
	 * motorista em questão.
	 * 
	 * @param dataHora
	 * @param idMotorista
	 * @return
	 */
	@Query
	ControleMotorista findControleAnterior(@Param("dataHora") Date dataHora, @Param("idMotorista") Integer idMotorista);

	/**
	 * Buscar o próximo controle de ponto em relação a data informada para o
	 * motorista.
	 * 
	 * @param dataHora
	 * @param idMotorista
	 * @return
	 */
	@Query
	ControleMotorista findControleProximo(@Param("dataHora") Date dataHora, @Param("idMotorista") Integer idMotorista);

	/**
	 * Contar quantos existem com a mesma data para o mesmo motorista.
	 * 
	 * @param dataHoraCorrente
	 * @param motorista
	 * @param id
	 * @return
	 */
	Long countByDataHoraAndMotoristaAndIdNot(Date dataHoraCorrente, Motorista motorista, Integer id);

}
