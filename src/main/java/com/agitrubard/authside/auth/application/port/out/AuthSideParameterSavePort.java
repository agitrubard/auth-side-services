package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;

import java.util.Set;

/**
 * The {@code AuthSideParameterSavePort} interface defines methods for saving authentication parameters.
 * Implementations of this interface provide the ability to save sets of authentication parameters.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideParameterSavePort {

    /**
     * Saves a set of authentication parameters.
     *
     * @param parameters A {@link Set} of {@link AuthSideParameter} objects to be saved.
     */
    void saveAll(Set<AuthSideParameter> parameters);

}
