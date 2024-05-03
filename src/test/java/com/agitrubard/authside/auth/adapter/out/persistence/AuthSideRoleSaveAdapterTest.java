package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideRoleToEntityMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSidePermissionRepository;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.application.exception.AuthSidePermissionNotFoundException;
import com.agitrubard.authside.auth.application.exception.AuthSideRoleAlreadyExistException;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRoleBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

class AuthSideRoleSaveAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideRoleSaveAdapter roleSaveAdapter;

    @Mock
    private AuthSideRoleRepository roleRepository;

    @Mock
    private AuthSidePermissionRepository permissionRepository;


    private final AuthSideRoleToEntityMapper roleToEntityMapper = AuthSideRoleToEntityMapper.initialize();

    @Test
    void givenValidRole_whenRoleNameAndPermissionIdsValid_thenSaveRole() {
        // Given
        AuthSideRole mockRole = new AuthSideRoleBuilder()
                .withValidFields()
                .build();

        // When
        Mockito.when(roleRepository.existsByName(Mockito.anyString()))
                .thenReturn(false);

        List<AuthSidePermissionEntity> mockPermissionEntities = new ArrayList<>();
        mockRole.getPermissions().forEach(permission -> {
            mockPermissionEntities.add(
                    new AuthSidePermissionEntityBuilder()
                            .withId(permission.getId())
                            .withName(permission.getName())
                            .withCategory(permission.getCategory())
                            .build()
            );
        });
        Mockito.when(permissionRepository.findAll())
                .thenReturn(mockPermissionEntities);

        AuthSideRoleEntity roleEntity = roleToEntityMapper.map(mockRole);
        Mockito.when(roleRepository.save(Mockito.any(AuthSideRoleEntity.class)))
                .thenReturn(roleEntity);

        // Then
        roleSaveAdapter.save(mockRole);

        // Verify
        Mockito.verify(roleRepository, Mockito.times(1))
                .existsByName(Mockito.anyString());

        Mockito.verify(permissionRepository, Mockito.times(1))
                .findAll();

        Mockito.verify(roleRepository, Mockito.times(1))
                .save(Mockito.any(AuthSideRoleEntity.class));
    }

    @Test
    void givenRoleWithExistingName_whenRoleNameAlreadyExist_thenThrowRoleAlreadyExistException() {
        // Given
        AuthSideRole mockRole = new AuthSideRoleBuilder()
                .withValidFields()
                .build();

        // When
        Mockito.when(roleRepository.existsByName(Mockito.anyString()))
                .thenReturn(true);

        // Then
        Assertions.assertThrows(
                AuthSideRoleAlreadyExistException.class,
                () -> roleSaveAdapter.save(mockRole)
        );

        // Verify
        Mockito.verify(roleRepository, Mockito.times(1))
                .existsByName(Mockito.anyString());

        Mockito.verify(permissionRepository, Mockito.never())
                .findAll();

        Mockito.verify(roleRepository, Mockito.never())
                .save(Mockito.any(AuthSideRoleEntity.class));
    }

    @Test
    void givenRoleWithInvalidPermissionIds_whenPermissionIdsAreNotExist_thenThrowPermissionNotFoundException() {
        // Given
        AuthSideRole mockRole = new AuthSideRoleBuilder()
                .withValidFields()
                .build();

        // When
        Mockito.when(roleRepository.existsByName(Mockito.anyString()))
                .thenReturn(false);

        List<AuthSidePermissionEntity> mockPermissionEntities = List.of(
                new AuthSidePermissionEntityBuilder()
                        .withValidFields()
                        .build()
        );
        Mockito.when(permissionRepository.findAll())
                .thenReturn(mockPermissionEntities);

        // Then
        Assertions.assertThrows(
                AuthSidePermissionNotFoundException.class,
                () -> roleSaveAdapter.save(mockRole)
        );

        // Verify
        Mockito.verify(roleRepository, Mockito.times(1))
                .existsByName(Mockito.anyString());

        Mockito.verify(permissionRepository, Mockito.times(1))
                .findAll();

        Mockito.verify(roleRepository, Mockito.never())
                .save(Mockito.any(AuthSideRoleEntity.class));
    }

}
