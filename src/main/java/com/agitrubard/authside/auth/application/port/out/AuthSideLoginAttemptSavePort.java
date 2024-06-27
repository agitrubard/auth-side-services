package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;

/**
 * The {@link AuthSideLoginAttemptSavePort} interface defines a method for saving user login attempt records
 * in the authentication side of the application. Implementations of this interface are responsible for persisting
 * login attempt records for future retrieval and analysis.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideLoginAttemptSavePort {

    /**
     * Saves a user's login attempt record.
     *
     * @param loginAttempt An {@link AuthSideLoginAttempt} object representing the user's login attempt information
     *                     to be saved.
     */
    void save(AuthSideLoginAttempt loginAttempt);

}
