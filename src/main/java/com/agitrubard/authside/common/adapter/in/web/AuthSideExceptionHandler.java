package com.agitrubard.authside.common.adapter.in.web;

import com.agitrubard.authside.common.application.exception.AuthSideAlreadyException;
import com.agitrubard.authside.common.application.exception.AuthSideException;
import com.agitrubard.authside.common.application.exception.AuthSideNotFoundException;
import com.agitrubard.authside.common.application.exception.AuthSideProcessException;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
class AuthSideExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected AuthSideErrorResponse handleProcessError(final Exception exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.PROCESS_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AuthSideProcessException.class)
    protected AuthSideErrorResponse handleProcessError(final AuthSideProcessException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.PROCESS_ERROR.getName())
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    protected AuthSideErrorResponse handleSQLError(final SQLException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.DATABASE_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected AuthSideErrorResponse handleValidationErrors(final MethodArgumentTypeMismatchException exception) {

        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected AuthSideErrorResponse handleValidationErrors(final MethodArgumentNotValidException exception) {

        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception.getBindingResult().getFieldErrors())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected AuthSideErrorResponse handlePathVariableErrors(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception.getConstraintViolations())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthSideNotFoundException.class)
    protected AuthSideErrorResponse handleNotExistError(final AuthSideNotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(AuthSideErrorResponse.Header.NOT_FOUND.getName())
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AuthSideAlreadyException.class)
    protected AuthSideErrorResponse handleAlreadyExistError(final AuthSideAlreadyException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .header(AuthSideErrorResponse.Header.ALREADY_EXIST.getName())
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    protected AuthSideErrorResponse handleAccessDeniedError(final AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthSideException.class)
    protected AuthSideErrorResponse handleAuthError(final AuthSideException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

}
