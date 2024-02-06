package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideTokenInvalidateCommand} class is a command object used for invalidating authentication tokens.
 * It includes fields for both the access token and the refresh token, allowing the application to invalidate one or both of these tokens as needed.
 * <p>
 * This class is typically used in the context of token invalidation operations in the authentication side of the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideTokensInvalidateCommand {

    /**
     * The access token to be invalidated.
     */
    private String accessToken;

    /**
     * The refresh token to be invalidated.
     */
    private String refreshToken;

}
