package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Usuario;
import br.mp.mpt.prt8.severino.entity.Viagem;
import br.mp.mpt.prt8.severino.valueobject.PessoaDisponibilidade;

/**
 * DAO para viagem.
 * 
 * @author sergio.eoliveira
 *
 */
public interface ViagemRepository extends BaseRepositorySpecification<Viagem, Integer>, DeleteSomentePeloCriador<Viagem, Integer> {

	/**
	 * Buscar viagem pelo identificador e usuário logado.
	 * 
	 * @param id
	 * @param usuario
	 * @return
	 */
	@Query("select v from Viagem as v where cast(v.dataHoraCadastro as date) = current_date and v.id = :id and upper(v.usuario) = upper(:usuario)")
	@Override
	Viagem findByIdAndUsuario(@Param("id") Integer id, @Param("usuario") Usuario usuario);

	/**
	 * Buscar viagens que ainda não retornaram.
	 * 
	 * @return
	 */
	List<Viagem> findByControleRetornoIsNull();

	/**
	 * Buscar viagem onde o retorno estiver nulo e o motorista igual ao
	 * informado.
	 * 
	 * @param motorista
	 * @return
	 */
	Viagem findByControleRetornoIsNullAndMotorista(Motorista motorista);

	/**
	 * Buscar identificador do controle de saída pelo id da viagem.
	 * 
	 * @param idViagem
	 * @return
	 */
	@Query("select v.controleSaida.id from Viagem as v where v.id = :idViagem")
	Integer findIdControleSaidaByViagem(@Param("idViagem") Integer idViagem);

	/**
	 * Buscar identificador do controle de retorno pelo id da viagem.
	 * 
	 * @param idViagem
	 * @return
	 */
	@Query("select v.controleRetorno.id from Viagem as v where v.id = :idViagem")
	Integer findIdControleRetornoByViagem(@Param("idViagem") Integer idViagem);

	/**
	 * Buscar a ultima viagem com cada passageiro.
	 * 
	 * @return
	 */
	@Query
	List<PessoaDisponibilidade> findPassageirosUltimaViagem();
}
