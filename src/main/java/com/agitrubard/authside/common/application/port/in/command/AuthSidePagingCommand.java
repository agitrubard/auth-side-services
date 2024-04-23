package com.agitrubard.authside.common.application.port.in.command;

import com.agitrubard.authside.common.domain.model.AuthSidePaging;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSidePagingCommand extends AuthSideSortingCommand {

    protected AuthSidePaging pagination;

}
