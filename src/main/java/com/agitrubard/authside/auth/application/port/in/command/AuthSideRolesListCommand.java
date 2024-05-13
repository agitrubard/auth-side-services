package com.agitrubard.authside.auth.application.port.in.command;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.application.port.in.command.AuthSidePagingCommand;
import com.agitrubard.authside.common.domain.model.AuthSideFiltering;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * This class represents a command for retrieving a list of roles on the authentication side,
 * extending from {@link AuthSidePagingCommand}.
 * <p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRolesListCommand extends AuthSidePagingCommand {

    /**
     * The filter criteria to apply when fetching roles.
     */
    private Filter filter;

    /**
     * Represents the filtering parameters for roles.
     */
    @Getter
    @Setter
    public static class Filter implements AuthSideFiltering {
        /**
         * The name to filter roles by.
         */
        private String name;

        /**
         * The set of statuses to filter roles by.
         */
        private Set<AuthSideRoleStatus> statuses;
    }

}
