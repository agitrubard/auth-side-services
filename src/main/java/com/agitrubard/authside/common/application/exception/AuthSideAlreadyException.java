package com.agitrubard.authside.common.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * This abstract class represents an exception scenario denoting a conflict, where an operation cannot be completed due to an "already exists" condition.
 * It is typically used to indicate that an entity or resource being created or modified already exists.
 * <p>
 * This exception is annotated with {@code @ResponseStatus(HttpStatus.CONFLICT)} to indicate an HTTP 409 Conflict status code.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public abstract class AuthSideAlreadyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3977780442691721992L;

    /**
     * Constructs a new instance of AuthSideAlreadyException with the specified message.
     *
     * @param message A message providing more information about the exception.
     */
    protected AuthSideAlreadyException(final String message) {
        super(message);
    }

}
