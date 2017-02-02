package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.mp.mpt.prt8.severino.entity.Cargo;
import br.mp.mpt.prt8.severino.entity.Veiculo;

/**
 * DAO para acesso aos dados do ve�culo.
 * 
 * @author sergio.eoliveira
 *
 */
public interface VeiculoRepository extends BaseRepositorySpecification<Veiculo, String> {

	/**
	 * Buscar viaturas do mp.
	 * 
	 * @return
	 */
	List<Veiculo> findByViaturaMpTrue();

	/**
	 * Selecionar o valor do campo viaturamp do ve�culo pela placa
	 * 
	 * @param placa
	 * @return
	 */
	@Query("select v.viaturaMp from Veiculo as v where v.id = :id")
	Boolean getViaturaMpById(@Param("id") String placa);

	/**
	 * Listar todos os ve�culos de servidores e membros.
	 * 
	 * @param cargos
	 * @return
	 */
	@Query("select v from Veiculo as v inner join v.motorista as m where m.cargo in (:cargos)")
	List<Veiculo> findByCargoIn(@Param("cargos") List<Cargo> cargos);

	/**
	 * Buscar ve�culo pela placa ignorando a capitaliza��o.
	 * 
	 * @param trim
	 * @return
	 */
	Veiculo findByIdIgnoreCase(String id);

	/**
	 * Contar veiculos v�lidos como ve�culos de visitantes.
	 * 
	 * @param id
	 * @return
	 */
	Long countByMotoristaIsNullAndViaturaMpFalseAndId(String id);

	/**
	 * Buscar ve�culos pelos par�metros informados.
	 * 
	 * @param pageable
	 * @param searchValue
	 * @return
	 */
	@Query("select v from Veiculo as v left join v.motorista as m where lower(v.cor) like :searchValue or lower(v.id) like :searchValue or lower(v.marca) like :searchValue or lower(v.modelo) like :searchValue or lower(m.nome) like :searchValue")
	Page<Veiculo> findByTerm(Pageable pageable, @Param("searchValue") String searchValue);
}
