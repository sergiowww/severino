package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.transaction.annotation.Transactional;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.DeleteSomentePeloCriador;
import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Classe base para um mediator.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class AbstractMediator<T, ID extends Serializable> {

	@Autowired
	protected UsuarioHolder usuarioHolder;

	/**
	 * Retornar o bean do repositório.
	 * 
	 * @return
	 */
	protected abstract BaseRepositorySpecification<T, ID> repositoryBean();

	/**
	 * Buscar um registro pelo identificador.
	 * 
	 * @param id
	 * @return
	 */
	public T findOne(Optional<ID> id) {
		if (id.isPresent()) {
			return repositoryBean().findOne(id.get());
		}
		return null;
	}

	/**
	 * Gravar um registro.
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	public T save(T entity) {
		return repositoryBean().save(entity);
	}

	/**
	 * Procurar registros por página.
	 * 
	 * @param dataTablesInput
	 * @return
	 */
	public abstract Page<T> find(DataTablesInput dataTablesInput);

	/**
	 * Remover registro.
	 * 
	 * @param id
	 */
	@Transactional
	public void apagar(ID id) {
		BaseRepositorySpecification<T, ID> repository = repositoryBean();
		if (repository instanceof DeleteSomentePeloCriador) {
			deleteSomenteCriador(id, repository);
		} else {
			repository.delete(id);
		}
	}

	@SuppressWarnings("unchecked")
	private void deleteSomenteCriador(ID id, BaseRepositorySpecification<T, ID> repository) {
		DeleteSomentePeloCriador<T, ID> deleteSomentePeloCriador = (DeleteSomentePeloCriador<T, ID>) repository;
		T entidade = deleteSomentePeloCriador.findByIdAndUsuario(id, usuarioHolder.getUsuario());
		if (entidade != null) {
			repository.delete(entidade);
		} else {
			throw new NegocioException("Este registro só pode ser removido no mesmo dia pelo usuário criador!");
		}
	}

	/**
	 * Listar todos os locais.
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return repositoryBean().findAll();
	}

}
