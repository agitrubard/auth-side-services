package com.agitrubard.authside.auth.domain.login.model;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSideLoginAttemptBuilder extends TestDataBuilder<AuthSideLoginAttempt> {

    public AuthSideLoginAttemptBuilder() {
        super(AuthSideLoginAttempt.class);
    }

    public AuthSideLoginAttemptBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withUserId(UUID.randomUUID().toString())
                .withLastLoginDate(LocalDateTime.now())
                .withFailedTryCount(0)
                .withLastFailedTryDate(null);
    }

    public AuthSideLoginAttemptBuilder withId(String id) {
        data.setId(id);
        return this;
    }

    public AuthSideLoginAttemptBuilder withUserId(String userId) {
        data.setUserId(userId);
        return this;
    }

    public AuthSideLoginAttemptBuilder withLastLoginDate(LocalDateTime lastLoginDate) {
        data.setLastLoginDate(lastLoginDate);
        return this;
    }

    public AuthSideLoginAttemptBuilder withFailedTryCount(Integer failedTryCount) {
        data.setFailedTryCount(failedTryCount);
        return this;
    }

    public AuthSideLoginAttemptBuilder withLastFailedTryDate(LocalDateTime lastFailedTryDate) {
        data.setLastFailedTryDate(lastFailedTryDate);
        return this;
    }

}
