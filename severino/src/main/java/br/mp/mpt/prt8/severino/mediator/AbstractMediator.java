package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.dao.BaseRepositorySpecification;
import br.mp.mpt.prt8.severino.dao.examplequery.ExampleSpecificationDisjunction;
import br.mp.mpt.prt8.severino.utils.DataTableUtils;

/**
 * Classe base para um mediator.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class AbstractMediator<T, ID extends Serializable> {

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
		ExampleMatcher exampleMather = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);
		return repositoryBean().findAll(
				new ExampleSpecificationDisjunction<>(Example.of(getExampleForSearching(searchValue), exampleMather)),
				pageable);
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
		Pageable pageable = DataTableUtils.getPageable(dataTablesInput);
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
		repositoryBean().delete(id);
	}
}
