package com.agitrubard.authside.auth.application.config;

import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.common.util.AuthSideBeanScope;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * The {@link AuthSideIdentity} class represents an authentication identity that provides access to user-related information
 * during the course of an HTTP request. It is designed to be scoped at the request level and is used for extracting
 * user-specific details, such as the user's unique identifier (user ID), from a JSON Web Token (JWT).
 * <p>
 * This class is typically used in the context of a web application to obtain user information stored within the JWT,
 * which may include data like the user's ID, roles, or other attributes.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@Scope(value = AuthSideBeanScope.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthSideIdentity {

    /**
     * Retrieves the user's unique identifier (user ID) from the JSON Web Token (JWT) stored in the current request's security context.
     *
     * @return The user's unique identifier as a string.
     */
    public String getUserId() {
        return this.getJwt().getClaim(AuthSideTokenClaim.USER_ID.getValue());
    }

    /**
     * Gets the JWT (JSON Web Token) associated with the current request's security context.
     *
     * @return The JWT representing the user's authentication information.
     */
    private Jwt getJwt() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
