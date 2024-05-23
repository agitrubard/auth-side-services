package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRoleToRolesResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRolesListRequestToCommandMapper;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideRolesResponse;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRoleBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSidePageResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponseBuilder;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class AuthSideRoleEndToEndTest extends AuthSidePermissionEndToEndTest {

    private final AuthSideRolesListRequestToCommandMapper rolesListRequestToCommandMapper = AuthSideRolesListRequestToCommandMapper.initialize();
    private final AuthSideRoleToRolesResponseMapper roleToRolesResponseMapper = AuthSideRoleToRolesResponseMapper.initialize();

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenValidRolesListRequest_whenRolesFound_thenReturnPageOfRoles() throws Exception {

        // Given
        AuthSideRolesListRequest mockListRequest = new AuthSideRolesListRequestBuilder()
                .withValidFields()
                .build();

        // When
        AuthSideRolesListCommand mockListCommand = rolesListRequestToCommandMapper.map(mockListRequest);
        List<AuthSideRole> mockRoles = List.of(
                new AuthSideRoleBuilder().withValidFields().build(),
                new AuthSideRoleBuilder().withValidFields().build()
        );
        AuthSidePage<AuthSideRole> pageOfRoles = AuthSidePage.<AuthSideRole>builder()
                .content(mockRoles)
                .pageNumber(mockListCommand.getPagination().getPageNumber())
                .pageSize(mockRoles.size())
                .totalPageCount(mockRoles.size())
                .totalElementCount((long) mockRoles.size())
                .sortedBy(mockListCommand.getSort())
                .filteredBy(mockListCommand.getFilter())
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/roles");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), mockListRequest);

        AuthSideResponse<AuthSidePageResponse<AuthSideRolesResponse>> mockResponse = new AuthSideResponseBuilder()
                .success(
                        AuthSidePageResponse.<AuthSideRolesResponse>builder()
                                .of(
                                        pageOfRoles,
                                        roleToRolesResponseMapper.map(pageOfRoles.getContent())
                                )
                                .build()
                )
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.content("size()")
                        .value(mockRoles.size()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageNumber")
                        .exists())
                .andExpect(AuthSideMockResultMatchersBuilders.response("pageSize")
                        .value(mockRoles.size()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalPageCount")
                        .value(1))
                .andExpect(AuthSideMockResultMatchersBuilders.response("totalElementCount")
                        .value(mockRoles.size()))
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
        AuthSideRolesListCommand mockListCommand = rolesListRequestToCommandMapper.map(mockListRequest);
        List<AuthSideRole> mockRoles = List.of();
        AuthSidePage<AuthSideRole> pageOfRoles = AuthSidePage.<AuthSideRole>builder()
                .content(mockRoles)
                .pageNumber(mockListCommand.getPagination().getPageNumber())
                .pageSize(0)
                .totalPageCount(0)
                .totalElementCount(0L)
                .sortedBy(mockListCommand.getSort())
                .filteredBy(mockListCommand.getFilter())
                .build();

        // Then
        String endpoint = BASE_PATH.concat("/roles");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), mockListRequest);

        AuthSideResponse<AuthSidePageResponse<AuthSideRolesResponse>> mockResponse = new AuthSideResponseBuilder()
                .success(
                        AuthSidePageResponse.<AuthSideRolesResponse>builder()
                                .of(
                                        pageOfRoles,
                                        roleToRolesResponseMapper.map(pageOfRoles.getContent())
                                )
                                .build()
                )
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
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
    void givenValidRoleCreateRequest_whenRoleSavedSuccessfully_thenReturnSuccessResponse() throws Exception {

        // Initialize
        Set<String> mockPermissionIds = permissionUseCase.findAll().stream()
                .map(AuthSidePermission::getId)
                .collect(Collectors.toSet());

        // Given
        AuthSideRoleCreateRequest mockCreateRequest = new AuthSideRoleCreateRequestBuilder()
                .withName("New Role")
                .withPermissionIds(mockPermissionIds).build();

        // Then
        String endpoint = BASE_PATH.concat("/role");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, adminUserToken.getAccessToken(), mockCreateRequest);

        AuthSideResponse<Void> mockResponse = new AuthSideResponseBuilder()
                .success()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .doesNotExist());
    }

}
