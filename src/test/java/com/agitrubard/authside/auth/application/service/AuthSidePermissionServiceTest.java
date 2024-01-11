package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.application.port.out.AuthSidePermissionReadPort;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermissionBuilder;
import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Set;
import java.util.UUID;

class AuthSidePermissionServiceTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSidePermissionService permissionService;

    @Mock
    private AuthSidePermissionReadPort permissionReadPort;


    @Test
    void whenFindAllPermissions_thenReturnAllPermissions() {

        // Initialize
        Set<AuthSidePermission> mockPermissions = Set.of(
                new AuthSidePermissionBuilder()
                        .withId(UUID.randomUUID().toString())
                        .withName("user:list")
                        .withCategory(AuthSidePermissionCategory.USER_MANAGEMENT)
                        .build(),
                new AuthSidePermissionBuilder()
                        .withId(UUID.randomUUID().toString())
                        .withName("role:list")
                        .withCategory(AuthSidePermissionCategory.ROLE_MANAGEMENT)
                        .build()
        );

        // When
        Mockito.when(permissionService.findAll())
                .thenReturn(mockPermissions);

        // Then
        Set<AuthSidePermission> permissions = permissionReadPort.findAll();

        Assertions.assertEquals(mockPermissions.size(), permissions.size());
        Assertions.assertEquals(mockPermissions.iterator().next().getId(), permissions.iterator().next().getId());
        Assertions.assertEquals(mockPermissions.iterator().next().getName(), permissions.iterator().next().getName());
        Assertions.assertEquals(mockPermissions.iterator().next().getCategory(), permissions.iterator().next().getCategory());

        // Verify
        Mockito.verify(permissionReadPort, Mockito.times(1))
                .findAll();
    }

    @Test
    void whenPermissionsAreNotExist_thenReturnEmptySet() {

        // Initialize
        Set<AuthSidePermission> mockPermissions = Set.of();

        // When
        Mockito.when(permissionReadPort.findAll())
                .thenReturn(mockPermissions);

        // Then
        Set<AuthSidePermission> permissions = permissionService.findAll();

        Assertions.assertEquals(0, permissions.size());

        // Verify
        Mockito.verify(permissionReadPort, Mockito.times(1))
                .findAll();
    }

}
