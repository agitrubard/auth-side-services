package com.agitrubard.authside.common.domain.model;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link AuthSidePageable} represents a pageable configuration for authentication-related data retrieval.
 * <p>
 * It extends {@link AuthSideSortable}, inheriting ordering properties functionality, and introduces
 * pageable settings including page number and page size.
 * </p>
 * <p>
 * The {@code pageNumber} indicates the specific page of data to retrieve, which must be a positive integer (1 or greater).
 * The {@code pageSize} specifies the number of items per page, typically set to limit the number of items displayed per page.
 * Both properties are annotated with {@link Positive} to enforce positive integer values.
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSidePageable extends AuthSideSortable {

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
