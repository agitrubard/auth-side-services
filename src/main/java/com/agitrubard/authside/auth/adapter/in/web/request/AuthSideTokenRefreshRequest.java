package com.agitrubard.authside.auth.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideTokenRefreshRequest} class represents a data transfer object (DTO)
 * used for requesting the refresh of an authentication token on the authentication side.
 * It contains the refresh token provided for the token refresh process.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideTokenRefreshRequest {

    /**
     * The refresh token used to request a token refresh.
     */
    @NotBlank
    private String refreshToken;

}
