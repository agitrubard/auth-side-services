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

@Getter
@Builder
public class AuthSideErrorResponse {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();
    private HttpStatus httpStatus;
    @Builder.Default
    private final Boolean isSuccess = false;
    private String header;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AuthSideSubError> subErrors;

    @Getter
    @Builder
    public static class AuthSideSubError {
        private String message;
        private String field;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Object value;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;
    }

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
