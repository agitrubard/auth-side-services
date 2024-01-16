package com.agitrubard.authside.auth.domain.token.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The {@code AuthSideTokenClaim} enum defines a set of standard claims used in JSON Web Tokens (JWTs) within the
 * authentication system. Each claim is represented by a key and provides specific information about the token.
 * <p>
 * These claims are used for various purposes, such as identifying the token, specifying its type, and including user-related
 * information like user ID, username, roles, and permissions.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum AuthSideTokenClaim {

    /**
     * The "typ" claim represents the type of the token.
     */
    TYPE("typ"),

    /**
     * The "alg" claim represents the algorithm used to sign the token.
     */
    ALGORITHM("alg"),

    /**
     * The "userId" claim represents the unique identifier of the user associated with the token.
     */
    USER_ID("userId"),

    /**
     * The "username" claim represents the username of the user associated with the token.
     */
    USERNAME("username"),

    /**
     * The "userRoles" claim represents the roles assigned to the user.
     */
    USER_ROLES("userRoles"),

    /**
     * The "userPermissions" claim represents the permissions assigned to the user.
     */
    USER_PERMISSIONS("userPermissions"),

    /**
     * The "userFirstName" claim represents the first name of the user.
     */
    USER_FIRST_NAME("userFirstName"),

    /**
     * The "userLastName" claim represents the last name of the user.
     */
    USER_LAST_NAME("userLastName"),

    /**
     * The "userLastLoginDate" claim represents the date and time of the user's last login.
     */
    USER_LAST_LOGIN_DATE("userLastLoginDate"),

    /**
     * The "userLastFailedTryDate" claim represents the date and time of the user's last failed login attempt.
     */
    USER_LAST_FAILED_TRY_DATE("userLastFailedTryDate");

    /**
     * The unique name (key) of the claim.
     */
    private final String value;

}
