package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;

/**
 * The {@code AuthSideLoginAttemptReadPort} interface defines a method for retrieving a user's login attempt information
 * by their user ID in the authentication side of the application. Implementations of this interface are responsible for
 * querying and providing user login attempt records for further processing.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideLoginAttemptReadPort {

    /**
     * Retrieves a user's login attempt record by their user ID.
     *
     * @param userId The unique identifier of the user for whom the login attempt record is requested.
     * @return An {@link AuthSideLoginAttempt} object representing the user's login attempt information.
     */
    AuthSideLoginAttempt findByUserId(String userId);

}
