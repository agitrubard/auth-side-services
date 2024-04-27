package com.agitrubard.authside.common.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * This class represents sorting criteria for authentication side operations.
 * It provides methods to create instances of {@code AuthSideSorting} and convert
 * Spring Data's {@link Sort} objects to lists of {@code AuthSideSorting}.
 *
 * <p>{@code AuthSideSorting} instances are typically used in conjunction with
 * repository methods to specify sorting behavior for query results.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
@Builder
public class AuthSideSorting {

    /**
     * The property to sort by.
     */
    @NotNull
    public String property;

    /**
     * The direction of sorting.
     */
    @NotNull
    public Sort.Direction direction;

    /**
     * Creates a list of {@code AuthSideSorting} from a Spring Data {@link Sort} object.
     *
     * @param sort the Spring Data Sort object to convert
     * @return a list of {@code AuthSideSorting} representing the sorting criteria
     */
    public static List<AuthSideSorting> of(final Sort sort) {
        return sort.stream()
                .map(order -> AuthSideSorting.builder()
                        .property(order.getProperty())
                        .direction(order.getDirection())
                        .build())
                .toList();
    }

}
