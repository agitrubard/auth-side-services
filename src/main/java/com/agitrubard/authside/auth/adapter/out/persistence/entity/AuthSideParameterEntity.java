package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.common.adapter.out.persistence.entity.AuthSideBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSideParameterEntity} class represents an entity that stores parameter information on the authentication side of the application.
 * It is used to manage and store various parameters and their definitions, such as configuration settings.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and is mapped to the "AUTH_SIDE_PARAMETER" table in the database.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "AUTH_SIDE_PARAMETER")
public class AuthSideParameterEntity extends AuthSideBaseEntity {

    /**
     * The automatically generated unique identifier for the parameter record.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the parameter, which serves as an identifier.
     */
    @Column(name = "NAME")
    private String name;

    /**
     * The definition or description of the parameter, providing information about its purpose or configuration details.
     */
    @Column(name = "DEFINITION")
    private String definition;

}
