package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.UUID;

public class AuthSidePermissionEntityBuilder extends TestDataBuilder<AuthSidePermissionEntity> {

    public AuthSidePermissionEntityBuilder() {
        super(AuthSidePermissionEntity.class);
    }

    public AuthSidePermissionEntityBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withName("user:list")
                .withCategory(AuthSidePermissionCategory.USER_MANAGEMENT);
    }

    public AuthSidePermissionEntityBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSidePermissionEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSidePermissionEntityBuilder withCategory(AuthSidePermissionCategory category) {
        data.setCategory(category);
        return this;
    }

}
