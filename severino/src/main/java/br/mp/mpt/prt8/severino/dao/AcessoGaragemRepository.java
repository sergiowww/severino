package br.mp.mpt.prt8.severino.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.AcessoGaragem;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.mediator.intervalodatas.IntervaloValidator;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * DAO para acesso o acesso a garagem.
 * 
 * @author sergio.eoliveira
 *
 */
public interface AcessoGaragemRepository extends BaseRepositorySpecification<AcessoGaragem, Integer>, IntervaloValidator<String>, DeleteSomentePeloCriador<AcessoGaragem, Integer> {
	/**
	 * Buscar registros que ainda não possuem a data de saída.
	 * 
	 * @return
	 */
	List<AcessoGaragem> findBySaidaIsNull();

	/**
	 * Contar quantos registros estão associados com a mesma visita, não pode
	 * haver mais de acesso associado a uma mesma visita.
	 * 
	 * @param idVisita
	 * @param id
	 * @return
	 */
	@Query("select count(*) from AcessoGaragem as ag where ag.visita.id = :idVisita and ag.id <> :id")
	Long countByIdVisita(@Param("idVisita") Integer idVisita, @Param("id") Integer id);

	/**
	 * Buscar um acesso garagem para ser eliminado que tenha o mesmo id e
	 * usuário que informada e que tenha sido cadastradado na mesma data.
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@Query("select ag from AcessoGaragem as ag where cast(ag.dataHoraCadastro as date) = current_date and ag.id = :id and upper(ag.usuario) = upper(:usuario)")
	@Override
	AcessoGaragem findByIdAndUsuario(@Param("id") Integer id, @Param("usuario") Usuario usuario);

	/**
	 * Contar acesso do motorista sem baixa.
	 * 
	 * @param idMotorista
	 * @param id
	 * @return
	 */
	@Query("select count(ag) from AcessoGaragem as ag where ag.motorista.id = :idMotorista and ag.saida is null and ag.id <> :id")
	Long countAcessoMotoristaSemBaixa(@Param("idMotorista") Integer idMotorista, @Param("id") Integer id);

	/**
	 * Selecionar ids conflitantes.
	 * 
	 * @param entrada
	 * @param saida
	 * @param idVisita
	 * @param id
	 * @return
	 */
	@Query("select ag.id from AcessoGaragem as ag where upper(ag.veiculo.id) = upper(:placa) and ag.id <> :id and :saida >= ag.entrada and ag.saida >= :entrada")
	@Override
	List<Integer> findIdsConflitos(@Param("entrada") Date entrada, @Param("saida") Date saida, @Param("placa") String placa, @Param("id") Integer id);

	/**
	 * Buscar o ultimo horario de entrada e saída de cada motorista.
	 * 
	 * @return
	 */
	@Query
	List<PessoaDisponibilidade> findUltimaDisponibilidade();
}
