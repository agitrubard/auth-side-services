package com.agitrubard.authside.auth.application.port.in.usecase;

import java.util.Set;

/**
 * The {@code AuthSideInvalidTokenUseCase} interface defines the use cases for managing and validating invalidated tokens
 * in the authentication side of the application. It provides methods for invalidating tokens and checking the invalidity
 * status of specific tokens.
 * <p>
 * Implementations of this interface are responsible for tracking and managing invalidated tokens, which are typically
 * tokens that have been revoked or are no longer valid for authentication or access.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideInvalidTokenUseCase {

    /**
     * Invalidates a set of tokens identified by their unique token IDs.
     *
     * @param tokenIds A set of token IDs to be invalidated.
     */
    void invalidateTokens(final Set<String> tokenIds);

    /**
     * Validates the invalidity status of a specific token identified by its unique token ID.
     *
     * @param tokenId The token ID to check for invalidity.
     */
    void validateInvalidityOfToken(final String tokenId);

}
