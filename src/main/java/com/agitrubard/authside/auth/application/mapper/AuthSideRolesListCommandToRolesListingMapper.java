package com.agitrubard.authside.auth.application.mapper;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideRolesListCommandToRolesListingMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRolesListCommand} to their corresponding domain model representations, {@link AuthSideRolesListing}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRolesListCommandToRolesListingMapper extends AuthSideBaseMapper<AuthSideRolesListCommand, AuthSideRolesListing> {

    /**
     * Initializes and returns an instance of the {@link AuthSideRolesListCommandToRolesListingMapper}.
     *
     * @return An instance of the mapper for converting {@link AuthSideRolesListCommand} to {@link AuthSideRolesListing}.
     */
    static AuthSideRolesListCommandToRolesListingMapper initialize() {
        return Mappers.getMapper(AuthSideRolesListCommandToRolesListingMapper.class);
    }

}
