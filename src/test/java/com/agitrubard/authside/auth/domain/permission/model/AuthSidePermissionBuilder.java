package com.agitrubard.authside.auth.domain.permission.model;

import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.UUID;

public class AuthSidePermissionBuilder extends TestDataBuilder<AuthSidePermission> {

    public AuthSidePermissionBuilder() {
        super(AuthSidePermission.class);
    }

    public AuthSidePermissionBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withName("user:list")
                .withCategory(AuthSidePermissionCategory.USER_MANAGEMENT);
    }

    public AuthSidePermissionBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSidePermissionBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSidePermissionBuilder withCategory(AuthSidePermissionCategory category) {
        data.setCategory(category);
        return this;
    }

}
