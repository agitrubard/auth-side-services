package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Builder
public class AuthSideRoleCreateCommand {

    private String name;
    private Set<String> permissionIds;

}
