package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.mapper.AuthSideRolesListCommandToRolesListingMapper;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleCreateUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleReadUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleSavePort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Provides service for managing authentication side roles.
 *
 * <p>This service implements both reading and creating authentication side roles.</p>
 *
 * <p>This class requires dependencies of role read and role save ports.</p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideRoleService implements AuthSideRoleReadUseCase, AuthSideRoleCreateUseCase {

    private final AuthSideRoleReadPort roleReadPort;
    private final AuthSideRoleSavePort roleSavePort;

    private final AuthSideRolesListCommandToRolesListingMapper rolesListCommandToRolesListingMapper = AuthSideRolesListCommandToRolesListingMapper.initialize();

    /**
     * Retrieves a page of authentication side roles based on the provided list command.
     *
     * @param listCommand The command containing criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    @Override
    public AuthSidePage<AuthSideRole> list(AuthSideRolesListCommand listCommand) {
        AuthSideRolesListing rolesListing = rolesListCommandToRolesListingMapper.map(listCommand);
        return roleReadPort.findAll(rolesListing);
    }

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
                .id(UUID.randomUUID().toString())
                .name(createCommand.getName())
                .permissions(permissions)
                .status(AuthSideRoleStatus.ACTIVE)
                .build();

        roleSavePort.save(role);
    }

}
