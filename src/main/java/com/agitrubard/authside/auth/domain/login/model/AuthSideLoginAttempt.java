package com.agitrubard.authside.auth.domain.login.model;

import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * The {@code AuthSideLoginAttempt} class represents a user's login attempt in the authentication system.
 * It extends the {@link AuthSideBaseDomainModel} and provides information about the login attempt, such as user ID,
 * login date, failed login attempts count, and the date of the last failed attempt.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@SuperBuilder
public class AuthSideLoginAttempt extends AuthSideBaseDomainModel {

    /**
     * The unique identifier of the login attempt.
     */
    private String id;

    /**
     * The user ID associated with the login attempt.
     */
    private String userId;

    /**
     * The date and time of the last successful login.
     */
    private LocalDateTime lastLoginDate;

    /**
     * The count of failed login attempts.
     */
    private Integer failedTryCount;

    /**
     * The date and time of the last failed login attempt.
     */
    private LocalDateTime lastFailedTryDate;


    /**
     * Updates the login attempt as successful, setting the last login date and resetting the failed attempts count.
     */
    public void success() {
        this.lastLoginDate = LocalDateTime.now();
        this.failedTryCount = 0;
    }

    /**
     * Updates the login attempt as failed, incrementing the failed attempts count and setting the last failed attempt date.
     */
    public void failed() {
        this.failedTryCount++;
        this.lastFailedTryDate = LocalDateTime.now();
    }


    /**
     * Checks whether the login attempt is blocked based on the number of failed attempts and time elapsed.
     *
     * @return {@code true} if the login attempt is blocked; {@code false} otherwise.
     */
    public boolean isBlocked() {
        return this.failedTryCount >= 3 && this.lastFailedTryDate.plusMinutes(5).isAfter(LocalDateTime.now());
    }

}
