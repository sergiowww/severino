package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.repository.DataTablesUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
	 * Realizar uma busca com os parâmetros informados.
	 * 
	 * @param searchValue
	 * @param pageable
	 * @return
	 */
	private Page<T> find(String searchValue, Pageable pageable) {
		ExampleMatcher exampleMather = getExampleMatcher();
		return repositoryBean().findAll(Example.of(getExampleForSearching(searchValue), exampleMather), pageable);
	}

	/**
	 * Example matcher.
	 * 
	 * @return
	 */
	protected ExampleMatcher getExampleMatcher() {
		return ExampleMatcher.matchingAny().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
	}

	/**
	 * Retorna a entidade de exemplo para buscar nos registros.
	 * 
	 * @param searchValue
	 * @return
	 */
	protected abstract T getExampleForSearching(String searchValue);

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
	 * Buscar registros.
	 * 
	 * @param dataTablesInput
	 * @return
	 */
	public Page<T> find(DataTablesInput dataTablesInput) {
		String searchValue = dataTablesInput.getSearch().getValue();
		Pageable pageable = DataTablesUtils.getPageable(dataTablesInput);
		if (StringUtils.isEmpty(searchValue)) {
			return repositoryBean().findAll(pageable);
		}
		return find(searchValue, pageable);
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
}
