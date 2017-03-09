package br.mp.mpt.prt8.severino.mediator;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import br.mp.mpt.prt8.severino.utils.NegocioException;

/**
 * Serviço para validar entidades.
 * 
 * @author sergio.eoliveira
 *
 */
@Service
public class ValidatorServiceBean<T> {

	@Autowired
	private SmartValidator smartValidator;

	/**
	 * Validar entidade e lançar exceção se não estive ok.
	 * 
	 * @param entidade
	 * @param classes
	 */
	public void validate(T entidade, Class<?>... groups) {
		Set<ConstraintViolation<T>> constraints = getValidationConstraints(entidade, groups);
		if (!constraints.isEmpty()) {
			throw new NegocioException(constraintsToMessage(constraints));
		}
	}

	/**
	 * Converter a lista de falhas para strings.
	 * 
	 * @param constraints
	 * @return
	 */
	public String constraintsToMessage(Set<ConstraintViolation<T>> constraints) {
		return constraints.stream().map(c -> c.getMessage()).collect(Collectors.joining(","));
	}

	/**
	 * Acionar a validação da entidade e retornar a lista de falhas.
	 * 
	 * @param entidade
	 * @param smartValidator
	 * @param groups
	 * @return
	 */
	public Set<ConstraintViolation<T>> getValidationConstraints(T entidade, Class<?>... groups) {
		return ((LocalValidatorFactoryBean) smartValidator).getValidator().validate(entidade, groups);
	}
}
