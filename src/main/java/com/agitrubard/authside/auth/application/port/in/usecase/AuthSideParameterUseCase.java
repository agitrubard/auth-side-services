package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideParameterSaveCommand;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;

import java.util.Set;

/**
 * The {@code AuthSideParameterUseCase} interface defines the use cases for managing authentication-related parameters in the
 * authentication side of the application. It provides methods for retrieving parameters with names that have a specific
 * prefix and for saving a collection of parameter save commands.
 * <p>
 * Implementations of this interface are responsible for handling authentication configuration parameters that are used
 * to configure various aspects of the authentication process.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideParameterUseCase {

    /**
     * Retrieves a set of authentication parameters with names that have the specified prefix.
     *
     * @param prefixOfName The prefix used to filter and retrieve authentication parameters.
     * @return A set of authentication parameters that match the specified prefix.
     */
    Set<AuthSideParameter> getAllByPrefixOfName(String prefixOfName);

    /**
     * Saves a collection of authentication parameters using the provided save commands.
     *
     * @param saveCommands A set of save commands, each containing the name and definition of a parameter to be saved.
     */
    void saveAll(Set<AuthSideParameterSaveCommand> saveCommands);

}
