package com.agitrubard.authside.common.application.exception;

import java.io.Serial;

/**
 * The {@code AuthSideKeyGenerationException} is a specific type of {@link AuthSideProcessException} that is thrown when there is an issue with generating keys or cryptographic material within the Auth Side application. This exception is used to indicate failures in the key generation process.
 *
 * <p>This exception typically wraps another exception, providing additional context or details about the failure. The primary message associated with this exception is "KEY COULD NOT BE GENERATED!"
 */
public final class AuthSideKeyGenerationException extends AuthSideProcessException {

    /**
     * The {@code serialVersionUID} is a unique identifier for a serializable class used during object serialization and
     * deserialization to verify class compatibility. It ensures that serialized instances of this class can be
     * deserialized without compatibility issues, even if the class structure changes between software versions.
     */
    @Serial
    private static final long serialVersionUID = -7819949872057719935L;

    /**
     * Constructs a new AuthSideKeyGenerationException with the specified cause exception.
     *
     * @param exception The root cause exception that led to the key generation failure.
     */
    public AuthSideKeyGenerationException(Exception exception) {
        super("KEY COULD NOT BE GENERATED!", exception);
    }

}
