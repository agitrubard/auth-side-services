package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@link AuthSidePasswordNotValidException} class is an exception that is thrown when an attempt to validate a password
 * fails in the authentication side of the application. This exception extends {@link AuthSideException}, indicating that
 * the provided password is not considered valid.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSidePasswordNotValidException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = 1899764163725033286L;

    /**
     * Constructs a new {@link AuthSidePasswordNotValidException} with a default error message.
     */
    public AuthSidePasswordNotValidException() {
        super("PASSWORD IS NOT VALID!");
    }

}
