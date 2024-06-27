package com.agitrubard.authside.common.adapter.in.web.response;

import com.agitrubard.authside.common.domain.model.AuthSideFiltering;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import com.agitrubard.authside.common.domain.model.AuthSideSortable;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

/**
 * The {@code AuthSidePageResponse} class is a generic response class that encapsulates paged data along with metadata, including page information,
 * total page count, total element count, ordering details, and filtering details. It is used to represent paged data in various scenarios within the application.
 * <p>
 * The class provides a builder pattern for creating instances, making it easy to initialize and populate the necessary fields.
 *
 * @param <R> The type parameter for the content list, representing the type of data being paginated.
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSidePageResponse<R> {

    /**
     * The list of elements in the current page.
     */
    private List<R> content;

    /**
     * The number of the current page.
     */
    private Integer pageNumber;

    /**
     * The size of each page, indicating the maximum number of elements per page.
     */
    private Integer pageSize;

    /**
     * The total number of pages required to display all elements in the result set.
     */
    private Integer totalPageCount;

    /**
     * The total number of elements across all pages.
     */
    private Long totalElementCount;

    /**
     * The set of ordering criteria used for ordering the elements in the result set.
     */
    private Set<AuthSideSortable.Order> orderedBy;

    /**
     * The filtering criteria used to filter the elements in the result set.
     */
    private AuthSideFiltering filteredBy;


    /**
     * The builder class for the {@code AuthSidePageResponse} class. This builder is responsible for creating instances of {@code AuthSidePageResponse}.
     *
     * <p>The method within this builder is used to convert an {@link AuthSidePage} object to an {@code AuthSidePageResponse} object.
     */
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    public static class AuthSidePageResponseBuilder<R> {

        /**
         * Converts the provided {@link AuthSidePage} object to an {@code AuthSidePageResponse} object.
         *
         * @param authSidePage The {@link AuthSidePage} object to be converted to an {@code AuthSidePageResponse} object.
         * @return An {@code AuthSidePageResponseBuilder} instance representing the converted page response.
         */
        public <M> AuthSidePageResponseBuilder<R> of(final AuthSidePage<M> authSidePage, final List<R> content) {
            return AuthSidePageResponse.<R>builder()
                    .content(content)
                    .pageNumber(authSidePage.getPageNumber())
                    .pageSize(authSidePage.getPageSize())
                    .totalPageCount(authSidePage.getTotalPageCount())
                    .totalElementCount(authSidePage.getTotalElementCount())
                    .orderedBy(authSidePage.getOrderedBy())
                    .filteredBy(authSidePage.getFilteredBy());
        }
    }

}
