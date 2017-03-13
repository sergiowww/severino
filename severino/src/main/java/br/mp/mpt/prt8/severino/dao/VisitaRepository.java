package br.mp.mpt.prt8.severino.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.IntervaloValidator;

/**
 * DAO para visitante.
 * 
 * @author sergio.eoliveira
 *
 */
public interface VisitaRepository extends BaseRepositorySpecification<Visita, Integer>, IntervaloValidator<String>, DeleteSomentePeloCriador<Visita, Integer> {

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
	@Override
	Visita findByIdAndUsuario(@Param("id") Integer id, @Param("usuario") Usuario usuario);

	/**
	 * Verificar se o mesmo visitante já não entrou no prédio.
	 * 
	 * @param documento
	 * @return
	 */
	@Query
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
	@Query
	@Override
	List<Integer> findIdsConflitos(@Param("entrada") Date entrada, @Param("saida") Date saida, @Param("documento") String documento, @Param("id") Integer id);

	/**
	 * Recupera todas as visitas com data de entrada na data corrente.
	 * 
	 * @param idVisita
	 * @return
	 */
	@Query("select v from Visita as v inner join v.visitante as vi where cast(v.entrada as date) = current_date or v.id = :idVisita order by vi.nome")
	List<Visita> findAllRegistradasHoje(@Param("idVisita") Integer idVisita);

	/**
	 * Buscar a data de cadastro do registro.
	 * 
	 * @param id
	 * @return
	 */
	@Query("select v.dataHoraCadastro from Visita as v where v.id = :id")
	Date findDataHoraCadastroById(@Param("id") Integer id);

}
