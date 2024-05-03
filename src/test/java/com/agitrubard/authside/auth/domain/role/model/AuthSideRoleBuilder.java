package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermissionBuilder;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.Set;
import java.util.UUID;

public class AuthSideRoleBuilder extends TestDataBuilder<AuthSideRole> {

    public AuthSideRoleBuilder() {
        super(AuthSideRole.class);
    }

    public AuthSideRoleBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withName("admin")
                .withPermissions(Set.of(
                                new AuthSidePermissionBuilder().withValidFields().withName("role:add").build(),
                                new AuthSidePermissionBuilder().withValidFields().withName("role:update").build()
                        )
                )
                .withStatus(AuthSideRoleStatus.ACTIVE);
    }

    public AuthSideRoleBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSideRoleBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public AuthSideRoleBuilder withStatus(AuthSideRoleStatus status) {
        data.setStatus(status);
        return this;
    }

    public AuthSideRoleBuilder withPermissions(Set<AuthSidePermission> permissions) {
        data.setPermissions(permissions);
        return this;
    }

}
