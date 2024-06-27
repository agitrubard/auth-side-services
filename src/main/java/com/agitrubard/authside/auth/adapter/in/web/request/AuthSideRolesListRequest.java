package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.adapter.in.web.request.AuthSidePagingRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a request for retrieving a list of authentication side roles with optional filtering and paging support.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRolesListRequest extends AuthSidePagingRequest {

    /**
     * Filtering criteria for the roles list request.
     */
    @Valid
    private Filter filter;

    /**
     * Nested class representing the filtering options.
     */
    @Getter
    @Setter
    public static class Filter {

        /**
         * The name of the role to filter.
         */
        @Size(min = 2, max = 255)
        private String name;

        /**
         * Set of statuses to filter roles by.
         */
        private Set<AuthSideRoleStatus> statuses;

    }

    /**
     * Checks if ordering by name or creation date is supported.
     *
     * @return {@code true} if ordering by name or creation date is supported, {@code false} otherwise.
     */
    @JsonIgnore
    @AssertTrue
    @Override
    public boolean isSortPropertyAccepted() {
        final Set<String> acceptedFields = Set.of("name", "createdAt");
        return this.isPropertyAccepted(acceptedFields);
    }

}
