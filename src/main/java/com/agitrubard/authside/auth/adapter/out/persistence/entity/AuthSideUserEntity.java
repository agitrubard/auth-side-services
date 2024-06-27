package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The {@link AuthSideUserEntity} class represents an entity that stores user information on the authentication side of the application.
 * It is used to manage and store user profiles, including user identifiers, login credentials, personal information, and associated roles for access control and authorization.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_USER" table in the database.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@Table(name = "AUTH_SIDE_USER")
public class AuthSideUserEntity extends AuthSideBaseEntity {

    /**
     * The unique identifier for the user.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The username associated with the user for authentication and identification.
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * The email address of the user, which may serve as a means of contact and authentication.
     */
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    /**
     * The password entity associated with the user for authentication and security.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PasswordEntity password;

    /**
     * The user's first name or given name.
     */
    @Column(name = "FIRST_NAME")
    private String firstName;

    /**
     * The user's last name or family name.
     */
    @Column(name = "LAST_NAME")
    private String lastName;

    /**
     * The status of the user, represented as an enumerated value (e.g., ACTIVE, INACTIVE, etc.).
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private AuthSideUserStatus status;

    /**
     * A set of roles associated with the user for access control and authorization purposes.
     */
    @ManyToMany
    @JoinTable(
            name = "AUTH_SIDE_USER_ROLE_RELATION",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<AuthSideRoleEntity> roles;


    /**
     * The {@link PasswordEntity} class represents an entity that stores user password information on the authentication side of the application.
     * It is used to manage and store user passwords, including user identifiers, password values, and expiration dates.
     * <p>
     * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_USER_PASSWORD" table in the database.
     */
    @Entity
    @Getter
    @Setter
    @Table(name = "AUTH_SIDE_USER_PASSWORD")
    public static class PasswordEntity extends AuthSideBaseEntity {

        /**
         * The unique identifier for the password.
         */
        @Id
        @Column(name = "ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        /**
         * The user identifier associated with the password.
         */
        @Column(name = "USER_ID")
        private String userId;

        /**
         * The password value for the user.
         */
        @Column(name = "VALUE")
        private String value;

        /**
         * The date and time when the password was created.
         */
        @Column(name = "EXPIRES_AT")
        private LocalDateTime expiresAt;

        /**
         * The user associated with the password.
         */
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
        private AuthSideUserEntity user;

    }

}
