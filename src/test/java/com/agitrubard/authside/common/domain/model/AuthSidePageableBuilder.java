package com.agitrubard.authside.common.domain.model;

public class AuthSidePageableBuilder extends TestDataBuilder<AuthSidePageable> {

    public AuthSidePageableBuilder() {
        super(AuthSidePageable.class);
    }

    public AuthSidePageableBuilder withValidFields() {
        return this
                .withPage(1)
                .withSize(10)
                .withoutOrders();
    }

    public AuthSidePageableBuilder withPage(int page) {
        data.setPageNumber(page);
        return this;
    }

    public AuthSidePageableBuilder withSize(int pageSize) {
        data.setPageSize(pageSize);
        return this;
    }

    public AuthSidePageableBuilder withoutOrders() {
        data.setOrders(null);
        return this;
    }

}
