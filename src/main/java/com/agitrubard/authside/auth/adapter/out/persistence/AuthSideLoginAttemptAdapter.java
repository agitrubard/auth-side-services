package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideLoginAttemptRepository;
import com.agitrubard.authside.auth.application.exception.AuthSideLoginAttemptRecordNotFoundException;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptSavePort;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.mapper.AuthSideLoginAttemptEntityToLoginAttemptMapper;
import com.agitrubard.authside.auth.mapper.AuthSideLoginAttemptToLoginAttemptEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The {@code AuthSideLoginAttemptAdapter} class is an adapter component responsible for bridging the gap between the application's
 * business logic and the data access layer. It implements the interfaces {@link AuthSideLoginAttemptReadPort} and
 * {@link AuthSideLoginAttemptSavePort}, providing methods to read and save user login attempts.
 * <p>
 * This adapter relies on the {@link AuthSideLoginAttemptRepository} for data storage and uses mappers to convert between
 * business objects ({@link AuthSideLoginAttempt}) and entity objects ({@link AuthSideLoginAttemptEntity}).
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideLoginAttemptAdapter implements AuthSideLoginAttemptReadPort, AuthSideLoginAttemptSavePort {

    private final AuthSideLoginAttemptRepository loginAttemptRepository;

    private static final AuthSideLoginAttemptEntityToLoginAttemptMapper LOGIN_ATTEMPT_ENTITY_TO_LOGIN_ATTEMPT_MAPPER = AuthSideLoginAttemptEntityToLoginAttemptMapper.initialize();
    private static final AuthSideLoginAttemptToLoginAttemptEntityMapper LOGIN_ATTEMPT_TO_LOGIN_ATTEMPT_ENTITY_MAPPER = AuthSideLoginAttemptToLoginAttemptEntityMapper.initialize();

    /**
     * Retrieves a user's login attempt based on their user ID.
     *
     * @param userId The unique identifier of the user.
     * @return An {@link AuthSideLoginAttempt} object representing the user's login attempt.
     * @throws AuthSideLoginAttemptRecordNotFoundException if no matching entry is found.
     */
    @Override
    public AuthSideLoginAttempt findByUserId(final String userId) {
        final AuthSideLoginAttemptEntity loginAttemptEntity = loginAttemptRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthSideLoginAttemptRecordNotFoundException(userId));
        return LOGIN_ATTEMPT_ENTITY_TO_LOGIN_ATTEMPT_MAPPER.map(loginAttemptEntity);

    }

    /**
     * Saves a user's login attempt in the data store.
     *
     * @param loginAttempt The user's login attempt to save.
     */
    @Override
    public void save(final AuthSideLoginAttempt loginAttempt) {
        final AuthSideLoginAttemptEntity loginAttemptEntity = LOGIN_ATTEMPT_TO_LOGIN_ATTEMPT_ENTITY_MAPPER.map(loginAttempt);
        loginAttemptRepository.save(loginAttemptEntity);
    }

}