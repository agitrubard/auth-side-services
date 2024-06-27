package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The {@link AuthSideLoginAttemptEntity} class represents an entity that stores information about user login attempts
 * on the authentication side of the application. It is used to track login attempts, including successful and failed ones,
 * and includes fields such as the user ID, last login date, failed try count, and the date of the last failed attempt.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_USER_LOGIN_ATTEMPT" table in the database.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@Table(name = "AUTH_SIDE_USER_LOGIN_ATTEMPT")
public class AuthSideLoginAttemptEntity extends AuthSideBaseEntity {

    /**
     * The unique identifier for the login attempt record.
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * The user ID associated with the login attempt.
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * The date and time of the last successful login.
     */
    @Column(name = "LAST_LOGIN_DATE")
    private LocalDateTime lastLoginDate;

    /**
     * The count of failed login attempts.
     */
    @Column(name = "FAILED_TRY_COUNT")
    private Integer failedTryCount;

    /**
     * The date and time of the last failed login attempt.
     */
    @Column(name = "LAST_FAILED_TRY_DATE")
    private LocalDateTime lastFailedTryDate;

}
