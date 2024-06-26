package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideRestControllerTest;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRoleToRolesResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRolesListRequestToCommandMapper;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideRolesResponse;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleCreateUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleReadUseCase;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRoleBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponseBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSidePageResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponseBuilder;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Set;
import java.util.UUID;

class AuthSideRoleControllerTest extends AuthSideRestControllerTest {

    @MockBean
    private AuthSideRoleReadUseCase roleReadUseCase;

    @MockBean
    private AuthSideRoleCreateUseCase roleCreateUseCase;


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
        Mockito.when(roleReadUseCase.list(Mockito.any(AuthSideRolesListCommand.class)))
                .thenReturn(pageOfRoles);

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

        // Verify-
        Mockito.verify(roleReadUseCase, Mockito.times(1))
                .list(Mockito.any(AuthSideRolesListCommand.class));
    }

    @Test
    void givenValidRolesListRequest_whenRoleNotFound_thenReturnEmptyPage() throws Exception {

        // Given
        AuthSideRolesListRequest mockListRequest = new AuthSideRolesListRequestBuilder()
                .withValidFields()
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
        Mockito.when(roleReadUseCase.list(Mockito.any(AuthSideRolesListCommand.class)))
                .thenReturn(pageOfRoles);

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
                        .value(mockListRequest.getPageable().getPageNumber()))
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

        // Verify-
        Mockito.verify(roleReadUseCase, Mockito.times(1))
                .list(Mockito.any(AuthSideRolesListCommand.class));
    }


    @Test
    void givenValidRoleCreateRequest_whenRoleSavedSuccessfully_thenReturnSuccessResponse() throws Exception {

        // Given
        AuthSideRoleCreateRequest mockCreateRequest = new AuthSideRoleCreateRequestBuilder()
                .withName("Test")
                .withPermissionIds(
                        Set.of(
                                UUID.randomUUID().toString(),
                                UUID.randomUUID().toString()
                        )
                ).build();

        // When
        Mockito.doNothing()
                .when(roleCreateUseCase)
                .create(Mockito.any(AuthSideRoleCreateCommand.class));

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

        // Verify-
        Mockito.verify(roleCreateUseCase, Mockito.times(1))
                .create(Mockito.any(AuthSideRoleCreateCommand.class));
    }

    @Test
    void givenValidRoleCreateRequest_whenUserHasNotPermissions_thenReturnForbiddenErrorResponse() throws Exception {

        // Given
        AuthSideRoleCreateRequest mockCreateRequest = new AuthSideRoleCreateRequestBuilder()
                .withName("Test")
                .withPermissionIds(
                        Set.of(
                                UUID.randomUUID().toString(),
                                UUID.randomUUID().toString()
                        )
                ).build();

        // Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(BASE_PATH.concat("/role"), userToken.getAccessToken(), mockCreateRequest);

        AuthSideErrorResponse mockErrorResponse = new AuthSideErrorResponseBuilder()
                .forbidden()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isForbidden())
                .andExpect(AuthSideMockResultMatchersBuilders.subErrors()
                        .doesNotExist());

        // Verify
        Mockito.verify(roleCreateUseCase, Mockito.never())
                .create(Mockito.any(AuthSideRoleCreateCommand.class));
    }

}