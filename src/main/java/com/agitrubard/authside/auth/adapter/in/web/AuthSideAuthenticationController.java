package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideLoginRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokenRefreshRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokensInvalidateRequest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideTokenResponse;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenInvalidateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideAuthenticationUseCase;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.mapper.AuthSideLoginRequestToLoginCommandMapper;
import com.agitrubard.authside.auth.mapper.AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper;
import com.agitrubard.authside.auth.mapper.AuthSideTokenRefreshRequestToTokenRefreshCommandMapper;
import com.agitrubard.authside.auth.mapper.AuthSideTokenToTokenResponseMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code AuthSideAuthenticationController} class is a Spring MVC controller responsible for handling authentication-related
 * requests on the authentication side of the application. It provides endpoints for authenticating users, refreshing access tokens,
 * and invalidating tokens.
 * <p>
 * This controller uses a set of mappers to convert request DTOs to command objects and to map authentication tokens to responses.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
class AuthSideAuthenticationController {

    private final AuthSideAuthenticationUseCase authenticationUseCase;

    private static final AuthSideLoginRequestToLoginCommandMapper LOGIN_REQUEST_TO_LOGIN_COMMAND_MAPPER = AuthSideLoginRequestToLoginCommandMapper.initialize();
    private static final AuthSideTokenRefreshRequestToTokenRefreshCommandMapper TOKEN_REFRESH_REQUEST_TO_TOKEN_REFRESH_COMMAND_MAPPER = AuthSideTokenRefreshRequestToTokenRefreshCommandMapper.initialize();
    private static final AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper TOKEN_INVALIDATE_REQUEST_TO_TOKEN_INVALIDATE_COMMAND_MAPPER = AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper.initialize();
    private static final AuthSideTokenToTokenResponseMapper TOKEN_TO_TOKEN_RESPONSE_MAPPER = AuthSideTokenToTokenResponseMapper.initialize();

    /**
     * Endpoint for user authentication. Receives a login request, converts it to a command, and returns an authentication token response.
     *
     * @param loginRequest The authentication request containing the username and password.
     * @return An {@code AuthSideResponse} containing the authentication token response.
     */
    @PostMapping("/token")
    public AuthSideResponse<AuthSideTokenResponse> authenticate(
            @RequestBody @Valid final AuthSideLoginRequest loginRequest) {

        final AuthSideLoginCommand loginCommand = LOGIN_REQUEST_TO_LOGIN_COMMAND_MAPPER.map(loginRequest);
        final AuthSideToken token = authenticationUseCase.authenticate(loginCommand);

        final AuthSideTokenResponse tokenResponse = TOKEN_TO_TOKEN_RESPONSE_MAPPER.map(token);
        return AuthSideResponse.successOf(tokenResponse);
    }

    /**
     * Endpoint for refreshing an access token. Receives a refresh request, converts it to a command, and returns a refreshed token response.
     *
     * @param refreshRequest The token refresh request containing the refresh token.
     * @return An {@code AuthSideResponse} containing the refreshed token response.
     */
    @PostMapping("/token/refresh")
    public AuthSideResponse<AuthSideTokenResponse> refreshToken(
            @RequestBody @Valid final AuthSideTokenRefreshRequest refreshRequest) {

        final AuthSideTokenRefreshCommand tokenRefreshCommand = TOKEN_REFRESH_REQUEST_TO_TOKEN_REFRESH_COMMAND_MAPPER
                .map(refreshRequest);
        final AuthSideToken token = authenticationUseCase.refreshAccessToken(tokenRefreshCommand);
        final AuthSideTokenResponse tokenResponse = TOKEN_TO_TOKEN_RESPONSE_MAPPER.map(token);
        return AuthSideResponse.successOf(tokenResponse);
    }

    /**
     * Endpoint for invalidating access tokens. Receives a token invalidation request and invalidates the provided tokens.
     *
     * @param invalidateRequest The token invalidation request containing the access and refresh tokens to invalidate.
     * @return An {@code AuthSideResponse} indicating the success of token invalidation.
     */
    @PostMapping("/token/invalidate")
    public AuthSideResponse<Void> invalidateTokens(
            @RequestBody @Valid final AuthSideTokensInvalidateRequest invalidateRequest) {

        final AuthSideTokenInvalidateCommand tokenInvalidateCommand = TOKEN_INVALIDATE_REQUEST_TO_TOKEN_INVALIDATE_COMMAND_MAPPER
                .map(invalidateRequest);
        authenticationUseCase.invalidateTokens(tokenInvalidateCommand);
        return AuthSideResponse.SUCCESS;
    }

}
