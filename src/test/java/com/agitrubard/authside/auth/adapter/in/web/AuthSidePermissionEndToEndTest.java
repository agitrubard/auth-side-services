package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideEndToEndTest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponseBuilder;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Set;

class AuthSidePermissionEndToEndTest extends AuthSideEndToEndTest {

    private final AuthSidePermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = AuthSidePermissionToPermissionsResponseMapper.initialize();


    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenPermissionsExist_thenReturnPermissions() throws Exception {
        // Initialize
        Set<AuthSidePermission> mockPermissions = permissionUseCase.findAll();

        // Then
        String endpoint = BASE_PATH.concat("/permissions");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .get(endpoint, adminUserToken.getAccessToken());

        AuthSidePermissionsResponse mockPermissionsResponse = permissionToPermissionsResponseMapper.map(mockPermissions);
        AuthSideResponse<AuthSidePermissionsResponse> mockResponse = AuthSideResponse.successOf(mockPermissionsResponse);

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty());
    }

    @Test
    void whenUserUnauthorized_thenReturnAccessDeniedException() throws Exception {

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
    }

}
