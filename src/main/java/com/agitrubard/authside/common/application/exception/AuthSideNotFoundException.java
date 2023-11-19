package com.agitrubard.authside.common.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * This abstract class represents an exception scenario denoting a resource or entity not found.
 * It is typically thrown when a requested resource or entity cannot be located.
 * <p>
 * This exception is annotated with {@code @ResponseStatus(HttpStatus.NOT_FOUND)} to indicate an HTTP 404 Not Found status code.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class AuthSideNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3046469863777315718L;

    /**
     * Constructs a new instance of AuthSideNotFoundException with the specified message.
     *
     * @param message A message providing more information about the exception.
     */
    protected AuthSideNotFoundException(final String message) {
        super(message);
    }

}
