package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideInvalidTokenEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideInvalidTokenRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenSavePort;
import com.agitrubard.authside.auth.domain.token.AuthSideInvalidToken;
import com.agitrubard.authside.auth.mapper.AuthSideInvalidTokenToInvalidTokenEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * The {@code AuthSideInvalidTokenAdapter} class is an adapter component responsible for bridging the gap between the application's
 * business logic and the data access layer. It implements the interfaces {@link AuthSideInvalidTokenReadPort} and
 * {@link AuthSideInvalidTokenSavePort}, providing methods to read and save invalidated authentication tokens.
 * <p>
 * This adapter relies on the {@link AuthSideInvalidTokenRepository} for data storage and uses a mapper to convert between
 * business objects ({@link AuthSideInvalidToken}) and entity objects ({@link AuthSideInvalidTokenEntity}).
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideInvalidTokenAdapter implements AuthSideInvalidTokenReadPort, AuthSideInvalidTokenSavePort {

    private final AuthSideInvalidTokenRepository invalidTokenRepository;

    private final AuthSideInvalidTokenToInvalidTokenEntityMapper invalidTokenToInvalidTokenEntityMapper = AuthSideInvalidTokenToInvalidTokenEntityMapper.initialize();

    /**
     * Saves a set of invalidated authentication tokens in the data store.
     *
     * @param invalidTokens The set of invalidated authentication tokens to save.
     */
    @Override
    public void saveAll(Set<AuthSideInvalidToken> invalidTokens) {
        final Set<AuthSideInvalidTokenEntity> invalidTokenEntities = invalidTokenToInvalidTokenEntityMapper
                .map(invalidTokens);
        invalidTokenRepository.saveAll(invalidTokenEntities);
    }

    /**
     * Checks if an invalidated token with the specified token ID exists in the data store.
     *
     * @param tokenId The unique identifier of the invalidated token.
     * @return {@code true} if a token with the specified ID exists; {@code false} otherwise.
     */
    @Override
    public boolean exists(final String tokenId) {
        return invalidTokenRepository.existsByTokenId(tokenId);
    }

}
