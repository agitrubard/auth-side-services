package com.agitrubard.authside.common.adapter.in.web;

import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.application.exception.AuthSideAlreadyException;
import com.agitrubard.authside.common.application.exception.AuthSideException;
import com.agitrubard.authside.common.application.exception.AuthSideNotFoundException;
import com.agitrubard.authside.common.application.exception.AuthSideProcessException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;

/**
 * The {@code AuthSideExceptionHandler} class is a controller advice that handles exceptions and translates them into appropriate HTTP responses.
 * It provides exception handling for various types of exceptions that can occur within the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Slf4j
@RestControllerAdvice
class AuthSideExceptionHandler {

    /**
     * Handle generic exceptions and translate them into an internal server error response (HTTP status 500).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing an internal server error.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    AuthSideErrorResponse handleProcessError(final Exception exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.PROCESS_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code AuthSideProcessException} and translate it into an internal server error response (HTTP status 500).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing an internal server error with a custom error message.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AuthSideProcessException.class)
    AuthSideErrorResponse handleProcessError(final AuthSideProcessException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.PROCESS_ERROR.getName())
                .message(exception.getMessage())
                .build();
    }

    /**
     * Handle SQL exceptions and translate them into an internal server error response (HTTP status 500).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a database-related error.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    AuthSideErrorResponse handleSQLError(final SQLException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(AuthSideErrorResponse.Header.DATABASE_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code MethodArgumentTypeMismatchException} exceptions and translate them into a bad request response (HTTP status 400).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a validation error due to method argument type mismatch.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    AuthSideErrorResponse handleValidationErrors(final MethodArgumentTypeMismatchException exception) {

        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code MethodArgumentNotValidException} exceptions and translate them into a bad request response (HTTP status 400).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a validation error due to method argument not being valid.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    AuthSideErrorResponse handleValidationErrors(final MethodArgumentNotValidException exception) {

        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception.getBindingResult().getFieldErrors())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code ConstraintViolationException} exceptions and translate them into a bad request response (HTTP status 400).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a validation error due to constraint violation.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    AuthSideErrorResponse handlePathVariableErrors(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.subErrors(exception.getConstraintViolations())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .header(AuthSideErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    /**
     * Handles HttpRequestMethodNotSupportedException by returning a customized error response
     * with HTTP status code METHOD_NOT_ALLOWED (405).
     *
     * @param exception The exception indicating the unsupported HTTP request method.
     * @return An instance of AuthSideErrorResponse containing the customized error details.
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    AuthSideErrorResponse handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .header(AuthSideErrorResponse.Header.API_ERROR.getName())
                .build();
    }

    /**
     * Handles NoResourceFoundException by returning a customized error response
     * with HTTP status code NOT_FOUND (404).
     *
     * @param exception The exception indicating the resource was not found.
     * @return An instance of AuthSideErrorResponse containing the customized error details.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    AuthSideErrorResponse handleResourceNotFound(final NoResourceFoundException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(AuthSideErrorResponse.Header.API_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code AuthSideNotFoundException} exceptions and translate them into a not found response (HTTP status 404).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a resource not found error.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthSideNotFoundException.class)
    AuthSideErrorResponse handleNotExistError(final AuthSideNotFoundException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .header(AuthSideErrorResponse.Header.NOT_FOUND.getName())
                .message(exception.getMessage())
                .build();
    }

    /**
     * Handle {@code AuthSideAlreadyException} exceptions and translate them into a conflict response (HTTP status 409).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a conflict due to a resource already existing.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AuthSideAlreadyException.class)
    AuthSideErrorResponse handleAlreadyExistError(final AuthSideAlreadyException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .header(AuthSideErrorResponse.Header.ALREADY_EXIST.getName())
                .message(exception.getMessage())
                .build();
    }

    /**
     * Handle {@code AccessDeniedException} exceptions and translate them into a forbidden response (HTTP status 403).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing a forbidden access error.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    AuthSideErrorResponse handleAccessDeniedError(final AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

    /**
     * Handle {@code AuthSideException} exceptions and translate them into an unauthorized response (HTTP status 401).
     *
     * @param exception The exception to be handled.
     * @return An {@code AuthSideErrorResponse} representing an unauthorized access error.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthSideException.class)
    AuthSideErrorResponse handleAuthError(final AuthSideException exception) {
        log.error(exception.getMessage(), exception);

        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

}
