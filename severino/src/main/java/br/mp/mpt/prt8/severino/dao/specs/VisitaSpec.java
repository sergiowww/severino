package br.mp.mpt.prt8.severino.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.Visita;
import br.mp.mpt.prt8.severino.entity.Visitante;

/**
 * Filtrar visitas por termo e organização.
 * 
 * @author sergio.eoliveira
 *
 */
public class VisitaSpec extends AbstractSpec<Visita> {

	@Override
	public Predicate toPredicate(Root<Visita> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate organizacaoEqual = organizacaoEqual(cb, root);
		if (StringUtils.isEmpty(getSearchValue())) {
			return organizacaoEqual;
		}
		Predicate likeNomeProcurado = likeCriteria(root, cb, "nomeProcurado");
		Join<Visita, Visitante> joinVisitante = root.join("visitante", JoinType.LEFT);
		Predicate likeNomeVisitante = likeCriteria(joinVisitante, cb, "nome");
		return cb.and(organizacaoEqual, cb.or(likeNomeProcurado, likeNomeVisitante));
	}

}
