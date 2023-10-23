package com.agitrubard.authside.auth.domain.role.enums;

/**
 * The {@code AuthSideRoleStatus} enum represents the status of a user role in the authentication system. User roles
 * can have one of the following statuses: ACTIVE, PASSIVE, or DELETED.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public enum AuthSideRoleStatus {

    /**
     * Indicates that the role is currently active and can be assigned to users.
     */
    ACTIVE,

    /**
     * Suggests that the role is temporarily inactive but can be reactivated in the future.
     */
    PASSIVE,

    /**
     * Implies that the role has been permanently deleted and should not be used.
     */
    DELETED

}
