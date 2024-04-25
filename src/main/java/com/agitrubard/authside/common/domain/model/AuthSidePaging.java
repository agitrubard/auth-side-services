package com.agitrubard.authside.common.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

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
public class AuthSidePaging {

    /**
     * The page number to retrieve, which must be a positive integer (1 or greater).
     */
    @NotNull
    @Range(min = 1, max = Integer.MAX_VALUE)
    public Integer page;

    /**
     * The page size, indicating the number of items per page. It should be a positive integer.
     * Typically, the page size is set to a specific value (e.g., 10) to limit the number of items displayed per page.
     */
    @NotNull
    @Range(min = 10, max = 10)
    public Integer pageSize;

    /**
     * Get the effective page number (0-based) based on the specified page number.
     *
     * @return The effective page number (0-based).
     */
    public Integer getPage() {
        return this.page - 1;
    }

}
