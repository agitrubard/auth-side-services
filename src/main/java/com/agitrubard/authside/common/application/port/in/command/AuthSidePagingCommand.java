package com.agitrubard.authside.common.application.port.in.command;

import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract base class representing a command for pagination and sorting of authentication side entities.
 * Extends {@link AuthSideSortingCommand} to include sorting criteria.
 * Provides a property for pagination criteria.
 * Subclasses should extend this class to define specific pagination and sorting criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 * @see AuthSidePageable
 * @see AuthSideSortingCommand
 */
@Getter
@Setter
public abstract class AuthSidePagingCommand extends AuthSideSortingCommand {

    /**
     * Pagination criteria for the command.
     */
    protected AuthSidePageable pagination;

}
