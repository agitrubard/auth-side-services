package com.agitrubard.authside.auth.application.port.out;

/**
 * The {@link AuthSideInvalidTokenReadPort} interface defines methods for reading and checking the existence of invalid tokens
 * in the authentication side of the application. It is responsible for querying the existence of tokens marked as invalid.
 * <p>
 * Implementations of this interface provide the ability to determine whether a token identified by its token ID exists in the
 * repository of invalidated tokens.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideInvalidTokenReadPort {

    /**
     * Checks the existence of a token with the specified token ID.
     *
     * @param tokenId The unique identifier of the token to check.
     * @return {@code true} if a token with the given token ID exists and is marked as invalid, {@code false} otherwise.
     */
    boolean exists(String tokenId);

}
