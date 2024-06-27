package com.agitrubard.authside.common.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import com.agitrubard.authside.common.domain.model.AuthSideSortable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * {@link AuthSidePagingRequest} is an abstract base class representing a paging request
 * configuration for authentication-related data retrieval.
 * <p>
 * It provides pageable settings through the {@link AuthSidePageable} object, ensuring
 * that page number and size are specified for data retrieval operations.
 * </p>
 * <p>
 * This class also includes methods to validate ordering properties for the specific use case,
 * ensuring that ordering options provided are accepted and properly configured.
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSidePagingRequest {

    /**
     * The pageable configuration specifying the page number and page size for data retrieval.
     */
    @Valid
    @NotNull
    protected AuthSidePageable pageable;

    /**
     * Checks whether the provided ordering properties are accepted for the particular use case.
     *
     * @return {@code true} if the ordering properties are accepted, {@code false} otherwise.
     */
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    public abstract boolean isSortPropertyAccepted();

    /**
     * Checks whether the provided properties for ordering are accepted based on the specified criteria.
     *
     * @param acceptedProperties The set of accepted ordering properties.
     * @return {@code true} if the ordering properties are accepted, {@code false} otherwise.
     */
    @SuppressWarnings("all")
    protected boolean isPropertyAccepted(final Set<String> acceptedProperties) {

        if (this.pageable == null || CollectionUtils.isEmpty(this.pageable.getOrders())) {
            return true;
        }

        for (AuthSideSortable.Order order : this.pageable.getOrders()) {
            if (StringUtils.isBlank(order.getProperty()) || order.getDirection() == null) {
                return true;
            }
        }

        final Set<AuthSideSortable.Order> orders = this.pageable.getOrders();
        if (CollectionUtils.isEmpty(orders)) {
            return true;
        }

        final boolean isAnyDirectionEmpty = orders.stream().anyMatch(order -> order.getDirection() == null);
        final boolean isAnyPropertyEmpty = orders.stream().anyMatch(order -> order.getProperty() == null);
        if (isAnyDirectionEmpty || isAnyPropertyEmpty) {
            return true;
        }

        return orders.stream()
                .map(AuthSideSortable.Order::getProperty)
                .allMatch(acceptedProperties::contains);
    }

}
