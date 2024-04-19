package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.Set;

public class AuthSideRoleCreateRequestBuilder extends TestDataBuilder<AuthSideRoleCreateRequest> {

    public AuthSideRoleCreateRequestBuilder() {
        super(AuthSideRoleCreateRequest.class);
    }

    public AuthSideRoleCreateRequestBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSideRoleCreateRequestBuilder withPermissionIds(Set<String> permissionIds) {
        data.setPermissionIds(permissionIds);
        return this;
    }

}
