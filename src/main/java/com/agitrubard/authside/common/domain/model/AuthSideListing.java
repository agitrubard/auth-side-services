package com.agitrubard.authside.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Abstract base class representing a listing of authentication side entities.
 * Provides properties for pagination and sorting criteria.
 * Subclasses should extend this class to define specific listing criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSideListing {

    /**
     * Pagination criteria for the listing.
     */
    protected AuthSidePaging pagination;

    /**
     * Sorting criteria for the listing.
     */
    protected List<AuthSideSorting> sort;

}
