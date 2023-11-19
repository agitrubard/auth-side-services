package com.agitrubard.authside.common.application.exception;

import java.io.Serial;

/**
 * This exception is thrown when an unexpected argument is encountered during a processing operation.
 * It is a specific type of processing exception.
 * <p>
 * This exception extends {@link AuthSideProcessException}, which indicates a processing-related issue, often an internal server error.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public final class AuthSideUnexpectedArgumentException extends AuthSideProcessException {

    @Serial
    private static final long serialVersionUID = 6175478681206196630L;

    public AuthSideUnexpectedArgumentException() {
        super("UNEXPECTED ARGUMENT!");
    }

    /**
     * Constructs a new instance of AuthSideUnexpectedArgumentException with an unexpected object as an argument.
     *
     * @param object The unexpected object that caused this exception.
     */
    public AuthSideUnexpectedArgumentException(final Object object) {
        super("UNEXPECTED ARGUMENT! object: " + object.toString() + ", objectType: " + object.getClass().getName());
    }

}
