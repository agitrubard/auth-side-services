package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.exception.AuthSideTokenAlreadyInvalidatedException;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideInvalidTokenUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenSavePort;
import com.agitrubard.authside.auth.domain.token.AuthSideInvalidToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for handling invalid tokens, including their invalidation and validation.
 * <p>
 * This service interacts with the database through the read and save ports to manage invalid tokens.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
class AuthSideInvalidTokenService implements AuthSideInvalidTokenUseCase {

    private final AuthSideInvalidTokenReadPort invalidTokenReadPort;
    private final AuthSideInvalidTokenSavePort invalidTokenSavePort;

    /**
     * Invalidates a set of token IDs, marking them as invalid in the system.
     *
     * @param tokenIds A set of token IDs to invalidate.
     */
    @Override
    public void invalidateTokens(final Set<String> tokenIds) {
        final Set<AuthSideInvalidToken> invalidTokens = tokenIds.stream()
                .map(tokenId -> AuthSideInvalidToken.builder()
                        .tokenId(tokenId)
                        .build()
                )
                .collect(Collectors.toSet());

        invalidTokenSavePort.saveAll(invalidTokens);
    }


    /**
     * Validates the invalidity of a token by checking if it exists in the list of invalidated tokens.
     * If the token is found to be invalidated, an exception is thrown.
     *
     * @param tokenId The token ID to validate.
     * @throws AuthSideTokenAlreadyInvalidatedException If the token is already invalidated.
     */
    @Override
    public void validateInvalidityOfToken(final String tokenId) {
        final boolean isTokenInvalid = invalidTokenReadPort.exists(tokenId);
        if (isTokenInvalid) {
            throw new AuthSideTokenAlreadyInvalidatedException(tokenId);
        }
    }

}
