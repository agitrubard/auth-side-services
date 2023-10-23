package com.agitrubard.authside.auth.domain.token;

import lombok.Builder;
import lombok.Getter;

/**
 * The {@code AuthSideToken} class represents an entity that contains authentication tokens used for securing user sessions in the application.
 * Authentication tokens are essential for maintaining user sessions, and they consist of both an access token and a refresh token.
 * The access token is used for authentication and expires after a certain period, while the refresh token is used to request a new access token
 * when the original access token expires.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSideToken {

    /**
     * The access token, which is used for authenticating requests within the application.
     */
    private String accessToken;

    /**
     * The timestamp representing the expiration time of the access token, typically in milliseconds since the epoch.
     */
    private Long accessTokenExpiresAt;

    /**
     * The refresh token, which is used to obtain a new access token after the original access token has expired.
     */
    private String refreshToken;

}
