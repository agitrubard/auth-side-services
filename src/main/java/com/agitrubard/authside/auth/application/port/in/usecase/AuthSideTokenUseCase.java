package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * The {@code AuthSideTokenUseCase} interface defines the use cases for working with authentication tokens in the authentication
 * side of the application. It provides methods for generating tokens, verifying and validating tokens, and retrieving token claims.
 * <p>
 * Implementations of this interface are responsible for handling the creation, validation, and management of authentication tokens
 * used within the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideTokenUseCase {

    /**
     * Generates an authentication token with the provided claims.
     *
     * @param claims The claims to be included in the token.
     * @return An authentication token containing the specified claims.
     */
    AuthSideToken generate(Map<String, Object> claims);

    /**
     * Generates an authentication token with the provided claims and associates it with a refresh token.
     *
     * @param claims       The claims to be included in the token.
     * @param refreshToken The associated refresh token.
     * @return An authentication token containing the specified claims and associated with the given refresh token.
     */
    AuthSideToken generate(Map<String, Object> claims, String refreshToken);

    /**
     * Verifies and validates the provided JWT (JSON Web Token) to ensure its authenticity and integrity.
     *
     * @param jwt The JWT to verify and validate.
     */
    void verifyAndValidate(String jwt);

    /**
     * Retrieves the claims from the provided JWT (JSON Web Token).
     *
     * @param jwt The JWT from which to retrieve the claims.
     * @return The claims contained within the JWT.
     */
    Claims getClaims(String jwt);

}
