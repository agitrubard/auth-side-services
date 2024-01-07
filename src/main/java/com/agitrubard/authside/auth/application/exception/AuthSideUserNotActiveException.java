package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@code AuthSideUserNotActiveException} class is an exception that is thrown when a user in the authentication side of
 * the application is found to be inactive. This exception extends {@link AuthSideException} and is used to indicate
 * cases where a user is not in an active state within the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideUserNotActiveException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = -5218287176856317070L;

    /**
     * Constructs a new {@code AuthSideUserNotActiveException} with the provided user ID.
     *
     * @param userId The user ID of the inactive user.
     */
    public AuthSideUserNotActiveException(final String userId) {
        super(STR."USER IS NOT ACTIVE! userId:\{userId}");
    }

}
