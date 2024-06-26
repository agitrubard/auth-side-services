package com.agitrubard.authside.common.domain.model;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * A class representing paging information for paginated data retrieval.
 * <p>
 * This class defines parameters for specifying the page number and page size when querying paginated data.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSidePageable {

    /**
     * The page number to retrieve, which must be a positive integer (1 or greater).
     */
    @Positive
    public int pageNumber;

    /**
     * The page size, indicating the number of items per page. It should be a positive integer.
     * Typically, the page size is set to a specific value (e.g., 10) to limit the number of items displayed per page.
     */
    @Positive
    public int pageSize;

}
