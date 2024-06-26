package com.agitrubard.authside.common.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    protected AuthSidePageable pagination;

    /**
     * Sorting criteria for the listing.
     */
    protected List<AuthSideSorting> sort;

    /**
     * Converts authentication side listing criteria into a Spring Data's Pageable object.
     *
     * @return A Pageable object representing the pagination and sorting criteria.
     */
    public Pageable toPageable() {

        if (this.sort != null) {
            return PageRequest.of(
                    this.pagination.getPageNumber() - 1,
                    this.pagination.getPageSize(),
                    Sort.by(this.sort.stream()
                            .map(sortable -> Sort.Order.by(sortable.getProperty()).with(sortable.getDirection()))
                            .toList())
            );
        }

        return PageRequest.of(
                this.pagination.getPageNumber() - 1,
                this.pagination.getPageSize()
        );

    }

}
