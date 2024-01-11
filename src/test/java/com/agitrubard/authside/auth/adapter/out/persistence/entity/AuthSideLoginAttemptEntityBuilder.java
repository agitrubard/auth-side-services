package com.agitrubard.authside.auth.adapter.out.persistence.entity;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthSideLoginAttemptEntityBuilder extends TestDataBuilder<AuthSideLoginAttemptEntity> {

    public AuthSideLoginAttemptEntityBuilder() {
        super(AuthSideLoginAttemptEntity.class);
    }

    public AuthSideLoginAttemptEntityBuilder withValidFields() {
        return new AuthSideLoginAttemptEntityBuilder()
                .withId(UUID.randomUUID().toString())
                .withUserId(UUID.randomUUID().toString())
                .withLastLoginDate(LocalDateTime.now())
                .withFailedTryCount(0)
                .withLastFailedTryDate(null);
    }

    public AuthSideLoginAttemptEntityBuilder withId(String id) {
        this.data.setId(id);
        return this;
    }

    public AuthSideLoginAttemptEntityBuilder withUserId(String userId) {
        this.data.setUserId(userId);
        return this;
    }

    public AuthSideLoginAttemptEntityBuilder withLastLoginDate(LocalDateTime lastLoginDate) {
        this.data.setLastLoginDate(lastLoginDate);
        return this;
    }

    public AuthSideLoginAttemptEntityBuilder withFailedTryCount(Integer failedTryCount) {
        this.data.setFailedTryCount(failedTryCount);
        return this;
    }

    public AuthSideLoginAttemptEntityBuilder withLastFailedTryDate(LocalDateTime lastFailedTryDate) {
        this.data.setLastFailedTryDate(lastFailedTryDate);
        return this;
    }

}
