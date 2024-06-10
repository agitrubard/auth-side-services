package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideEndToEndTest;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponseBuilder;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Set;

class AuthSidePermissionEndToEndTest extends AuthSideEndToEndTest {

    @Autowired
    private AuthSidePermissionUseCase permissionUseCase;

    private final AuthSidePermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = AuthSidePermissionToPermissionsResponseMapper.initialize();


    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenPermissionsExist_thenReturnPermissions() throws Exception {
        // Initialize
        Set<AuthSidePermission> permissions = permissionUseCase.findAll();

        // Then
        String endpoint = BASE_PATH.concat("/permissions");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .get(endpoint, adminUserToken.getAccessToken());

        Set<AuthSidePermissionsResponse> mockPermissionsResponses = permissionToPermissionsResponseMapper.map(permissions);
        AuthSideResponse<Set<AuthSidePermissionsResponse>> mockResponse = new AuthSideResponseBuilder()
                .success(mockPermissionsResponses)
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.response("size()")
                        .value(permissions.size()));
    }

}
