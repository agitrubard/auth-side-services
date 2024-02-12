package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideRestControllerTest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermissionBuilder;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponseBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponseBuilder;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Set;

class AuthSidePermissionControllerTest extends AuthSideRestControllerTest {


    @MockBean
    private AuthSidePermissionUseCase permissionUseCase;


    private final AuthSidePermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = AuthSidePermissionToPermissionsResponseMapper.initialize();


    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenPermissionsExist_thenReturnPermissions() throws Exception {
        // When
        Set<AuthSidePermission> mockPermissions = Set.of(
                new AuthSidePermissionBuilder().withValidFields().build(),
                new AuthSidePermissionBuilder().withValidFields().build()
        );

        Mockito.when(permissionUseCase.findAll())
                .thenReturn(mockPermissions);

        // Then
        String endpoint = BASE_PATH.concat("/permissions");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .get(endpoint, adminUserToken.getAccessToken());

        AuthSidePermissionsResponse mockPermissionsResponse = permissionToPermissionsResponseMapper.map(mockPermissions);
        AuthSideResponse<AuthSidePermissionsResponse> mockResponse = new AuthSideResponseBuilder()
                .success(mockPermissionsResponse)
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty());

        // Verify
        Mockito.verify(permissionUseCase, Mockito.times(1))
                .findAll();
    }

    @Test
    void whenUserHasNotPermissions_thenReturnForbiddenErrorResponse() throws Exception {

        // Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .get(BASE_PATH.concat("/permissions"), userToken.getAccessToken());

        AuthSideErrorResponse mockErrorResponse = new AuthSideErrorResponseBuilder()
                .forbidden()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isForbidden())
                .andExpect(AuthSideMockResultMatchersBuilders.subErrors()
                        .doesNotExist());

        // Verify
        Mockito.verify(permissionUseCase, Mockito.never())
                .findAll();
    }

}
