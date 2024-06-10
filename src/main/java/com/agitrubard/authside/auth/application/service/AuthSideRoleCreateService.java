package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleCreateUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleSavePort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class responsible for creating authentication side roles.
 * This class implements the AuthSideRoleCreateUseCase interface.
 * It interacts with a port to save the newly created authentication side role.
 * The role creation process involves constructing a role object using data provided in a create command.
 * Permissions associated with the role are retrieved from the create command and mapped to AuthSidePermission objects.
 * The created role is then saved using the specified port.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideRoleCreateService implements AuthSideRoleCreateUseCase {

    private final AuthSideRoleSavePort roleSavePort;

    /**
     * Creates a new authentication side role based on the provided command.
     *
     * @param createCommand The command containing data for creating the role.
     */
    @Override
    public void create(final AuthSideRoleCreateCommand createCommand) {

        final Set<AuthSidePermission> permissions = createCommand.getPermissionIds().stream()
                .map(permissionId -> AuthSidePermission.builder().id(permissionId).build())
                .collect(Collectors.toSet());

        final AuthSideRole role = AuthSideRole.builder()
                .name(createCommand.getName())
                .permissions(permissions)
                .build();

        roleSavePort.save(role);
    }

}
