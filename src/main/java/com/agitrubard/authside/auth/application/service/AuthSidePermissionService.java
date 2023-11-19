package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSidePermissionReadPort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * AuthSidePermissionService is a service class that implements the AuthSidePermissionUseCase
 * interface. It provides business logic for managing authentication side permissions.
 */
@Service
@RequiredArgsConstructor
class AuthSidePermissionService implements AuthSidePermissionUseCase {

    private final AuthSidePermissionReadPort permissionReadPort;

    /**
     * Retrieves all authentication side permissions.
     *
     * @return A set of AuthSidePermission objects representing all permissions.
     */
    @Override
    public Set<AuthSidePermission> findAll() {
        return permissionReadPort.findAll();
    }

}
