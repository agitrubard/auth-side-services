package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSidePermissionRepository;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionEntityToPermissionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

class AuthSidePermissionAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSidePermissionAdapter permissionAdapter;

    @Mock
    private AuthSidePermissionRepository permissionRepository;


    private final AuthSidePermissionEntityToPermissionMapper permissionEntityToPermissionMapper = AuthSidePermissionEntityToPermissionMapper.initialize();


    @Test
    void whenFindAllPermissions_thenReturnAllPermissions() {

        // Initialize
        Set<AuthSidePermissionEntity> mockPermissionEntities = Set.of(
                new AuthSidePermissionEntityBuilder()
                        .withId(UUID.randomUUID().toString())
                        .withName("user:list")
                        .withCategory(AuthSidePermissionCategory.USER_MANAGEMENT)
                        .build(),
                new AuthSidePermissionEntityBuilder()
                        .withId(UUID.randomUUID().toString())
                        .withName("role:list")
                        .withCategory(AuthSidePermissionCategory.ROLE_MANAGEMENT)
                        .build()
        );

        Set<AuthSidePermission> mockPermissions = permissionEntityToPermissionMapper.map(mockPermissionEntities);

        // When
        Mockito.when(permissionRepository.findAll())
                .thenReturn(new ArrayList<>(mockPermissionEntities));

        // Then
        Set<AuthSidePermission> permissions = permissionAdapter.findAll();

        Assertions.assertEquals(mockPermissions.size(), permissions.size());
        Assertions.assertEquals(mockPermissions.iterator().next().getId(), permissions.iterator().next().getId());
        Assertions.assertEquals(mockPermissions.iterator().next().getName(), permissions.iterator().next().getName());
        Assertions.assertEquals(mockPermissions.iterator().next().getCategory(), permissions.iterator().next().getCategory());

        // Verify
        Mockito.verify(permissionRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    void whenPermissionsAreNotExist_thenReturnEmptySet() {

        // Initialize
        Set<AuthSidePermissionEntity> mockPermissionEntities = Set.of();

        Set<AuthSidePermission> mockPermissions = permissionEntityToPermissionMapper.map(mockPermissionEntities);

        // When
        Mockito.when(permissionRepository.findAll())
                .thenReturn(new ArrayList<>(mockPermissionEntities));

        // Then
        Set<AuthSidePermission> permissions = permissionAdapter.findAll();

        Assertions.assertEquals(mockPermissions.size(), permissions.size());

        // Verify
        Mockito.verify(permissionRepository, Mockito.times(1))
                .findAll();
    }

}