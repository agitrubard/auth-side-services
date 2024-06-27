package com.agitrubard.authside.common.domain.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * The {@link AuthSideBaseDomainModel} is an abstract base class representing common attributes for domain models within the Auth Side application. Domain models typically represent core business entities and may have information about their creation and modification history.
 *
 * <p>This class includes attributes to track the creator, creation timestamp, last updater, and update timestamp for domain entities. Subclasses can extend this base class to inherit these common attributes.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@SuperBuilder
public abstract class AuthSideBaseDomainModel {

    /**
     * The username or identifier of the user who created this domain entity.
     */
    protected String createdBy;

    /**
     * The timestamp when this domain entity was created.
     */
    protected LocalDateTime createdAt;

    /**
     * The username or identifier of the user who last updated this domain entity.
     */
    protected String updatedBy;

    /**
     * The timestamp when this domain entity was last updated.
     */
    protected LocalDateTime updatedAt;

}
