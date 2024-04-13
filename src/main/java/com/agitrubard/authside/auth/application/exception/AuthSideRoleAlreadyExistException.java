package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideAlreadyException;

import java.io.Serial;

/**
 * Exception thrown when a user role already exists.
 * <p>
 * This exception is thrown when attempting to create a user role with a name that already exists in the system.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideRoleAlreadyExistException extends AuthSideAlreadyException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and deserialization
     * to verify class compatibility. It ensures that serialized instances of this class can be deserialized without compatibility
     * issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = 279749580033343201L;

    /**
     * Constructs a new {@code AuthSideRoleAlreadyExistException} with the specified role name.
     *
     * @param name The name of the role that already exists.
     */
    public AuthSideRoleAlreadyExistException(final String name) {
        super(STR."ROLE ALREADY EXIST! name:\{name}");
    }

}
