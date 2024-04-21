package com.agitrubard.authside.auth.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRoleCreateCommand {

    private String name;
    private Set<String> permissionIds;

}
