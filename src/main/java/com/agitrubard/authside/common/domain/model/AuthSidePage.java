package com.agitrubard.authside.common.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * The {@code AuthSidePage} class represents a paginated response containing a list of content items, along with pageable information and optional ordering and filtering details.
 *
 * <p>It is commonly used in the Auth Side application to provide paginated results of data. The class includes the content, page number, page size, total page count, total element count, ordering criteria, and filtering details.
 *
 * @param <R> The type of content items in the page.
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSidePage<R> {

    /**
     * The list of content items for the current page.
     */
    private List<R> content;

    /**
     * The page number, starting from 1, of the current page within the paginated result set.
     */
    private Integer pageNumber;

    /**
     * The size of the page (number of content items) in the paginated response.
     */
    private Integer pageSize;

    /**
     * The total number of pages in the paginated result set.
     */
    private Integer totalPageCount;

    /**
     * The total number of elements across all pages in the result set.
     */
    private Long totalElementCount;

    /**
     * The ordering criteria applied to the content items. Can be null if no ordering is specified.
     */
    private Set<AuthSideSortable.Order> orderedBy;

    /**
     * The filtering criteria used to retrieve the content items. Can be null if no filtering is applied.
     */
    private AuthSideFiltering filteredBy;

    /**
     * Create an {@link AuthSidePage} from a Spring Data Page and content list.
     *
     * @param <E>              The type of entities in the original Spring Data Page.
     * @param <C>              The type of content items in the AuthSidePage.
     * @param pageableEntities The Spring Data Page containing entities.
     * @param content          The content items for the page.
     * @return An {@link AuthSidePage} containing the specified content and pageable information.
     */
    public static <E, C> AuthSidePage<C> of(final Page<E> pageableEntities,
                                            final List<C> content) {

        final var responseBuilder = AuthSidePage.<C>builder()
                .content(content)
                .pageNumber(pageableEntities.getNumber() + 1)
                .pageSize(content.size())
                .totalPageCount(pageableEntities.getTotalPages())
                .totalElementCount(pageableEntities.getTotalElements());

        if (pageableEntities.getSort().isSorted()) {
            responseBuilder.orderedBy(AuthSideSortable.Order.of(pageableEntities.getSort()));
        }

        return responseBuilder.build();
    }

    /**
     * Create an {@link AuthSidePage} from a filtering object, Spring Data Page, and content list.
     *
     * @param <E>              The type of entities in the original Spring Data Page.
     * @param <C>              The type of content items in the AuthSidePage.
     * @param filter           The filtering criteria used for the content.
     * @param pageableEntities The Spring Data Page containing entities.
     * @param content          The content items for the page.
     * @return An {@link AuthSidePage} containing the specified content, pageable, and filtering information.
     */
    public static <E, C> AuthSidePage<C> of(final AuthSideFiltering filter,
                                            final Page<E> pageableEntities,
                                            final List<C> content) {

        final var responseBuilder = AuthSidePage.<C>builder()
                .content(content)
                .pageNumber(pageableEntities.getNumber() + 1)
                .pageSize(content.size())
                .totalPageCount(pageableEntities.getTotalPages())
                .totalElementCount(pageableEntities.getTotalElements())
                .filteredBy(filter);

        if (pageableEntities.getSort().isSorted()) {
            responseBuilder.orderedBy(AuthSideSortable.Order.of(pageableEntities.getSort()));
        }

        return responseBuilder.build();
    }
}

