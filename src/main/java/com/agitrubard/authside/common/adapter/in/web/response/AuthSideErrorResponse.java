package com.agitrubard.authside.common.adapter.in.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The {@code AuthSideErrorResponse} class represents a standard error response format used for handling exceptions and conveying error information.
 * It includes details such as the timestamp, HTTP status, error message, and optional sub-errors.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSideErrorResponse {

    /**
     * The timestamp indicating when the error response was created.
     */
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    /**
     * The HTTP status code corresponding to the error.
     */
    private HttpStatus httpStatus;

    /**
     * A boolean value indicating whether the request was successful (default is false for error responses).
     */
    @Builder.Default
    private final Boolean isSuccess = false;

    /**
     * A short header or error code describing the type of error.
     */
    private String header;

    /**
     * An optional error message providing more information about the error.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    /**
     * An optional list of sub-errors, each representing a specific issue related to the main error.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AuthSideSubError> subErrors;

    public static final AuthSideErrorResponse FORBIDDEN = AuthSideErrorResponse.builder()
            .httpStatus(HttpStatus.FORBIDDEN)
            .header(Header.AUTH_ERROR.getName())
            .isSuccess(false).build();

    /**
     * The {@code AuthSideSubError} class represents a sub-error within an error response.
     */
    @Getter
    @Builder
    public static class AuthSideSubError {

        /**
         * A message describing the sub-error.
         */
        private String message;

        /**
         * The name of the field associated with the sub-error.
         */
        private String field;

        /**
         * The value that caused the sub-error (if applicable).
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object value;

        /**
         * The type or data type related to the sub-error (if applicable).
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;
    }

    /**
     * An enumeration of predefined error headers used for categorizing errors.
     */
    @Getter
    @RequiredArgsConstructor
    public enum Header {
        API_ERROR("API ERROR"),
        ALREADY_EXIST("ALREADY EXIST"),
        NOT_FOUND("NOT FOUND"),
        VALIDATION_ERROR("VALIDATION ERROR"),
        DATABASE_ERROR("DATABASE ERROR"),
        PROCESS_ERROR("PROCESS ERROR"),
        AUTH_ERROR("AUTH ERROR");

        private final String name;
    }


    /**
     * Constructs an {@code AuthSideErrorResponseBuilder} with sub-errors generated from a list of {@code FieldError} objects.
     * This is typically used to handle validation errors.
     *
     * @param fieldErrors The list of {@code FieldError} objects representing validation errors.
     * @return An {@code AuthSideErrorResponseBuilder} with sub-errors.
     */
    public static AuthSideErrorResponse.AuthSideErrorResponseBuilder subErrors(final List<FieldError> fieldErrors) {

        if (CollectionUtils.isEmpty(fieldErrors)) {
            return AuthSideErrorResponse.builder();
        }

        final List<AuthSideSubError> subErrorErrors = new ArrayList<>();

        fieldErrors.forEach(fieldError -> {
            final AuthSideSubError.AuthSideSubErrorBuilder subErrorBuilder = AuthSideSubError.builder();

            List<String> codes = List.of(Objects.requireNonNull(fieldError.getCodes()));
            if (!codes.isEmpty()) {

                subErrorBuilder.field(StringUtils.substringAfterLast(codes.get(0), "."));

                if (!"AssertTrue".equals(codes.get(codes.size() - 1))) {
                    subErrorBuilder.type(StringUtils.substringAfterLast(codes.get(codes.size() - 2), "."));
                }
            }
            subErrorBuilder.value(fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null);
            subErrorBuilder.message(fieldError.getDefaultMessage());

            subErrorErrors.add(subErrorBuilder.build());
        });

        return AuthSideErrorResponse.builder().subErrors(subErrorErrors);
    }

    /**
     * Constructs an {@code AuthSideErrorResponseBuilder} with sub-errors generated from a set of {@code ConstraintViolation} objects.
     * This is used to handle validation errors that occur due to constraint violations.
     *
     * @param constraintViolations The set of {@code ConstraintViolation} objects representing validation errors.
     * @return An {@code AuthSideErrorResponseBuilder} with sub-errors.
     */
    public static AuthSideErrorResponse.AuthSideErrorResponseBuilder subErrors(final Set<ConstraintViolation<?>> constraintViolations) {

        if (CollectionUtils.isEmpty(constraintViolations)) {
            return AuthSideErrorResponse.builder();
        }

        final List<AuthSideSubError> subErrors = new ArrayList<>();

        constraintViolations.forEach(constraintViolation ->
                subErrors.add(
                        AuthSideSubError.builder()
                                .message(constraintViolation.getMessage())
                                .field(StringUtils.substringAfterLast(constraintViolation.getPropertyPath().toString(), "."))
                                .value(constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null)
                                .type(constraintViolation.getInvalidValue().getClass().getSimpleName()).build()
                )
        );

        return AuthSideErrorResponse.builder().subErrors(subErrors);
    }

    /**
     * Constructs an {@code AuthSideErrorResponseBuilder} with sub-errors generated from a {@code MethodArgumentTypeMismatchException}.
     * This is used to handle type mismatch errors in method arguments.
     *
     * @param exception The {@code MethodArgumentTypeMismatchException} representing the type mismatch error.
     * @return An {@code AuthSideErrorResponseBuilder} with sub-errors.
     */
    public static AuthSideErrorResponse.AuthSideErrorResponseBuilder subErrors(final MethodArgumentTypeMismatchException exception) {
        return AuthSideErrorResponse.builder()
                .subErrors(List.of(
                        AuthSideSubError.builder()
                                .message(exception.getMessage().split(";")[0])
                                .field(exception.getName())
                                .value(Objects.requireNonNull(exception.getValue()).toString())
                                .type(Objects.requireNonNull(exception.getRequiredType()).getSimpleName()).build()
                ));
    }

}
