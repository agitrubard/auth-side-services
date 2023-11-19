package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSidePermissionRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSidePermissionReadPort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionEntityToPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * AuthSidePermissionAdapter is an implementation of the AuthSidePermissionReadPort
 * interface, providing read operations for authentication side permissions.
 * This component utilizes the AuthSidePermissionRepository to interact with the
 * underlying data store and converts entities to domain objects using a mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSidePermissionAdapter implements AuthSidePermissionReadPort {

    private final AuthSidePermissionRepository permissionRepository;

    private final AuthSidePermissionEntityToPermissionMapper permissionEntityToPermissionMapper = AuthSidePermissionEntityToPermissionMapper.initialize();

    /**
     * Retrieves all authentication side permissions.
     *
     * @return A set of AuthSidePermission objects representing all permissions.
     */
    @Override
    public Set<AuthSidePermission> findAll() {
        final List<AuthSidePermissionEntity> permissionEntities = permissionRepository.findAll();
        return permissionEntityToPermissionMapper.map(permissionEntities);
    }

}
