package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;

/**
 * Manages the creation of authentication side roles.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleCreateUseCase {

    /**
     * Creates a new authentication side role based on the provided command.
     *
     * @param createCommand The command containing information to create the authentication side role.
     */
    void create(AuthSideRoleCreateCommand createCommand);

}
