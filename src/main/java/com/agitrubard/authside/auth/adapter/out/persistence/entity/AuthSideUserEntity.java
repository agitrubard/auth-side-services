package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * The {@code AuthSideUserEntity} class represents an entity that stores user information on the authentication side of the application.
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
@EqualsAndHashCode(callSuper = true)
@Table(name = "AUTH_SIDE_USER")
public class AuthSideUserEntity extends AuthSideBaseEntity {

    /**
     * The unique identifier for the user.
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * The username associated with the user for authentication and identification.
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * The email address of the user, which may serve as a means of contact and authentication.
     */
    @Column(name = "MAIL_ADDRESS")
    private String mailAddress;

    /**
     * The user's password, securely stored for authentication purposes.
     */
    @Column(name = "PASSWORD")
    private String password;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "AUTH_SIDE_USER_ROLE_RELATION",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<AuthSideRoleEntity> roles;

}
