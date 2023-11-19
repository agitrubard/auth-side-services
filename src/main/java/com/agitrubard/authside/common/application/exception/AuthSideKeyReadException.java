package com.agitrubard.authside.common.application.exception;

import java.io.Serial;

/**
 * This exception is thrown when an error occurs while attempting to read a key during a processing operation.
 * It is a specific type of processing exception.
 * <p>
 * This exception extends {@link AuthSideProcessException}, which indicates a processing-related issue, often an internal server error.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public final class AuthSideKeyReadException extends AuthSideProcessException {

    @Serial
    private static final long serialVersionUID = 4609433003373867767L;

    /**
     * Constructs a new instance of AuthSideKeyReadException with a message and an underlying exception.
     *
     * @param exception The underlying exception that caused this key read failure.
     */
    public AuthSideKeyReadException(Exception exception) {
        super("KEY COULD NOT BE READ!", exception);
    }

}
