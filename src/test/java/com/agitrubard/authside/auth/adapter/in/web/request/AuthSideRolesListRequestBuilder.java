package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import com.agitrubard.authside.common.domain.model.AuthSidePagingBuilder;
import com.agitrubard.authside.common.domain.model.AuthSideSortingBuilder;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Set;

public class AuthSideRolesListRequestBuilder extends TestDataBuilder<AuthSideRolesListRequest> {

    public AuthSideRolesListRequestBuilder() {
        super(AuthSideRolesListRequest.class);
    }

    public AuthSideRolesListRequestBuilder withValidFields() {

        return new AuthSideRolesListRequestBuilder()
                .withPagination(new AuthSidePagingBuilder().withValidFields().build())
                .withName("ad")
                .withStatuses(Set.of(AuthSideRoleStatus.ACTIVE))
                .withSortName(Sort.Direction.ASC)
                .withSortCreatedAt(Sort.Direction.DESC);
    }

    public AuthSideRolesListRequestBuilder withPagination(AuthSidePageable pagination) {
        data.setPageable(pagination);
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

    public AuthSideRolesListRequestBuilder withSortName(Sort.Direction direction) {

        if (CollectionUtils.isEmpty(data.getSort())) {
            data.setSort(new ArrayList<>());
        }

        data.getSort().add(new AuthSideSortingBuilder().withProperty("name").withDirection(direction).build());
        return this;
    }

    public AuthSideRolesListRequestBuilder withSortCreatedAt(Sort.Direction direction) {

        if (CollectionUtils.isEmpty(data.getSort())) {
            data.setSort(new ArrayList<>());
        }

        data.getSort().add(new AuthSideSortingBuilder().withProperty("createdAt").withDirection(direction).build());
        return this;
    }

}
