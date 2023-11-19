package com.agitrubard.authside.common.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * This abstract class represents an exception scenario related to authentication, typically denoting an unauthorized operation.
 * It is used to indicate authentication-related issues, such as unauthorized access.
 * <p>
 * This exception is annotated with {@code @ResponseStatus(HttpStatus.UNAUTHORIZED)} to indicate an HTTP 401 Unauthorized status code.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public abstract class AuthSideException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4529296872877563275L;

    /**
     * Constructs a new instance of AuthSideException with the specified message.
     *
     * @param message A message providing more information about the exception.
     */
    protected AuthSideException(final String message) {
        super(message);
    }

    /**
     * Constructs a new instance of AuthSideException with the specified message and a cause.
     *
     * @param message A message providing more information about the exception.
     * @param cause   The cause of this exception, typically another exception.
     */
    protected AuthSideException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
