package com.agitrubard.authside.common.adapter.in.web.response;

import org.springframework.http.HttpStatus;

public class AuthSideErrorResponseBuilder {

    public AuthSideErrorResponse.AuthSideErrorResponseBuilder forbidden() {
        return AuthSideErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .isSuccess(false)
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName());
    }

}
