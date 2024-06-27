package com.agitrubard.authside.common.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link AuthSideSortable} provides ordering configuration for authentication-related data retrieval.
 * <p>
 * It defines a set of ordering orders ({@link Order}) to specify ordering by property and direction.
 * The orders are validated using {@link Valid}, ensuring that each order is correctly configured.
 * </p>
 * <p>
 * The {@link Order} class encapsulates ordering details:
 * <ul>
 *     <li>{@code property}: Specifies the property to sort by, annotated with {@link NotBlank} to enforce non-empty values.</li>
 *     <li>{@code direction}: Indicates the direction of ordering, required and validated with {@link NotNull}.</li>
 * </ul>
 * The {@link Order} class also provides a utility method {@link Order#of} to convert from Spring {@link Sort} to a list of {@link Order} instances.
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSideSortable {

    /**
     * The set of ordering orders specifying property and direction.
     */
    @Valid
    protected Set<Order> orders;


    /**
     * Represents a ordering order specifying property and direction.
     */
    @Getter
    @Setter
    @Builder
    public static class Order {

        /**
         * The property to order by.
         */
        @NotBlank
        public String property;

        /**
         * The direction of ordering.
         */
        @NotNull
        public Sort.Direction direction;

        /**
         * Converts a Spring {@link Sort} object to a list of {@link Order} instances.
         *
         * @param sort the Spring {@link Sort} object to convert
         * @return a set of {@link Order} instances representing the ordering orders
         */
        public static Set<Order> of(final Sort sort) {
            return sort.stream()
                    .map(order -> Order.builder()
                            .property(order.getProperty())
                            .direction(order.getDirection())
                            .build())
                    .collect(Collectors.toSet());
        }

    }

}
