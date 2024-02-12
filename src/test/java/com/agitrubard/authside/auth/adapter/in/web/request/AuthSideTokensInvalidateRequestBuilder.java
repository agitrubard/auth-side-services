package com.agitrubard.authside.auth.adapter.in.web.request;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideTokensInvalidateRequestBuilder extends TestDataBuilder<AuthSideTokensInvalidateRequest> {

    public AuthSideTokensInvalidateRequestBuilder() {
        super(AuthSideTokensInvalidateRequest.class);
    }

    public AuthSideTokensInvalidateRequestBuilder withAccessToken(String accessToken) {
        this.data.setAccessToken(accessToken);
        return this;
    }

    public AuthSideTokensInvalidateRequestBuilder withRefreshToken(String refreshToken) {
        this.data.setRefreshToken(refreshToken);
        return this;
    }

}
