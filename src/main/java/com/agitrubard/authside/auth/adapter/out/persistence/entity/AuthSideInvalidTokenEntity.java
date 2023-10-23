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
 * The {@code AuthSideInvalidTokenEntity} class represents an entity that stores information about invalidated authentication tokens
 * on the authentication side of the application. It is used to keep a record of tokens that have been invalidated for security purposes.
 * <p>
 * This entity extends the {@link AuthSideBaseEntity} class and includes fields for an automatically generated ID and the token ID.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "AUTH_SIDE_INVALID_TOKEN")
public class AuthSideInvalidTokenEntity extends AuthSideBaseEntity {

    /**
     * The automatically generated unique identifier for the invalid token record.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique identifier associated with the invalidated token.
     */
    @Column(name = "TOKEN_ID")
    private String tokenId;

}
