package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;

import java.util.Set;

/**
 * The {@link AuthSideParameterReadPort} interface defines methods for retrieving authentication parameters
 * with names that match a given prefix in the authentication side of the application. Implementations of this interface
 * provide the ability to search for parameters based on a specified prefix.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideParameterReadPort {

    /**
     * Finds and retrieves all authentication parameters whose names start with the specified prefix.
     *
     * @param prefixOfName The prefix to search for in parameter names.
     * @return A {@link Set} of {@link AuthSideParameter} objects that match the specified prefix.
     */
    Set<AuthSideParameter> findAllByPrefixOfName(String prefixOfName);

}
