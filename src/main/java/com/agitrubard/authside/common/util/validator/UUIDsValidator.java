package com.agitrubard.authside.common.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Validator implementation for the {@link UUIDs} annotation.
 * Validates if the annotated set of strings contains valid UUIDs.
 */
class UUIDsValidator implements ConstraintValidator<UUIDs, Set<String>> {

    private static final String PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    /**
     * Validates if the annotated set of strings contains valid UUIDs.
     *
     * @param uuids                      the set of strings to validate
     * @param constraintValidatorContext the context in which the constraint is evaluated
     * @return {@code true} if all strings are valid UUIDs, {@code false} otherwise
     */
    @Override
    public boolean isValid(Set<String> uuids, ConstraintValidatorContext constraintValidatorContext) {

        if (CollectionUtils.isEmpty(uuids)) {
            return true;
        }

        return uuids.stream().allMatch(uuid -> Pattern.matches(PATTERN, uuid));
    }

}
