package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@code AuthSideUsernameNotValidException} class is an exception that is thrown when a username in the authentication
 * side of the application is considered not valid. This exception extends {@link AuthSideException} and is used to indicate
 * failures related to invalid usernames.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideUsernameNotValidException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = 8712878086437207740L;

    /**
     * Constructs a new {@code AuthSideUsernameNotValidException} with the provided username.
     *
     * @param username The username that is considered not valid.
     */
    public AuthSideUsernameNotValidException(final String username) {
        super("USERNAME IS NOT VALID! username: " + username);
    }

}
