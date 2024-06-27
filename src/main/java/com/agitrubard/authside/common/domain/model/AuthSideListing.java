package com.agitrubard.authside.common.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * Abstract base class representing a listing of authentication side entities.
 * Provides properties for pageable and ordering criteria.
 * Subclasses should extend this class to define specific listing criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSideListing {

    /**
     * Pageable criteria for the listing.
     */
    protected AuthSidePageable pageable;

    /**
     * Converts authentication side listing criteria into a Spring Data's Pageable object.
     *
     * @return A Pageable object representing the pageable and ordering criteria.
     */
    public Pageable toPageable() {

        if (this.pageable == null) {
            return Pageable.unpaged();
        }

        final Set<AuthSideSortable.Order> orders = this.pageable.getOrders();
        if (CollectionUtils.isNotEmpty(orders)) {
            return PageRequest.of(
                    this.pageable.getPageNumber() - 1,
                    this.pageable.getPageSize(),
                    Sort.by(orders.stream()
                            .map(sortable -> Sort.Order.by(sortable.getProperty()).with(sortable.getDirection()))
                            .toList())
            );
        }

        return PageRequest.of(
                this.pageable.getPageNumber() - 1,
                this.pageable.getPageSize()
        );

    }

}
