package com.agitrubard.authside.common.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.AuthSideSorting;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

/**
 * The {@code AuthSideSortingRequest} class is an abstract base class representing a request for sorting data in a specific order.
 * It includes a list of sorting criteria, each defining a property name and sort direction.
 * Subclasses that extend this class provide specific sorting configurations for various use cases.
 * <p>
 * This class provides utility methods to check whether sorting properties are accepted, whether sorting is specified,
 * and to convert sorting criteria into a Spring Data {@link Sort} object, which can be utilized in database queries.
 * <p>
 * Subclasses of this abstract class must implement the {@code isSortPropertyAccepted} method to specify whether
 * the provided sorting properties are accepted for the particular use case.
 * <p>
 * The class also provides methods to check if a property is accepted for sorting and to convert sorting criteria into a {@link Sort} object.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSideSortingRequest {

    /**
     * The list of sorting criteria, each specifying a property name and sort direction.
     */
    @Valid
    protected List<AuthSideSorting> sort;

    /**
     * Checks whether the provided sorting properties are accepted for the particular use case.
     *
     * @return {@code true} if the sorting properties are accepted, {@code false} otherwise.
     */
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    public abstract boolean isSortPropertyAccepted();

    /**
     * Checks whether the provided property is accepted for sorting based on a set of accepted property names.
     *
     * @param acceptedProperties The set of property names accepted for sorting.
     * @return {@code true} if the property is accepted for sorting, {@code false} otherwise.
     */
    protected boolean isPropertyAccepted(final Set<String> acceptedProperties) {

        if (CollectionUtils.isEmpty(this.sort)) {
            return true;
        }

        for (AuthSideSorting sorting : this.sort) {
            if (sorting.getProperty() == null || sorting.getDirection() == null) {
                return true;
            }
        }

        return sort.stream()
                .map(AuthSideSorting::getProperty)
                .allMatch(acceptedProperties::contains);
    }

}
