package com.agitrubard.authside.common.application.port.in.command;

import com.agitrubard.authside.common.domain.model.AuthSidePageable;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link AuthSidePagingCommand} defines pageable criteria for commands related to authentication.
 * <p>
 * It encapsulates the configuration for pageable using {@link AuthSidePageable}.
 * The {@code pageable} property specifies the pageable details, ensuring that pageable criteria
 * are provided for operations that require paging through results.
 * </p>
 * <p>
 * This abstract class serves as a base for commands that involve pageable, allowing subclasses
 * to define specific behaviors or additional properties as needed.
 * </p>
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 * @see AuthSidePageable
 */
@Getter
@Setter
public abstract class AuthSidePagingCommand {

    /**
     * Pageable criteria for the command.
     */
    protected AuthSidePageable pageable;

}
