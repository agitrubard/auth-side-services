package com.agitrubard.authside.common.application.port.out;

import com.agitrubard.authside.common.domain.model.AuthSidePaging;
import com.agitrubard.authside.common.domain.model.AuthSideSorting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Port interface for converting authentication side listing criteria to Spring Data's Pageable object.
 * Implementations of this interface should provide functionality to convert pagination and sorting criteria
 * into a Pageable object.
 * <p>
 * This interface provides a default implementation for converting AuthSidePaging and AuthSideSorting objects
 * into a Pageable object.
 * </p>
 * <p>
 * Subclasses can override the default implementation if additional customization is needed.
 * </p>
 * <p>
 * Note: This interface assumes the usage of Spring Data for pagination and sorting.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 *     Pageable pageable = this.toPageable(pagination, sort);
 * }</pre>
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 * @see Pageable
 * @see AuthSidePaging
 * @see AuthSideSorting
 * @see PageRequest
 * @see Sort
 */
public interface AuthSideListPort {

    /**
     * Converts authentication side listing criteria into a Spring Data's Pageable object.
     *
     * @param pagination The pagination criteria.
     * @param sort       The sorting criteria.
     * @return A Pageable object representing the pagination and sorting criteria.
     */
    default Pageable toPageable(AuthSidePaging pagination, List<AuthSideSorting> sort) {

        if (sort != null) {
            return PageRequest.of(
                    pagination.getPage(),
                    pagination.getPageSize(),
                    Sort.by(sort.stream()
                            .map(sortable -> Sort.Order.by(sortable.getProperty()).with(sortable.getDirection()))
                            .toList())
            );
        }

        return PageRequest.of(
                pagination.getPage(),
                pagination.getPageSize()
        );

    }

}
