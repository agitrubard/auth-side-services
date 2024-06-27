package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import com.agitrubard.authside.common.domain.model.AuthSidePageableBuilder;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideRolesListingBuilder extends TestDataBuilder<AuthSideRolesListing> {

    public AuthSideRolesListingBuilder() {
        super(AuthSideRolesListing.class);
    }

    public AuthSideRolesListingBuilder withValidFields() {
        return this
                .withPageable(new AuthSidePageableBuilder().withValidFields().build());
    }

    public AuthSideRolesListingBuilder withPageable(AuthSidePageable paging) {
        data.setPageable(paging);
        return this;
    }

}
