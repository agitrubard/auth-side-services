package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;
import com.agitrubard.authside.util.AuthSideValidTestData;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class AuthSideUserEntityBuilder extends TestDataBuilder<AuthSideUserEntity> {

    public AuthSideUserEntityBuilder() {
        super(AuthSideUserEntity.class);
    }

    public AuthSideUserEntityBuilder withValidFields() {

        String id = UUID.randomUUID().toString();
        AuthSideUserEntity.PasswordEntity passwordEntity = new PasswordEntityBuilder()
                .withValidFields()
                .withUserId(id)
                .withValue(AuthSideValidTestData.ReadUser.PASSWORD_ENCRYPTED)
                .build();
        Set<AuthSideRoleEntity> roles = Set.of(new AuthSideRoleEntityBuilder().withValidFields().build());
        return this
                .withId(id)
                .withEmailAddress(RandomStringUtils.randomAlphabetic(8).concat("@authside.com"))
                .withPasswordEntity(passwordEntity)
                .withRoles(roles)
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

    public AuthSideUserEntityBuilder withPasswordEntity(AuthSideUserEntity.PasswordEntity passwordEntity) {
        data.setPassword(passwordEntity);
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

    private static class PasswordEntityBuilder extends TestDataBuilder<AuthSideUserEntity.PasswordEntity> {

        public PasswordEntityBuilder() {
            super(AuthSideUserEntity.PasswordEntity.class);
        }

        public PasswordEntityBuilder withValidFields() {
            return this
                    .withUserId(UUID.randomUUID().toString())
                    .withValue(AuthSideValidTestData.ReadUser.PASSWORD_ENCRYPTED)
                    .withExpiresAt(LocalDateTime.now().plusDays(90));
        }

        public PasswordEntityBuilder withUserId(String userId) {
            data.setUserId(userId);
            return this;
        }

        public PasswordEntityBuilder withValue(String password) {
            data.setValue(password);
            return this;
        }

        public PasswordEntityBuilder withExpiresAt(LocalDateTime expiresAt) {
            data.setExpiresAt(expiresAt);
            return this;
        }

    }

}
