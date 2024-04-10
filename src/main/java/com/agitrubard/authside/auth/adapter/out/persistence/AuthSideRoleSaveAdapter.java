package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSidePermissionRepository;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.application.exception.AuthSidePermissionNotFoundException;
import com.agitrubard.authside.auth.application.exception.AuthSideRoleAlreadyExistException;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleSavePort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.mapper.AuthSideRoleToEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapter class for saving user roles.
 * <p>
 * This adapter communicates with the data layer to save user roles.
 * It ensures that the role name is unique and that the associated permissions exist before saving.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideRoleSaveAdapter implements AuthSideRoleSavePort {

    private final AuthSideRoleRepository roleRepository;
    private final AuthSidePermissionRepository permissionRepository;

    private final AuthSideRoleToEntityMapper roleToEntityMapper = AuthSideRoleToEntityMapper.initialize();

    /**
     * Saves the given user role.
     *
     * @param role The user role to be saved.
     * @throws AuthSideRoleAlreadyExistException   if a role with the same name already exists.
     * @throws AuthSidePermissionNotFoundException if any of the associated permissions are not found.
     */
    @Override
    public void save(final AuthSideRole role) {

        this.checkRoleName(role.getName());

        final Set<String> permissionIds = role.getPermissions().stream()
                .map(AuthSidePermission::getId)
                .collect(Collectors.toSet());
        this.checkPermissionIds(permissionIds);

        final AuthSideRoleEntity roleEntity = roleToEntityMapper.map(role);
        roleRepository.save(roleEntity);

    }

    private void checkRoleName(final String name) {
        boolean isRoleExist = roleRepository.existsByName(name);
        if (isRoleExist) {
            throw new AuthSideRoleAlreadyExistException(name);
        }
    }

    private void checkPermissionIds(final Set<String> permissionIds) {

        final Set<AuthSidePermissionEntity> permissionEntities = new HashSet<>(permissionRepository.findAll());

        final Set<String> notFoundPermissionIds = permissionIds.stream()
                .filter(permissionId -> permissionEntities.stream().noneMatch(permission -> permission.getId().equals(permissionId)))
                .collect(Collectors.toSet());

        if (!notFoundPermissionIds.isEmpty()) {
            throw new AuthSidePermissionNotFoundException(notFoundPermissionIds);
        }

    }

}
