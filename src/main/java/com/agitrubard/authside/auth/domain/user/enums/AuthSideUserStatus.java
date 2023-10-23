package com.agitrubard.authside.auth.domain.user.enums;

/**
 * Enumeration representing the various status options for user accounts in the AuthSide system.
 * These statuses define the state of a user account, such as whether it's active, inactive, locked, or deleted.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
public enum AuthSideUserStatus {

    /**
     * The user account is active and in good standing.
     */
    ACTIVE,

    /**
     * The user account is currently inactive.
     */
    INACTIVE,

    /**
     * The user account is not yet verified or validated.
     */
    NOT_VERIFIED,

    /**
     * The user account is locked and cannot be accessed.
     */
    LOCKED,

    /**
     * The user account has been deleted or marked for deletion.
     */
    DELETED

}
