package com.agitrubard.authside.common.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AuthSideListing {

    protected AuthSidePaging pagination;
    protected List<AuthSideSorting> sort;

}
