package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import com.agitrubard.authside.common.domain.model.AuthSidePagingBuilder;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideRolesListingBuilder extends TestDataBuilder<AuthSideRolesListing> {

    public AuthSideRolesListingBuilder() {
        super(AuthSideRolesListing.class);
    }

    public AuthSideRolesListingBuilder withValidFields() {
        return this
                .withPagination(new AuthSidePagingBuilder().withValidFields().build());
    }

    public AuthSideRolesListingBuilder withPagination(AuthSidePageable paging) {
        data.setPagination(paging);
        return this;
    }

}
