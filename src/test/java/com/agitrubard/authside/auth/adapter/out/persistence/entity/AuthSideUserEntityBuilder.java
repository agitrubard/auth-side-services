package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;
import com.agitrubard.authside.util.AuthSideValidTestData;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;
import java.util.UUID;

public class AuthSideUserEntityBuilder extends TestDataBuilder<AuthSideUserEntity> {

    public AuthSideUserEntityBuilder() {
        super(AuthSideUserEntity.class);
    }

    public AuthSideUserEntityBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withEmailAddress(RandomStringUtils.randomAlphabetic(8).concat("@authside.com"))
                .withPassword(AuthSideValidTestData.ReadUser.PASSWORD_ENCRYPTED)
                .withRoles(Set.of(new AuthSideRoleEntityBuilder().withValidFields().build()))
                .withStatus(AuthSideUserStatus.ACTIVE);
    }

    public AuthSideUserEntityBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSideUserEntityBuilder withEmailAddress(String mailAddress) {
        data.setEmailAddress(mailAddress);
        return this;
    }

    public AuthSideUserEntityBuilder withPassword(String password) {
        data.setPassword(password);
        return this;
    }

    public AuthSideUserEntityBuilder withStatus(AuthSideUserStatus status) {
        data.setStatus(status);
        return this;
    }

    public AuthSideUserEntityBuilder withRoles(Set<AuthSideRoleEntity> roleEntities) {
        data.setRoles(roleEntities);
        return this;
    }

}
