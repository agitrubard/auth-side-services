package com.agitrubard.authside.common.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The {@code AuthSideBaseEntity} is an abstract base class for all entities in the AuthSide system. It includes fields for audit information such as creation and update timestamps, as well as user information.
 *
 * <p>Entities that extend this class will inherit the audit fields and the logic to set them automatically during the lifecycle of the entity.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AuthSideBaseEntity {

    /**
     * The username of the user who created the entity.
     */
    @Column(name = "CREATED_BY")
    protected String createdBy;

    /**
     * The timestamp when the entity was created.
     */
    @Column(name = "CREATED_AT")
    protected LocalDateTime createdAt;

    /**
     * Automatically sets the {@code createdBy} and {@code createdAt} fields before an entity is persisted.
     */
    @PrePersist
    public void prePersist() {
        this.createdBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(AuthSideTokenClaim.USERNAME.getValue()).toString())
                .orElse("AUTH_SIDE");
        this.createdAt = LocalDateTime.now();
    }


    /**
     * The username of the user who last updated the entity.
     */
    @Column(name = "UPDATED_BY")
    protected String updatedBy;

    /**
     * The timestamp when the entity was last updated.
     */
    @Column(name = "UPDATED_AT")
    protected LocalDateTime updatedAt;

    /**
     * Automatically sets the {@code updatedBy} and {@code updatedAt} fields before an entity is updated.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(user -> !"anonymousUser".equals(user))
                .map(Jwt.class::cast)
                .map(jwt -> jwt.getClaim(AuthSideTokenClaim.USERNAME.getValue()).toString())
                .orElse("AUTH_SIDE");
        this.updatedAt = LocalDateTime.now();
    }

}
