package com.agitrubard.authside.common.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to validate if the annotated field contains valid UUID strings.
 *
 * <p>Usage:</p>
 * <pre>{@code
 * @UUIDs
 * private String uuid;
 * }</pre>
 *
 * <p>The validator used for validation is {@link UUIDsValidator}.</p>
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UUIDsValidator.class)
public @interface UUIDs {

    /**
     * Error message to be used when validation fails.
     *
     * @return the error message
     */
    String message() default "invalid UUID format";

    /**
     * Groups targeted for validation.
     *
     * @return the validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload type to associate with the constraint.
     *
     * @return the payload type
     */
    Class<? extends Payload>[] payload() default {};

}
