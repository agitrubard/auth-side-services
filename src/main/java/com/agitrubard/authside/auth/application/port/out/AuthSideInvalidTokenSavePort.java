package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.token.AuthSideInvalidToken;

import java.util.Set;

/**
 * The {@code AuthSideInvalidTokenSavePort} interface defines methods for saving invalid tokens in the authentication side
 * of the application. It provides the capability to store a set of tokens marked as invalid.
 * <p>
 * Implementations of this interface are responsible for persisting invalidated tokens in a data repository, allowing them to
 * be later checked for their existence and validity.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideInvalidTokenSavePort {

    /**
     * Saves a set of invalid tokens.
     *
     * @param invalidTokens A {@code Set} of {@link AuthSideInvalidToken} objects representing tokens to be marked as invalid
     *                      and stored in the repository.
     */
    void saveAll(Set<AuthSideInvalidToken> invalidTokens);

}
