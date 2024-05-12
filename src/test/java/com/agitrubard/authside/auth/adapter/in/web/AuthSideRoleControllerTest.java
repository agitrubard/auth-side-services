package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideRestControllerTest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequestBuilder;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleCreateUseCase;
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
import java.util.UUID;

class AuthSideRoleControllerTest extends AuthSideRestControllerTest {


    @MockBean
    private AuthSideRoleCreateUseCase roleUseCase;


    private static final String BASE_PATH = "/api/v1";

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
                .when(roleUseCase)
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
                .andExpect(AuthSideMockResultMatchersBuilders.content()
                        .doesNotExist());

        // Verify-
        Mockito.verify(roleUseCase, Mockito.times(1))
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
        Mockito.verify(roleUseCase, Mockito.never())
                .create(Mockito.any(AuthSideRoleCreateCommand.class));
    }

}