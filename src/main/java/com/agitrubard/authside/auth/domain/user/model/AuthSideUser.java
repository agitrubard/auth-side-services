package com.agitrubard.authside.auth.domain.user.model;

import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@link AuthSideUser} class represents a user in the authentication system. It contains user-related information
 * such as the user's ID, username, email address, password, first name, last name, status, and associated roles.
 * This class provides methods for checking the user's activity status and generating claims for authentication tokens.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
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
    private String emailAddress;

    /**
     * The user's password (usually encrypted or hashed for security).
     */
    private Password password;

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
     * Generates a claims for authentication tokens based on user and login attempt information.
     *
     * @param loginAttempt The user's login attempt information, such as the last login date and last failed try date.
     * @return A claims for authentication tokens.
     */
    public Claims getPayload(final AuthSideLoginAttempt loginAttempt) {
        final ClaimsBuilder claimsBuilder = Jwts.claims();

        claimsBuilder.add(AuthSideTokenClaim.USER_ID.getValue(), this.id);
        claimsBuilder.add(AuthSideTokenClaim.USERNAME.getValue(), this.username);
        claimsBuilder.add(AuthSideTokenClaim.USER_ROLES.getValue(), this.getRoleNames());
        claimsBuilder.add(AuthSideTokenClaim.USER_PERMISSIONS.getValue(), this.getPermissionNames());
        claimsBuilder.add(AuthSideTokenClaim.USER_FIRST_NAME.getValue(), this.firstName);
        claimsBuilder.add(AuthSideTokenClaim.USER_LAST_NAME.getValue(), this.lastName);

        if (loginAttempt.getLastLoginDate() != null) {
            claimsBuilder.add(
                    AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue(),
                    loginAttempt.getLastLoginDate().toInstant(ZoneOffset.UTC).getEpochSecond()
            );
        }

        if (loginAttempt.getLastFailedTryDate() != null) {
            claimsBuilder.add(
                    AuthSideTokenClaim.USER_LAST_FAILED_TRY_DATE.getValue(),
                    loginAttempt.getLastFailedTryDate().toInstant(ZoneOffset.UTC).getEpochSecond()
            );
        }

        return claimsBuilder.build();
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

    /**
     * Represents a password with expiration information.
     */
    @Getter
    @Setter
    @SuperBuilder
    public static class Password extends AuthSideBaseDomainModel {
        /**
         * The actual password string.
         */
        private String value;

        /**
         * The date and time when the password expires.
         */
        private LocalDateTime expiresAt;
    }

}
