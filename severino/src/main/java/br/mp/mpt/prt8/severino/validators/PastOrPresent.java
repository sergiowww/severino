package br.mp.mpt.prt8.severino.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotated element must be a date in the past. Now is defined as the
 * current time according to the virtual machine.
 * <p/>
 * The calendar used if the compared type is of type {@code Calendar} is the
 * calendar based on the current timezone and the current locale.
 * <p/>
 * Supported types are:
 * <ul>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.util.Calendar}</li>
 * </ul>
 * <p/>
 * {@code null} elements are considered valid.
 *
 * @author Emmanuel Bernard
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PastOrPresentValidatorForDate.class })
public @interface PastOrPresent {

	String message() default "{javax.validation.constraints.Past.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}