package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideEndToEndTest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

class AuthSidePermissionEndToEndTest extends AuthSideEndToEndTest {

    private final AuthSidePermissionToPermissionsResponseMapper permissionToPermissionsResponseMapper = AuthSidePermissionToPermissionsResponseMapper.initialize();


    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenPermissionsExist_thenReturnPermissions() throws Exception {
        // Initialize
        Set<AuthSidePermission> mockPermissions = permissionUseCase.findAll();

        // Then
        AuthSidePermissionsResponse mockPermissionsResponse = permissionToPermissionsResponseMapper.map(mockPermissions);
        AuthSideResponse<AuthSidePermissionsResponse> mockAysResponse = AuthSideResponse.successOf(mockPermissionsResponse);

        mockMvc.perform(AuthSideMockMvcRequestBuilders
                        .get(BASE_PATH.concat("/permissions"), adminUserToken.getAccessToken()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AuthSideMockResultMatchersBuilders.status().isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.time()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus()
                        .value(mockAysResponse.getHttpStatus().getReasonPhrase()))
                .andExpect(AuthSideMockResultMatchersBuilders.isSuccess()
                        .value(mockAysResponse.getIsSuccess()))
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty());
    }

    @Test
    void whenUserUnauthorized_thenReturnAccessDeniedException() throws Exception {

        // When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .get(BASE_PATH.concat("/permissions"), userToken.getAccessToken());

        // Then
        AuthSideErrorResponse mockResponse = AuthSideErrorResponse.FORBIDDEN;
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AuthSideMockResultMatchersBuilders.status().isForbidden())
                .andExpect(AuthSideMockResultMatchersBuilders.time().isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.httpStatus().value(mockResponse.getHttpStatus().name()))
                .andExpect(AuthSideMockResultMatchersBuilders.isSuccess().value(mockResponse.getIsSuccess()))
                .andExpect(AuthSideMockResultMatchersBuilders.response().doesNotExist());
    }

}
