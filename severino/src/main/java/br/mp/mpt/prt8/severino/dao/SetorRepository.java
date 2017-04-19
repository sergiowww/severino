package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Setor;

/**
 * DAO para setor.
 * 
 * @author sergio.eoliveira
 *
 */
public interface SetorRepository extends BaseRepositorySpecification<Setor, Integer> {

	/**
	 * Buscar todos os registros filtrando pelo local do usuário e ordenando
	 * pelo andar.
	 * 
	 * @param local
	 * @return
	 */
	List<Setor> findByLocalOrderByAndar(Local local);

}
