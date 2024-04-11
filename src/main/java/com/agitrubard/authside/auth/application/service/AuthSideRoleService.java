package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleSavePort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing user roles.
 * <p>
 * This service provides functionality for creating user roles in the system.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideRoleService implements AuthSideRoleUseCase {

    private final AuthSideRoleSavePort roleSavePort;

    /**
     * Creates a new user role based on the provided command.
     *
     * @param createCommand The command containing the data for creating the role.
     */
    @Override
    public void create(final AuthSideRoleCreateCommand createCommand) {

        final Set<AuthSidePermission> permissions = createCommand.getPermissionIds().stream()
                .map(permissionId -> AuthSidePermission.builder().id(permissionId).build())
                .collect(Collectors.toSet());

        final AuthSideRole role = AuthSideRole.builder()
                .id(UUID.randomUUID().toString())
                .name(createCommand.getName())
                .permissions(permissions)
                .status(AuthSideRoleStatus.ACTIVE)
                .build();

        roleSavePort.save(role);
    }

}
