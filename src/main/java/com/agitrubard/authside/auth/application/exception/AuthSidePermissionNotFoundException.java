package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideNotFoundException;

import java.io.Serial;
import java.util.Set;

/**
 * Exception thrown when one or more permissions are not found.
 * <p>
 * This exception is thrown when attempting to retrieve permissions that do not exist in the system.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSidePermissionNotFoundException extends AuthSideNotFoundException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and deserialization
     * to verify class compatibility. It ensures that serialized instances of this class can be deserialized without compatibility
     * issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = -9034922188971510120L;

    /**
     * Constructs a new {@code AuthSidePermissionNotFoundException} with the specified permission IDs.
     *
     * @param permissionIds The IDs of the permissions that are not found.
     */
    public AuthSidePermissionNotFoundException(final Set<String> permissionIds) {
        super(STR."PERMISSIONS ARE NOT FOUND! permissionIds:\{permissionIds}");
    }

}
