package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideRestControllerTest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermissionBuilder;
import com.agitrubard.authside.auth.mapper.AuthSidePermissionToPermissionsResponseMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.util.AuthSideMockMvcRequestBuilders;
import com.agitrubard.authside.util.AuthSideMockResultMatchersBuilders;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

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

        Mockito.when(permissionUseCase.findAll()).thenReturn(mockPermissions);

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
