package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;

import java.util.Optional;

/**
 * The {@code AuthSideUserReadPort} interface defines methods for reading user information in the authentication system.
 * Implementations of this interface provide the ability to retrieve user details by their ID or username.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideUserReadPort {

    /**
     * Retrieves a user by their unique identifier (ID).
     *
     * @param id The unique ID of the user to retrieve.
     * @return An {@link Optional} containing the user information if found, or an empty {@link Optional} if not found.
     */
    Optional<AuthSideUser> findById(String id);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return An {@link Optional} containing the user information if found, or an empty {@link Optional} if not found.
     */
    Optional<AuthSideUser> findByUsername(String username);

}
