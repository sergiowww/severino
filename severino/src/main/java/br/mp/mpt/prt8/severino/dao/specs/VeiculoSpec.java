package br.mp.mpt.prt8.severino.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.Motorista;
import br.mp.mpt.prt8.severino.entity.Veiculo;

/**
 * Filtro de veículos.
 * 
 * @author sergio.eoliveira
 *
 */
public class VeiculoSpec extends AbstractSpec<Veiculo> {

	@Override
	public Predicate toPredicate(Root<Veiculo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate organizacaoEqual = organizacaoEqual(cb, root);
		String searchValue = getSearchValue();
		if (StringUtils.isEmpty(searchValue)) {
			return organizacaoEqual;
		}

		Predicate likeCor = likeCriteria(root, cb, "cor");
		Predicate likeId = likeCriteria(root, cb, "id");
		Predicate likeMarca = likeCriteria(root, cb, "marca");
		Predicate likeModelo = likeCriteria(root, cb, "modelo");
		Join<Veiculo, Motorista> joinMotora = root.join("motorista", JoinType.LEFT);
		Predicate likeNomeMotora = likeCriteria(joinMotora, cb, "nome");
		return cb.and(organizacaoEqual, cb.or(likeCor, likeId, likeMarca, likeModelo, likeNomeMotora));
	}

}
