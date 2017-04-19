package br.mp.mpt.prt8.severino.dao.specs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Setor;
import br.mp.mpt.prt8.severino.utils.EntidadeUtil;

/**
 * Buscar um setor com os mesmos dados e no mesmo local.
 * 
 * @author sergio.eoliveira
 *
 */
public class SetorEqualsSpec implements Specification<Setor> {

	private final Local local;
	private final Setor setor;

	/**
	 * Construtor.
	 * 
	 * @param local
	 * @param setor
	 */
	public SetorEqualsSpec(Local local, Setor setor) {
		this.local = local;
		this.setor = setor;
	}

	@Override
	public Predicate toPredicate(Root<Setor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Integer id = EntidadeUtil.getIdNaoNulo(setor);
		String sala = setor.getSala();
		List<Predicate> criterios = new ArrayList<>();

		Path<String> pathSala = root.get("sala");
		if (StringUtils.isEmpty(sala)) {
			criterios.add(cb.isNull(pathSala));
		} else {
			criterios.add(cb.equal(cb.lower(pathSala), sala.toLowerCase()));
		}
		criterios.add(cb.equal(cb.lower(root.get("nome")), setor.getNome().toLowerCase()));
		criterios.add(cb.equal(root.get("andar"), setor.getAndar()));
		criterios.add(cb.equal(root.get("local"), local));
		criterios.add(cb.notEqual(root.get("id"), id));
		return cb.and(criterios.toArray(new Predicate[criterios.size()]));
	}

}
