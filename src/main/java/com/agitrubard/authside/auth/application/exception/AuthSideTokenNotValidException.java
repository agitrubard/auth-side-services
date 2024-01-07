package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@code AuthSideTokenNotValidException} class is an exception that is thrown when a token in the authentication side
 * of the application is not considered valid. This exception extends {@link AuthSideException} and is used to indicate
 * token validation failures.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideTokenNotValidException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = -5404410121820902017L;

    /**
     * Constructs a new {@code AuthSideTokenNotValidException} with the provided token and a cause.
     *
     * @param jwt   The token that is not considered valid.
     * @param cause The cause of the exception, typically another exception that led to this token validation failure.
     */
    public AuthSideTokenNotValidException(String jwt, Throwable cause) {
        super(STR."TOKEN IS NOT VALID! token: \{jwt}", cause);
    }

}
