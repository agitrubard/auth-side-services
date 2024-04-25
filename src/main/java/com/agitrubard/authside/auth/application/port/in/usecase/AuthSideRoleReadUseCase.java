package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.domain.model.AuthSidePage;

/**
 * Manages the retrieval of authentication side roles.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleReadUseCase {

    /**
     * Retrieves a page of authentication side roles based on the provided command.
     *
     * @param listCommand The command containing criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    AuthSidePage<AuthSideRole> list(AuthSideRolesListCommand listCommand);

}
