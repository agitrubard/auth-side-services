package com.agitrubard.authside.auth.domain.token;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

import java.util.UUID;

public class AuthSideInvalidTokenBuilder extends TestDataBuilder<AuthSideInvalidToken> {

    public AuthSideInvalidTokenBuilder() {
        super(AuthSideInvalidToken.class);
    }

    public AuthSideInvalidTokenBuilder withValidFields() {
        return this
                .withTokenId(UUID.randomUUID().toString());
    }

    public AuthSideInvalidTokenBuilder withTokenId(String tokenId) {
        data.setTokenId(tokenId);
        return this;
    }

}
