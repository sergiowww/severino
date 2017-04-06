package br.mp.mpt.prt8.severino.mediator;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.repository.DataTablesUtils;

import br.mp.mpt.prt8.severino.dao.specs.AbstractSpec;
import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;

/**
 * Mediator base para classes com buscas por especificação.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractSpecMediator<T, ID extends Serializable> extends AbstractMediator<T, ID> {
	/**
	 * Busca pela classe de especificação.
	 */
	@Override
	public Page<T> find(DataTablesInput dataTablesInput) {
		String searchValue = dataTablesInput.getSearch().getValue();
		Pageable pageable = DataTablesUtils.getPageable(dataTablesInput);
		Local local = usuarioHolder.getLocal();
		Organizacao organizacao = local.getOrganizacao();
		AbstractSpec<T> spec = BeanUtils.instantiate(specClass());
		spec.setOrganizacao(organizacao);
		spec.setSearchValue(searchValue);
		spec.setLocal(local);
		return repositoryBean().findAll(spec, pageable);
	}

	/**
	 * Deve retornar a classe de specificação.
	 * 
	 * @return
	 */
	public abstract Class<? extends AbstractSpec<T>> specClass();

}
