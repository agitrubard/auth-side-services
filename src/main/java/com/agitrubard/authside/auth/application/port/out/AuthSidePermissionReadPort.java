package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;

import java.util.Set;

/**
 * AuthSidePermissionReadPort defines the port for reading authentication side permissions.
 * This interface declares the contract for retrieving all authentication side permissions.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSidePermissionReadPort {

    /**
     * Retrieves all authentication side permissions.
     *
     * @return A set of AuthSidePermission objects representing all permissions.
     */
    Set<AuthSidePermission> findAll();

}
