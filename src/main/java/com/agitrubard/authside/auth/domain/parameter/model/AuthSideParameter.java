package com.agitrubard.authside.auth.domain.parameter.model;

import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * The {@code AuthSideParameter} class represents a parameter in the authentication system.
 * It extends the {@link AuthSideBaseDomainModel} and provides information about parameters, including an ID, name,
 * and a definition.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@SuperBuilder
public class AuthSideParameter extends AuthSideBaseDomainModel {

    /**
     * The unique identifier of the parameter.
     */
    private Long id;

    /**
     * The name of the parameter.
     */
    private String name;

    /**
     * The definition or value associated with the parameter.
     */
    private String definition;

}
