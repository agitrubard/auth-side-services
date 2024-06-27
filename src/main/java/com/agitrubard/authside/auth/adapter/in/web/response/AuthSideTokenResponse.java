package com.agitrubard.authside.auth.adapter.in.web.response;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@link AuthSideTokenResponse} class represents a data transfer object (DTO)
 * used for encapsulating the response containing authentication tokens on the authentication side.
 * It includes the access token, access token expiration time, and the refresh token.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideTokenResponse {

    /**
     * The access token provided as part of the authentication response.
     */
    private String accessToken;

    /**
     * The expiration time (in milliseconds since epoch) of the access token.
     */
    private Long accessTokenExpiresAt;

    /**
     * The refresh token provided as part of the authentication response.
     */
    private String refreshToken;

}
