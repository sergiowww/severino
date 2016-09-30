package br.mp.mpt.prt8.severino.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

/**
 * {@link Specification} that gives access to the {@link Predicate} instance
 * representing the values contained in the {@link Example}.
 *
 * @author Christoph Strobl
 * @since 1.10
 * @param <T>
 */
public class ExampleSpecificationDisjunction<T> implements Specification<T> {

	private final Example<T> example;

	/**
	 * Creates new {@link ExampleSpecificationDisjunction}.
	 *
	 * @param example
	 */
	public ExampleSpecificationDisjunction(Example<T> example) {

		Assert.notNull(example, "Example must not be null!");
		this.example = example;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.
	 * persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery,
	 * javax.persistence.criteria.CriteriaBuilder)
	 */
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate restrictions = QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
		List<Expression<Boolean>> expressions = restrictions.getExpressions();
		if (expressions.isEmpty()) {
			return restrictions;
		}
		return cb.or(expressions.toArray(new Predicate[expressions.size()]));
	}
}