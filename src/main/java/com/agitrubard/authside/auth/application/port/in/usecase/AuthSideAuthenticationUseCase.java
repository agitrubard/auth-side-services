package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommand;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;

/**
 * The {@code AuthSideAuthenticationUseCase} interface defines the use cases for handling user authentication and token management
 * in the authentication side of the application. It provides methods for authenticating users, refreshing access tokens,
 * and invalidating tokens.
 * <p>
 * Implementations of this interface are responsible for performing the necessary actions to validate user credentials,
 * generate and manage authentication tokens, and handle token invalidation as needed.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideAuthenticationUseCase {

    /**
     * Authenticates a user based on the provided login command and returns an authentication token.
     *
     * @param loginCommand The command containing user login information.
     * @return An authentication token representing a successful login.
     */
    AuthSideToken authenticate(AuthSideLoginCommand loginCommand);

    /**
     * Refreshes the access token based on the provided token refresh command.
     *
     * @param tokenRefreshCommand The command containing the refresh token for refreshing the access token.
     * @return An updated authentication token, which may include a new access token and refresh token.
     */
    AuthSideToken refreshAccessToken(AuthSideTokenRefreshCommand tokenRefreshCommand);

    /**
     * Invalidates the provided access token and/or refresh token based on the token invalidate command.
     *
     * @param tokenInvalidateCommand The command containing the tokens to be invalidated.
     */
    void invalidateTokens(AuthSideTokensInvalidateCommand tokenInvalidateCommand);

}
