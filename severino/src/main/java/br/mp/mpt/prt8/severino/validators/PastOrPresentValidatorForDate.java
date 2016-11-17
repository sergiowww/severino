package br.mp.mpt.prt8.severino.validators;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.spi.time.TimeProvider;

/**
 * Validação de datas para valores igual a agora ou passado.
 * 
 * @author sergio.eoliveira
 *
 */
public class PastOrPresentValidatorForDate implements ConstraintValidator<PastOrPresent, Date> {
	@Override
	public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
		// null values are valid
		if (date == null) {
			return true;
		}

		TimeProvider timeProvider = constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class).getTimeProvider();
		long now = timeProvider.getCurrentTime();

		return date.getTime() <= now;
	}

	@Override
	public void initialize(PastOrPresent constraintAnnotation) {

	}
}
