package com.agitrubard.authside.auth.application.port.in.command;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.application.port.in.command.AuthSidePagingCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRolesListCommand extends AuthSidePagingCommand {

    private Filter filter;

    @Getter
    @Setter
    public static class Filter {
        private String name;
        private Set<AuthSideRoleStatus> statuses;
    }

}
