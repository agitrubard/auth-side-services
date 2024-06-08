package com.agitrubard.authside.common.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.AuthSidePaging;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code AuthSidePagingRequest} is an abstract class that extends {@link AuthSideSortingRequest} and provides
 * pagination support for querying data in a paged manner. It encapsulates the pagination information, such as page
 * number and page size, and offers a method to convert this information into a Spring Data Pageable object for use
 * in database queries.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSidePagingRequest extends AuthSideSortingRequest {

    /**
     * The pagination configuration specifying the page number and page size for data retrieval.
     */
    @Valid
    @NotNull
    protected AuthSidePaging pagination;

}
