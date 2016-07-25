package br.mp.mpt.prt8.severino.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Visita;

/**
 * DAO para visitante.
 * 
 * @author sergio.eoliveira
 *
 */
public interface VisitaRepository extends BaseRepositorySpecification<Visita, Integer> {

	/**
	 * Buscar registros que ainda não possuem a data de saída.
	 * 
	 * @return
	 */
	List<Visita> findBySaidaIsNull();

	/**
	 * Buscar uma visita para ser eliminada que tenha o mesmo id e usuário que
	 * informada e que tenha sido cadastradado na mesma data.
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@Query("select v from Visita as v where cast(v.dataHoraCadastro as date) = current_date and v.id = :id and upper(v.usuario) = upper(:usuario)")
	Visita findByIdAndUsuario(@Param("id") Integer id, @Param("usuario") Usuario usuario);

	/**
	 * Verificar se o mesmo visitante já não entrou no prédio.
	 * 
	 * @param documento
	 * @return
	 */
	@Query("select count(v) from Visita as v inner join v.visitante as vi where upper(vi.documento) = upper(:documento) and v.saida is null and v.id <> :id")
	Long countByUsuarioAndSaida(@Param("documento") String documento, @Param("id") Integer id);

	/**
	 * Contar se o mesmo visitante possui um intervalo de data conflitante.
	 * 
	 * @param entrada
	 * @param saida
	 * @param documento
	 * @param id
	 * @return
	 */
	@Query("select v.id from Visita as v inner join v.visitante as vi where upper(vi.documento) = upper(:documento) and v.id <> :id  and :saida >= v.entrada AND v.saida >= :entrada")
	List<Integer> findIdsByDocumentoAndIntervalo(@Param("entrada") Date entrada, @Param("saida") Date saida, @Param("documento") String documento, @Param("id") Integer id);

}
