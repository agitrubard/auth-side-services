package com.agitrubard.authside.common.adapter.in.web.response;

import org.springframework.http.HttpStatus;

public class AuthSideResponseBuilder {

    public AuthSideResponse.AuthSideResponseBuilder<Void> success() {
        return AuthSideResponse.<Void>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true);
    }

    public <T> AuthSideResponse.AuthSideResponseBuilder<T> success(T response) {
        return AuthSideResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true)
                .response(response);
    }

}
