package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideUserEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideUserEntityToUserMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideUserRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSideUserReadPort;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * The {@code AuthSideUserAdapter} class is an adapter component responsible for bridging the gap between the application's
 * business logic and the data access layer. It implements the {@link AuthSideUserReadPort} interface, providing methods to
 * read user profiles on the authentication side of the application.
 * <p>
 * This adapter relies on the {@link AuthSideUserRepository} for data retrieval and uses a mapper to convert between
 * entity objects ({@link AuthSideUserEntity}) and business objects ({@link AuthSideUser}).
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideUserAdapter implements AuthSideUserReadPort {

    private final AuthSideUserRepository userRepository;

    private final AuthSideUserEntityToUserMapper userEntityToUserMapper = AuthSideUserEntityToUserMapper.initialize();

    /**
     * Retrieves a user profile based on the user's unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return An {@link Optional} containing the user profile, or an empty {@link Optional} if no matching user is found.
     */
    @Override
    public Optional<AuthSideUser> findById(final String id) {
        final Optional<AuthSideUserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userEntityToUserMapper::map);
    }

    /**
     * Retrieves a user profile based on the user's username.
     *
     * @param username The username of the user.
     * @return An {@link Optional} containing the user profile, or an empty {@link Optional} if no matching user is found.
     */
    @Override
    public Optional<AuthSideUser> findByUsername(final String username) {
        final Optional<AuthSideUserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(userEntityToUserMapper::map);
    }

}
