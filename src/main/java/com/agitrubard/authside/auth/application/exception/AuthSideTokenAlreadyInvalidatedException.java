package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideException;

import java.io.Serial;

/**
 * The {@link AuthSideTokenAlreadyInvalidatedException} class is an exception that is thrown when an attempt to invalidate a
 * token in the authentication side of the application fails because the token has already been invalidated. This exception
 * extends {@link AuthSideException}, indicating that the requested token is already in an invalidated state.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideTokenAlreadyInvalidatedException extends AuthSideException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = 926590327251528609L;

    /**
     * Constructs a new {@link AuthSideTokenAlreadyInvalidatedException} with the provided token ID.
     *
     * @param tokenId The token ID for which the invalidation failed due to the token being already invalidated.
     */
    public AuthSideTokenAlreadyInvalidatedException(final String tokenId) {
        super(STR."TOKEN IS ALREADY INVALIDATED! tokenId: \{tokenId}");
    }

}
