package br.mp.mpt.prt8.severino.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.mp.mpt.prt8.severino.entity.Local;
import br.mp.mpt.prt8.severino.entity.Organizacao;

/**
 * Classe base para especificações de busca.
 * 
 * @author sergio.eoliveira
 *
 * @param <T>
 */
public abstract class AbstractSpec<T> implements Specification<T> {
	private static final char PERCENT = '%';

	private Organizacao organizacao;
	private Local local;
	private String searchValue;

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	protected String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	/**
	 * Criar um like predicate case-insensitive.
	 * 
	 * @param root
	 * @param cb
	 * @param property
	 * @return
	 */
	protected Predicate likeCriteria(From<T, ?> root, CriteriaBuilder cb, String property) {
		return cb.like(cb.lower(root.get(property)), PERCENT + getSearchValue().toLowerCase() + PERCENT);
	}

	/**
	 * Critério de busca por organização.
	 * 
	 * @param cb
	 * @param root
	 * @return
	 */
	protected Predicate organizacaoEqual(CriteriaBuilder cb, Root<T> root) {
		Join<T, Local> join = joinLocal(root);
		return cb.equal(join.get("organizacao"), organizacao);
	}

	/**
	 * Critério busca por local.
	 * 
	 * @param cb
	 * @param root
	 * @return
	 */
	protected Predicate localEqual(CriteriaBuilder cb, Root<T> root) {
		return cb.equal(root.get("local"), local);
	}

	/**
	 * Criar um join com local.
	 * 
	 * @param root
	 * @return
	 */
	private Join<T, Local> joinLocal(Root<T> root) {
		return root.join("local");
	}

}
