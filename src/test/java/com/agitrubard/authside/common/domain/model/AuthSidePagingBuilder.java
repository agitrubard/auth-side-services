package com.agitrubard.authside.common.domain.model;

public class AuthSidePagingBuilder extends TestDataBuilder<AuthSidePageable> {

    public AuthSidePagingBuilder() {
        super(AuthSidePageable.class);
    }

    public AuthSidePagingBuilder withValidFields() {
        return this
                .withPage(1)
                .withSize(10);
    }

    public AuthSidePagingBuilder withPage(int page) {
        data.setPageNumber(page);
        return this;
    }

    public AuthSidePagingBuilder withSize(int pageSize) {
        data.setPageSize(pageSize);
        return this;
    }

}
