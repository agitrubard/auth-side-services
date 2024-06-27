package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * AuthSidePermissionController is a Spring MVC controller responsible for handling
 * authentication side permission-related HTTP requests within the API version 1.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class AuthSidePermissionController {

    private final AuthSidePermissionUseCase permissionUseCase;

    private final AuthSidePermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = AuthSidePermissionToPermissionsResponseMapper.initialize();

    /**
     * Retrieves all permissions accessible to the user, filtered based on their authorities.
     * <p>
     * This method is mapped to handle HTTP GET requests to "/permissions". It requires
     * the user to have either 'role:create' or 'role:update' authority to access.
     *
     * @return An {@link AuthSideResponse} containing a set of {@link AuthSidePermissionsResponse}
     * representing all permissions available to the user.
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAnyAuthority('role:create', 'role:update')")
    public AuthSideResponse<Set<AuthSidePermissionsResponse>> findAll() {
        final Set<AuthSidePermission> permissions = permissionUseCase.findAll();
        return AuthSideResponse.success(permissionToPermissionsResponseMapper.map(permissions));
    }

}
