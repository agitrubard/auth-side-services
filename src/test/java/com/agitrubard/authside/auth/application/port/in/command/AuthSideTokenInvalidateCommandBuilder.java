package com.agitrubard.authside.auth.application.port.in.command;

import com.agitrubard.authside.common.domain.model.TestDataBuilder;

public class AuthSideTokenInvalidateCommandBuilder extends TestDataBuilder<AuthSideTokensInvalidateCommand> {

    public AuthSideTokenInvalidateCommandBuilder() {
        super(AuthSideTokensInvalidateCommand.class);
    }

}
