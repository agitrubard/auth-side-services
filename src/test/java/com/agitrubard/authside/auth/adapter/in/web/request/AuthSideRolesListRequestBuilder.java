package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import com.agitrubard.authside.common.domain.model.AuthSidePageableBuilder;
import com.agitrubard.authside.common.domain.model.AuthSideSortable;
import com.agitrubard.authside.common.domain.model.AuthSideSortableBuilder;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;

public class AuthSideRolesListRequestBuilder extends TestDataBuilder<AuthSideRolesListRequest> {

    public AuthSideRolesListRequestBuilder() {
        super(AuthSideRolesListRequest.class);
    }

    public AuthSideRolesListRequestBuilder withValidFields() {

        return new AuthSideRolesListRequestBuilder()
                .withPageable(new AuthSidePageableBuilder().withValidFields().build())
                .withName("ad")
                .withStatuses(Set.of(AuthSideRoleStatus.ACTIVE))
                .withOrderByName(Sort.Direction.ASC)
                .withOrderByCreatedAt(Sort.Direction.DESC);
    }

    public AuthSideRolesListRequestBuilder withPageable(AuthSidePageable pageable) {
        data.setPageable(pageable);
        return this;
    }

    public AuthSideRolesListRequestBuilder withName(String name) {

        if (data.getFilter() == null) {
            data.setFilter(new AuthSideRolesListRequest.Filter());
        }

        data.getFilter().setName(name);
        return this;
    }

    public AuthSideRolesListRequestBuilder withStatuses(Set<AuthSideRoleStatus> statuses) {

        if (data.getFilter() == null) {
            data.setFilter(new AuthSideRolesListRequest.Filter());
        }

        data.getFilter().setStatuses(statuses);
        return this;
    }

    public AuthSideRolesListRequestBuilder withOrderByName(Sort.Direction direction) {

        if (CollectionUtils.isEmpty(data.getPageable().getOrders())) {
            data.getPageable().setOrders(new HashSet<>());
        }

        AuthSideSortable.Order order = new AuthSideSortableBuilder.OrderBuilder()
                .withProperty("name")
                .withDirection(direction)
                .build();
        data.getPageable().getOrders().add(order);
        return this;
    }

    public AuthSideRolesListRequestBuilder withOrderByCreatedAt(Sort.Direction direction) {

        if (CollectionUtils.isEmpty(data.getPageable().getOrders())) {
            data.getPageable().setOrders(new HashSet<>());
        }

        AuthSideSortable.Order order = new AuthSideSortableBuilder.OrderBuilder()
                .withProperty("createdAt")
                .withDirection(direction)
                .build();
        data.getPageable().getOrders().add(order);
        return this;
    }

}
