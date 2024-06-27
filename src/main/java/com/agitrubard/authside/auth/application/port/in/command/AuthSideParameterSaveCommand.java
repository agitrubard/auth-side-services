package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

/**
 * The {@link AuthSideParameterSaveCommand} class is a command object used for saving parameters related to the authentication side of the application.
 * It includes a parameter name and its corresponding definition.
 * <p>
 * This class is typically used to transfer parameter data when saving or updating parameters in the application's configuration.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSideParameterSaveCommand {

    /**
     * The name of the parameter, which serves as its unique identifier.
     */
    private String name;

    /**
     * The definition or value associated with the parameter.
     */
    private String definition;

}
