package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideLoginCommand} class represents a command object for handling user login requests in the authentication module.
 * It contains the user's username and password, which are used for authentication.
 * <p>
 * This class is typically used to transfer login request data between the controller and the authentication use case.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideLoginCommand {

    /**
     * The user's username for authentication.
     */
    private String username;

    /**
     * The user's password for authentication.
     */
    private String password;

}
