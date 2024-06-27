package com.agitrubard.authside.common.domain.model;

import org.springframework.data.domain.Sort;

public class AuthSideSortableBuilder extends TestDataBuilder<AuthSideSortable> {

    public AuthSideSortableBuilder() {
        super(AuthSideSortable.class);
    }

    public static class OrderBuilder extends TestDataBuilder<AuthSideSortable.Order> {

        public OrderBuilder() {
            super(AuthSideSortable.Order.class);
        }

        public OrderBuilder withProperty(String property) {
            data.setProperty(property);
            return this;
        }

        public OrderBuilder withDirection(Sort.Direction direction) {
            data.setDirection(direction);
            return this;
        }

    }

}
