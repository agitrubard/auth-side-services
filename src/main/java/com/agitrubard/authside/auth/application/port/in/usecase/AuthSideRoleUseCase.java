package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleUseCase {

    void create(AuthSideRoleCreateCommand createCommand);

}
