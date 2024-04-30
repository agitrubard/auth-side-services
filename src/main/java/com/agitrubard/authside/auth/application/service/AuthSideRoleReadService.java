package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.mapper.AuthSideRolesListCommandToRolesListingMapper;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleReadUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleReadPort;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for reading authentication side roles.
 * This class implements the AuthSideRoleReadUseCase interface.
 * It interacts with a port to retrieve authentication side roles based on provided criteria.
 * The criteria are encapsulated in a list command object.
 * This service utilizes a mapper to convert the list command into a roles listing object.
 * The retrieved roles are returned as a page of authentication side roles.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideRoleReadService implements AuthSideRoleReadUseCase {

    private final AuthSideRoleReadPort roleReadPort;

    private final AuthSideRolesListCommandToRolesListingMapper rolesListCommandToRolesListingMapper = AuthSideRolesListCommandToRolesListingMapper.initialize();

    /**
     * Retrieves a page of authentication side roles based on the provided list command.
     *
     * @param listCommand The command containing criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    @Override
    public AuthSidePage<AuthSideRole> list(AuthSideRolesListCommand listCommand) {
        AuthSideRolesListing rolesListing = rolesListCommandToRolesListingMapper.map(listCommand);
        return roleReadPort.findAll(rolesListing);
    }

}
