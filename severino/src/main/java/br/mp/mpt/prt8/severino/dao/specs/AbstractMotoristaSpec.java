package br.mp.mpt.prt8.severino.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.Motorista;

/**
 * Filtrar pelo local e o nome do motorista.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 */
abstract class AbstractMotoristaSpec<T> extends AbstractSpec<T> {

	@Override
	public final Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate localEqual = localEqual(cb, root);
		String searchValue = getSearchValue();
		if (StringUtils.isEmpty(searchValue)) {
			return localEqual;
		}
		Join<T, Motorista> joinMotora = root.join("motorista");
		Predicate nomeLike = likeCriteria(joinMotora, cb, "nome");
		return cb.and(localEqual, nomeLike);
	}

}
