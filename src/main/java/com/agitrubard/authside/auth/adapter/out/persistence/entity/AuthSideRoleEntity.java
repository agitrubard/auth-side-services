package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
 * The {@code AuthSideRoleEntity} class represents an entity that stores role information on the authentication side of the application.
 * It is used to manage and store roles, including their names, statuses, and associated permissions for access control and authorization.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_ROLE" table in the database.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "AUTH_SIDE_ROLE")
public class AuthSideRoleEntity extends AuthSideBaseEntity {

    /**
     * The unique identifier for the role.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * The name of the role, serving as an identifier.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * The status of the role, represented as an enumerated value (e.g., ACTIVE, INACTIVE, etc.).
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private AuthSideRoleStatus status;

    /**
     * A set of permissions associated with the role for access control and authorization purposes.
     */
    @ManyToMany
    @JoinTable(
            name = "AUTH_SIDE_ROLE_PERMISSION_RELATION",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID")
    )
    private Set<AuthSidePermissionEntity> permissions;

}
