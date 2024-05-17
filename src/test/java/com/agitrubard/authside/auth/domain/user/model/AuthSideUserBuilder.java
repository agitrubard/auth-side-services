package com.agitrubard.authside.auth.domain.user.model;

import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRoleBuilder;
import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.common.domain.model.TestDataBuilder;
import com.agitrubard.authside.util.AuthSideValidTestData;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class AuthSideUserBuilder extends TestDataBuilder<AuthSideUser> {

    public AuthSideUserBuilder() {
        super(AuthSideUser.class);
    }

    public AuthSideUserBuilder withValidFields() {

        AuthSideUser.Password password = new PasswordBuilder()
                .withValidFields()
                .withValue(AuthSideValidTestData.ReadUser.PASSWORD_ENCRYPTED)
                .build();

        Set<AuthSideRole> roles = Set.of(new AuthSideRoleBuilder().withValidFields().build());

        return this
                .withId(UUID.randomUUID().toString())
                .withEmailAddress(RandomStringUtils.randomAlphabetic(8).concat("@authside.com"))
                .withPassword(password)
                .withRoles(roles)
                .withStatus(AuthSideUserStatus.ACTIVE);
    }

    public AuthSideUserBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSideUserBuilder withUsername(String username) {
        data.setUsername(username);
        return this;
    }

    public AuthSideUserBuilder withEmailAddress(String mailAddress) {
        data.setEmailAddress(mailAddress);
        return this;
    }

    public AuthSideUserBuilder withPassword(AuthSideUser.Password password) {
        data.setPassword(password);
        return this;
    }

    public AuthSideUserBuilder withStatus(AuthSideUserStatus status) {
        data.setStatus(status);
        return this;
    }

    public AuthSideUserBuilder withRoles(Set<AuthSideRole> roleEntities) {
        data.setRoles(roleEntities);
        return this;
    }

    private static class PasswordBuilder extends TestDataBuilder<AuthSideUser.Password> {

        public PasswordBuilder() {
            super(AuthSideUser.Password.class);
        }

        public PasswordBuilder withValidFields() {
            return this
                    .withValue(AuthSideValidTestData.ReadUser.PASSWORD_ENCRYPTED)
                    .withExpiresAt(LocalDateTime.now().plusDays(90));
        }

        public PasswordBuilder withValue(String value) {
            data.setValue(value);
            return this;
        }

        public PasswordBuilder withExpiresAt(LocalDateTime expiresAt) {
            data.setExpiresAt(expiresAt);
            return this;
        }

    }

}
