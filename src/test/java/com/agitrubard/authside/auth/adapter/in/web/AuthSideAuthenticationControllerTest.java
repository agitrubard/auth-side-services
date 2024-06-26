package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.AuthSideRestControllerTest;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideTokenToTokenResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideLoginRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideLoginRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokenRefreshRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokenRefreshRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokensInvalidateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokensInvalidateRequestBuilder;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideTokenResponse;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideAuthenticationUseCase;
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

class AuthSideAuthenticationControllerTest extends AuthSideRestControllerTest {

    @MockBean
    private AuthSideAuthenticationUseCase authenticationUseCase;


    private final AuthSideTokenToTokenResponseMapper tokenToTokenResponseMapper = AuthSideTokenToTokenResponseMapper.initialize();


    private static final String BASE_PATH = "/api/v1/authentication";

    @Test
    void givenValidUserLoginRequest_whenTokensGeneratedSuccessfully_thenReturnTokenResponse() throws Exception {
        // Given
        AuthSideLoginRequest mockLoginRequest = new AuthSideLoginRequestBuilder().build();

        // When
        Mockito.when(authenticationUseCase.authenticate(Mockito.any(AuthSideLoginCommand.class)))
                .thenReturn(userToken);

        // Then
        String endpoint = BASE_PATH.concat("/token");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, mockLoginRequest);

        AuthSideTokenResponse mockTokenResponse = tokenToTokenResponseMapper.map(userToken);
        AuthSideResponse<AuthSideTokenResponse> mockResponse = new AuthSideResponseBuilder()
                .success(mockTokenResponse)
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.response("accessToken")
                        .value(mockResponse.getResponse().getAccessToken()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("accessTokenExpiresAt")
                        .value(mockResponse.getResponse().getAccessTokenExpiresAt()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("refreshToken")
                        .value(mockResponse.getResponse().getRefreshToken()));

        // Verify
        Mockito.verify(authenticationUseCase, Mockito.times(1))
                .authenticate(Mockito.any(AuthSideLoginCommand.class));
    }


    @Test
    void givenValidTokenRefreshRequest_whenAccessTokenGeneratedSuccessfully_thenReturnTokenResponse() throws Exception {
        // Given
        AuthSideTokenRefreshRequest mockTokenRefreshRequest = new AuthSideTokenRefreshRequestBuilder()
                .withRefreshToken(adminUserToken.getRefreshToken())
                .build();

        // When
        Mockito.when(authenticationUseCase.refreshAccessToken(Mockito.any(AuthSideTokenRefreshCommand.class)))
                .thenReturn(userToken);

        // Then
        String endpoint = BASE_PATH.concat("/token/refresh");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, mockTokenRefreshRequest);

        AuthSideTokenResponse mockTokenResponse = tokenToTokenResponseMapper.map(userToken);
        AuthSideResponse<AuthSideTokenResponse> mockResponse = new AuthSideResponseBuilder()
                .success(mockTokenResponse)
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .isNotEmpty())
                .andExpect(AuthSideMockResultMatchersBuilders.response("accessToken")
                        .value(mockResponse.getResponse().getAccessToken()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("accessTokenExpiresAt")
                        .value(mockResponse.getResponse().getAccessTokenExpiresAt()))
                .andExpect(AuthSideMockResultMatchersBuilders.response("refreshToken")
                        .value(mockResponse.getResponse().getRefreshToken()));

        // Verify
        Mockito.verify(authenticationUseCase, Mockito.times(1))
                .refreshAccessToken(Mockito.any(AuthSideTokenRefreshCommand.class));
    }


    @Test
    void givenValidTokensInvalidateRequest_whenTokensInvalidated_thenReturnSuccessResponse() throws Exception {
        // Given
        AuthSideTokensInvalidateRequest mockTokenInvalidateRequest = new AuthSideTokensInvalidateRequestBuilder()
                .withAccessToken(adminUserToken.getAccessToken())
                .withRefreshToken(adminUserToken.getRefreshToken())
                .build();

        // When
        Mockito.doNothing()
                .when(authenticationUseCase)
                .invalidateTokens(Mockito.any(AuthSideTokensInvalidateCommand.class));

        // Then
        String endpoint = BASE_PATH.concat("/token/invalidate");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, userToken.getAccessToken(), mockTokenInvalidateRequest);

        AuthSideResponse<Void> mockResponse = new AuthSideResponseBuilder()
                .success()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isOk())
                .andExpect(AuthSideMockResultMatchersBuilders.response()
                        .doesNotExist());

        // Verify
        Mockito.verify(authenticationUseCase, Mockito.times(1))
                .invalidateTokens(Mockito.any());
    }

    @Test
    void givenInvalidTokensInvalidateRequest_whenTokensAreNotValid_thenReturnUnauthorizedError() throws Exception {
        // Given
        AuthSideTokensInvalidateRequest mockTokenInvalidateRequest = new AuthSideTokensInvalidateRequestBuilder()
                .withAccessToken("invalid")
                .withRefreshToken("invalid")
                .build();

        // When
        Mockito.doThrow(AuthSideTokenNotValidException.class)
                .when(authenticationUseCase)
                .invalidateTokens(Mockito.any(AuthSideTokensInvalidateCommand.class));

        // Then
        String endpoint = BASE_PATH.concat("/token/invalidate");
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = AuthSideMockMvcRequestBuilders
                .post(endpoint, userToken.getAccessToken(), mockTokenInvalidateRequest);

        AuthSideErrorResponse mockErrorResponse = new AuthSideErrorResponseBuilder()
                .unauthorized()
                .build();

        authSideMockMvc.perform(mockHttpServletRequestBuilder, mockErrorResponse)
                .andExpect(AuthSideMockResultMatchersBuilders.status()
                        .isUnauthorized())
                .andExpect(AuthSideMockResultMatchersBuilders.subErrors()
                        .doesNotExist());

        // Verify
        Mockito.verify(authenticationUseCase, Mockito.times(1))
                .invalidateTokens(Mockito.any(AuthSideTokensInvalidateCommand.class));
    }

}