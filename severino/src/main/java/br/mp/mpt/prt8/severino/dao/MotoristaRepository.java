package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Motorista;

/**
 * Dao para motorista.
 * 
 * @author sergio.eoliveira
 *
 */
public interface MotoristaRepository extends BaseRepositorySpecification<Motorista, Integer> {

	/**
	 * Contar motorista pelo idenficador e cargo.
	 * 
	 * @param id
	 * @param motorista
	 * @return
	 */
	Long countByIdAndCargo(Integer id, Cargo motorista);

	/**
	 * Não permitir que o um motorista com a mesma matícula seja cadastrado.
	 * 
	 * @param matricula
	 * @param id
	 * @return
	 */
	Long countByMatriculaIgnoreCaseAndIdNot(String matricula, Integer id);

	/**
	 * Buscar motoristas que não tem qualquer registro de ponto.
	 * 
	 * @param motorista
	 * @param idLocal
	 * @return
	 */
	@Query
	List<Motorista> findMotoristasSemRegistroPonto(@Param("cargoMotorista") Cargo motorista, @Param("idLocal") Integer idLocal);

	/**
	 * Buscar motoristas por cargo.
	 * 
	 * @param motorista
	 * @return
	 */
	List<Motorista> findByCargoIn(List<Cargo> motorista);
}
