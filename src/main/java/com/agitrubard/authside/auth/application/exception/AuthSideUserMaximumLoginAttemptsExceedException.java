package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@code AuthSideUserMaximumLoginAttemptsExceedException} class is an exception that is thrown when a user in the
 * authentication side of the application exceeds the maximum allowed login attempts. This exception extends
 * {@link AuthSideException} and is used to indicate that the user's login attempts have exceeded the allowable limit.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideUserMaximumLoginAttemptsExceedException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = -8525633683813130816L;

    /**
     * Constructs a new {@code AuthSideUserMaximumLoginAttemptsExceedException} with the provided user ID.
     *
     * @param userId The user ID of the user who has exceeded the maximum allowed login attempts.
     */
    public AuthSideUserMaximumLoginAttemptsExceedException(final String userId) {
        super("USER EXCEED MAXIMUM LOGIN ATTEMPTS! userId:" + userId);
    }

}
