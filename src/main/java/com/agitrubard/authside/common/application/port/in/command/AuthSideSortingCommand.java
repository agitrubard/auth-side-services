package com.agitrubard.authside.common.application.port.in.command;

import com.agitrubard.authside.common.domain.model.AuthSideSorting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Abstract base class representing a command for sorting authentication side entities.
 * Provides a property for sorting criteria.
 * Subclasses should extend this class to define specific sorting criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 * @see AuthSideSorting
 */
@Getter
@Setter
public abstract class AuthSideSortingCommand {

    /**
     * Sorting criteria for the command.
     */
    protected List<AuthSideSorting> sort;

}
