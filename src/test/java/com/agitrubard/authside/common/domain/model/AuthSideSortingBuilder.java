package com.agitrubard.authside.common.domain.model;

import org.springframework.data.domain.Sort;

public class AuthSideSortingBuilder extends TestDataBuilder<AuthSideSorting> {

    public AuthSideSortingBuilder() {
        super(AuthSideSorting.class);
    }

    public AuthSideSortingBuilder withProperty(String property) {
        data.setProperty(property);
        return this;
    }

    public AuthSideSortingBuilder withDirection(Sort.Direction direction) {
        data.setDirection(direction);
        return this;
    }

}
