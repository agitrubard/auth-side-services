package com.agitrubard.authside.auth.application.port.in.usecase;

import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

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
     * Generates an authentication side token based on the provided claims.
     *
     * @param claims The claims to be included in the generated token.
     * @return The generated authentication side token.
     */
    AuthSideToken generate(Map<String, Object> claims);

    /**
     * Generates an authentication side token with an additional refresh token,
     * based on the provided claims.
     *
     * @param claims       The claims to be included in the generated token.
     * @param refreshToken The refresh token to be associated with the generated token.
     * @return The generated authentication side token with a refresh token.
     */
    AuthSideToken generate(Map<String, Object> claims, String refreshToken);

    /**
     * Verifies and validates the authenticity and integrity of the provided token.
     *
     * @param token The token to be verified and validated.
     */
    void verifyAndValidate(String token);

    /**
     * Retrieves the payload (claims) from the provided token.
     *
     * @param token The token from which to extract the payload.
     * @return The claims (payload) extracted from the token.
     */
    Claims getPayload(String token);

    /**
     * Retrieves the complete set of claims from the provided token as a JSON Web Signature (JWS) object.
     *
     * @param token The token from which to extract the claims.
     * @return The JWS object containing the claims.
     */
    Jws<Claims> getClaims(String token);

}
