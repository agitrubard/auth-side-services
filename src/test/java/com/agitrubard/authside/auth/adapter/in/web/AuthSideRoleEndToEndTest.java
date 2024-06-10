package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideEndToEndTest;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRoleToRolesResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRolesListRequestToCommandMapper;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideRolesResponse;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSidePermissionRepository;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRoleBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSidePageResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponseBuilder;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class AuthSideRoleEndToEndTest extends AuthSideEndToEndTest {

    @Autowired
    private AuthSideRoleRepository roleRepository;

    @Autowired
    private AuthSidePermissionRepository permissionRepository;

    private final AuthSideRolesListRequestToCommandMapper rolesListRequestToCommandMapper = AuthSideRolesListRequestToCommandMapper.initialize();
    private final AuthSideRoleToRolesResponseMapper roleToRolesResponseMapper = AuthSideRoleToRolesResponseMapper.initialize();

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenValidRolesListRequest_whenRolesFound_thenReturnPageOfRoles() throws Exception {

        // Given
        AuthSideRolesListRequest listRequest = new AuthSideRolesListRequestBuilder()
                .withValidFields()
                .build();

        // When
        AuthSideRolesListCommand listCommand = rolesListRequestToCommandMapper.map(listRequest);
        List<AuthSideRole> roles = List.of(
                new AuthSideRoleBuilder().withValidFields().build(),
                new AuthSideRoleBuilder().withValidFields().build()
        );
        AuthSidePage<AuthSideRole> pageOfRoles = AuthSidePage.<AuthSideRole>builder()
                .content(roles)
                .pageNumber(listCommand.getPagination().getPageNumber())
                .pageSize(roles.size())
                .totalPageCount(roles.size())
                .totalElementCount((long) roles.size())
                .sortedBy(listCommand.getSort())
                .filteredBy(listCommand.getFilter())
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/roles");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), listRequest);

        AuthSideResponse<AuthSidePageResponse<AuthSideRolesResponse>> response = new AuthSideResponseBuilder()
                .success(
                        AuthSidePageResponse.<AuthSideRolesResponse>builder()
                                .of(
                                        pageOfRoles,
                                        roleToRolesResponseMapper.map(pageOfRoles.getContent())
                                )
                                .build()
                )
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, response)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.content("size()")
                        .value(roles.size()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageNumber")
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageSize")
                        .value(roles.size()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalPageCount")
                        .value(1))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalElementCount")
                        .value(roles.size()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("sortedBy")
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.response("filteredBy")
                        .exists());
    }

    @Test
    void givenValidRolesListRequest_whenRoleNotFound_thenReturnEmptyPage() throws Exception {

        // Given
        AuthSideRolesListRequest mockListRequest = new AuthSideRolesListRequestBuilder()
                .withValidFields()
                .withName("Non-Existent Role")
                .build();

        // When
        AuthSideRolesListCommand listCommand = rolesListRequestToCommandMapper.map(mockListRequest);
        List<AuthSideRole> roles = List.of();
        AuthSidePage<AuthSideRole> pageOfRoles = AuthSidePage.<AuthSideRole>builder()
                .content(roles)
                .pageNumber(listCommand.getPagination().getPageNumber())
                .pageSize(0)
                .totalPageCount(0)
                .totalElementCount(0L)
                .sortedBy(listCommand.getSort())
                .filteredBy(listCommand.getFilter())
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/roles");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), mockListRequest);

        AuthSideResponse<AuthSidePageResponse<AuthSideRolesResponse>> response = new AuthSideResponseBuilder()
                .success(
                        AuthSidePageResponse.<AuthSideRolesResponse>builder()
                                .of(
                                        pageOfRoles,
                                        roleToRolesResponseMapper.map(pageOfRoles.getContent())
                                )
                                .build()
                )
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, response)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.content("size()")
                        .value(0))
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageNumber")
                        .value(mockListRequest.getPagination().getPageNumber()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageSize")
                        .value(0))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalPageCount")
                        .value(0))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalElementCount")
                        .value(0))
                .andExpect(AuthSideMockResultMatchersBuilders.response("sortedBy")
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.response("filteredBy")
                        .exists());
    }


    @Test
    @Transactional
    void givenValidRoleCreateRequest_whenRoleSavedSuccessfully_thenReturnSuccessResponse() throws Exception {

        // Initialize
        Set<String> permissionIds = permissionRepository.findAll().stream()
                .map(AuthSidePermissionEntity::getId)
                .collect(Collectors.toSet());

        // Given
        AuthSideRoleCreateRequest createRequest = new AuthSideRoleCreateRequestBuilder()
                .withName("New Role")
                .withPermissionIds(permissionIds)
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), createRequest);

        AuthSideResponse<Void> response = new AuthSideResponseBuilder()
                .success()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, response)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .doesNotExist());

        // Verify
        Optional<AuthSideRoleEntity> roleEntity = roleRepository.findAll().stream()
                .filter(role -> role.getName().equals(createRequest.getName()))
                .findFirst();

        Assertions.assertTrue(roleEntity.isPresent());
        Assertions.assertEquals(createRequest.getName(), roleEntity.get().getName());
        Assertions.assertEquals(createRequest.getPermissionIds().size(), roleEntity.get().getPermissions().size());
        createRequest.getPermissionIds().forEach(permissionId -> {
            Assertions.assertTrue(roleEntity.get().getPermissions().stream()
                    .anyMatch(permission -> permission.getId().equals(permissionId)));
        });
        Assertions.assertEquals(AuthSideRoleStatus.ACTIVE, roleEntity.get().getStatus());
        Assertions.assertNotNull(roleEntity.get().getCreatedBy());
        Assertions.assertNotNull(roleEntity.get().getCreatedAt());
        Assertions.assertNull(roleEntity.get().getUpdatedBy());
        Assertions.assertNull(roleEntity.get().getUpdatedAt());
    }

}
