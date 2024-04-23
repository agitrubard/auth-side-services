package com.agitrubard.authside.common.application.port.in.command;

import com.agitrubard.authside.common.domain.model.AuthSideSorting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class AuthSideSortingCommand {

    protected List<AuthSideSorting> sort;

}
