package com.agitrubard.authside.common.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * This abstract class represents an exception scenario related to a processing error, often indicating an internal server error.
 * It is used to indicate issues that occur during the processing of a request.
 * <p>
 * This exception is annotated with {@code @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)} to indicate an HTTP 500 Internal Server Error status code.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class AuthSideProcessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -9145701836708877113L;

    /**
     * Constructs a new instance of AuthSideProcessException with the specified message.
     *
     * @param message A message providing more information about the exception.
     */
    protected AuthSideProcessException(final String message) {
        super(message);
    }

    /**
     * Constructs a new instance of AuthSideProcessException with the specified message and a cause.
     *
     * @param message A message providing more information about the exception.
     * @param cause   The cause of this exception, typically another exception.
     */
    protected AuthSideProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
