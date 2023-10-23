package com.agitrubard.authside.auth.domain.user.model;

import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code AuthSideUser} class represents a user in the authentication system. It contains user-related information
 * such as the user's ID, username, email address, password, first name, last name, status, and associated roles.
 * This class provides methods for checking the user's activity status and generating claims for authentication tokens.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@SuperBuilder
public class AuthSideUser extends AuthSideBaseDomainModel {

    /**
     * The unique identifier for the user.
     */
    private String id;

    /**
     * The username associated with the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String mailAddress;

    /**
     * The user's password (usually encrypted or hashed for security).
     */
    private String password;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The status of the user account, indicating whether it's active, inactive, locked, not verified, or deleted.
     */
    private AuthSideUserStatus status;

    /**
     * The roles associated with the user, which grant certain permissions and access privileges.
     */
    private Set<AuthSideRole> roles;

    /**
     * Checks whether the user account is active.
     *
     * @return {@code true} if the user account is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return AuthSideUserStatus.ACTIVE.equals(this.status);
    }


    /**
     * Generates a map of claims for authentication tokens based on user and login attempt information.
     *
     * @param loginAttempt The user's login attempt information, such as the last login date and last failed try date.
     * @return A map of claims for authentication tokens.
     */
    public Map<String, Object> getClaims(final AuthSideLoginAttempt loginAttempt) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put(AuthSideTokenClaim.USER_ID.getValue(), this.id);
        claims.put(AuthSideTokenClaim.USERNAME.getValue(), this.username);
        claims.put(AuthSideTokenClaim.USER_ROLES.getValue(), this.getRoleNames());
        claims.put(AuthSideTokenClaim.USER_PERMISSIONS.getValue(), this.getPermissionNames());
        claims.put(AuthSideTokenClaim.USER_FIRST_NAME.getValue(), this.firstName);
        claims.put(AuthSideTokenClaim.USER_LAST_NAME.getValue(), this.lastName);

        claims.put(
                AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue(),
                loginAttempt.getLastLoginDate().toInstant(ZoneOffset.UTC).getEpochSecond()
        );

        if (loginAttempt.getLastFailedTryDate() != null) {
            claims.put(
                    AuthSideTokenClaim.USER_LAST_FAILED_TRY_DATE.getValue(),
                    loginAttempt.getLastFailedTryDate().toInstant(ZoneOffset.UTC).getEpochSecond()
            );
        }

        return claims;
    }

    /**
     * Retrieves the names of roles associated with the user.
     *
     * @return A set of role names.
     */
    private Set<String> getRoleNames() {
        return this.roles.stream()
                .map(AuthSideRole::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the names of permissions associated with the user's roles.
     *
     * @return A set of permission names.
     */
    private Set<String> getPermissionNames() {
        return this.roles.stream()
                .map(AuthSideRole::getPermissions)
                .flatMap(Set::stream)
                .map(AuthSidePermission::getName)
                .collect(Collectors.toSet());
    }

}
