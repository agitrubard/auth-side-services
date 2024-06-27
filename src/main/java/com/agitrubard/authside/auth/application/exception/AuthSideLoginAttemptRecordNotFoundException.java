package com.agitrubard.authside.auth.application.exception;

import com.agitrubard.authside.common.application.exception.AuthSideNotFoundException;

import java.io.Serial;

/**
 * The {@link AuthSideLoginAttemptRecordNotFoundException} class is an exception that is thrown when an attempt to find a
 * user login attempt record by user ID in the authentication side of the application fails because the record is not found.
 * This exception extends {@link AuthSideNotFoundException}, indicating that the requested record is not present.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public class AuthSideLoginAttemptRecordNotFoundException extends AuthSideNotFoundException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = 5360206400050299920L;

    /**
     * Constructs a new {@link AuthSideLoginAttemptRecordNotFoundException} with the provided user ID.
     *
     * @param userId The user ID for which the login attempt record is not found.
     */
    public AuthSideLoginAttemptRecordNotFoundException(String userId) {
        super(STR."USER LOGIN ATTEMPT RECORD IS NOT FOUND! userId: \{userId}");
    }

}
