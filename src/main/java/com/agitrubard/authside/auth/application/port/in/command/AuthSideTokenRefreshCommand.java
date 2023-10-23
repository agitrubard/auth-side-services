package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideTokenRefreshCommand} class represents a command object used for refreshing an authentication token,
 * typically a refresh token. It includes a field for the refresh token, allowing the application to request a new access token
 * and possibly a new refresh token based on the provided refresh token.
 * <p>
 * This class is commonly used in the context of token refresh operations in the authentication side of the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideTokenRefreshCommand {

    /**
     * The refresh token used to request a new access token and possibly a new refresh token.
     */
    private String refreshToken;

}
