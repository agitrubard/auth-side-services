package com.agitrubard.authside.auth.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideLoginRequest} class represents a data transfer object (DTO)
 * used for authenticating a user's login on the authentication side.
 * It contains the user's username and password.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideLoginRequest {

    /**
     * The username provided for authentication.
     */
    @NotBlank
    private String username;

    /**
     * The password provided for authentication.
     */
    @NotBlank
    private String password;

}
