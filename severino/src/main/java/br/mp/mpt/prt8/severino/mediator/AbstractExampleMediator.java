package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.repository.DataTablesUtils;
import org.springframework.util.StringUtils;

/**
 * Classe base para um mediator que pesquisa por example.
 * 
 * @author sergio.eoliveira
 *
 */
public abstract class AbstractExampleMediator<T, ID extends Serializable> extends AbstractMediator<T, ID> {

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
}
