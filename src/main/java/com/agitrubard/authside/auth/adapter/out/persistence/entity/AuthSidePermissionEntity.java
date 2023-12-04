package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.permission.enums.AuthSidePermissionCategory;
import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSidePermissionEntity} class represents an entity that stores permission information on the authentication side of the application.
 * It is used to manage and store various permissions, including their names and categories, for access control and authorization purposes.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_PERMISSION" table in the database.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "AUTH_SIDE_PERMISSION")
public class AuthSidePermissionEntity extends AuthSideBaseEntity {

    /**
     * The unique identifier for the permission record.
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * The name of the permission, serving as an identifier.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * The category to which the permission belongs, represented as an enumerated value (e.g., READ, WRITE, ADMIN).
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "CATEGORY")
    private AuthSidePermissionCategory category;

}
