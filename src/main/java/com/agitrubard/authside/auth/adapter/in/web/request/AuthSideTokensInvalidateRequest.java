package com.agitrubard.authside.auth.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@link AuthSideTokensInvalidateRequest} class represents a data transfer object (DTO)
 * used for invalidating authentication tokens on the authentication side.
 * It contains both an access token and a refresh token that are intended to be invalidated.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideTokensInvalidateRequest {

    /**
     * The access token to be invalidated.
     */
    @NotBlank
    private String accessToken;

    /**
     * The refresh token to be invalidated.
     */
    @NotBlank
    private String refreshToken;

}
