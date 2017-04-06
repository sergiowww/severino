package br.mp.mpt.prt8.severino.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.Setor;

/**
 * Filtrar setores por termo de busca e organização.
 * 
 * @author sergio.eoliveira
 *
 */
public class SetorSpec extends AbstractSpec<Setor> {

	@Override
	public Predicate toPredicate(Root<Setor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate organizacaoEqual = organizacaoEqual(cb, root);
		if (StringUtils.isEmpty(getSearchValue())) {
			return organizacaoEqual;
		}
		Predicate nomeLike = likeCriteria(root, cb, "nome");
		Predicate salaLike = likeCriteria(root, cb, "sala");
		return cb.and(organizacaoEqual, cb.or(nomeLike, salaLike));

	}

}