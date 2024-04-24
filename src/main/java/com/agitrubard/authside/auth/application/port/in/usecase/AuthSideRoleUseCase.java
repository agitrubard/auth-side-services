package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.domain.model.AuthSidePage;

/**
 * Use case interface for managing authentication side roles.
 * Implementations of this interface should provide functionality to list authentication side roles
 * and create new authentication side roles.
 * <p>
 * It defines methods to retrieve a page of authentication side roles based on a command,
 * and to create a new authentication side role based on a command.
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleUseCase {

    /**
     * Retrieves a page of authentication side roles based on the provided command.
     *
     * @param listCommand The command containing criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    AuthSidePage<AuthSideRole> list(AuthSideRolesListCommand listCommand);

    /**
     * Creates a new authentication side role based on the provided command.
     *
     * @param createCommand The command containing information to create the authentication side role.
     */
    void create(AuthSideRoleCreateCommand createCommand);

}
