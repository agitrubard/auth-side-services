package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.Set;
import java.util.UUID;

public class AuthSideRoleEntityBuilder extends TestDataBuilder<AuthSideRoleEntity> {

    public AuthSideRoleEntityBuilder() {
        super(AuthSideRoleEntity.class);
    }

    public AuthSideRoleEntityBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withName("admin")
                .withPermissions(Set.of(new AuthSidePermissionEntityBuilder().withValidFields().build()))
                .withStatus(AuthSideRoleStatus.ACTIVE);
    }

    public AuthSideRoleEntityBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSideRoleEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSideRoleEntityBuilder withStatus(AuthSideRoleStatus status) {
        data.setStatus(status);
        return this;
    }

    public AuthSideRoleEntityBuilder withPermissions(Set<AuthSidePermissionEntity> permissionEntities) {
        data.setPermissions(permissionEntities);
        return this;
    }

}
