package com.agitrubard.authside;

import com.agitrubard.authside.auth.application.port.in.usecase.AuthSidePermissionUseCase;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuthSideEndToEndTest extends AuthSideRestControllerTest implements AuthSideTestcontainer {

    @Autowired
    protected AuthSidePermissionUseCase permissionUseCase;

}
