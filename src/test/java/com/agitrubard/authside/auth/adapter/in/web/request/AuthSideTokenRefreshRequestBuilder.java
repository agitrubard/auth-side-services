package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideTokenRefreshRequestBuilder extends TestDataBuilder<AuthSideTokenRefreshRequest> {

    public AuthSideTokenRefreshRequestBuilder() {
        super(AuthSideTokenRefreshRequest.class);
    }

    public AuthSideTokenRefreshRequestBuilder withRefreshToken(String refreshToken) {
        this.data.setRefreshToken(refreshToken);
        return this;
    }

}
