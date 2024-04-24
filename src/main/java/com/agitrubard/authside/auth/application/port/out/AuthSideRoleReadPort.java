package com.agitrubard.authside.auth.application.port.out;

import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.domain.model.AuthSidePage;

/**
 * Port interface for reading authentication side roles.
 * Implementations of this interface should provide functionality to retrieve a page of authentication side roles
 * based on the provided listing criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideRoleReadPort {

    /**
     * Retrieves a page of authentication side roles based on the provided listing criteria.
     *
     * @param listing The criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    AuthSidePage<AuthSideRole> findAll(AuthSideRolesListing listing);

}
