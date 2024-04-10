package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;

/**
 * Port for saving user roles.
 * <p>
 * This port defines the method for saving user roles in the system.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleSavePort {

    /**
     * Saves the given user role.
     *
     * @param role The user role to be saved.
     */
    void save(AuthSideRole role);

}
