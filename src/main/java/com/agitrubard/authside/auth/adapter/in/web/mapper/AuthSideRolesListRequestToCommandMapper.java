package com.agitrubard.authside.auth.adapter.in.web.mapper;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideRolesListRequestToCommandMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRolesListRequest} to their corresponding domain model representations, {@link AuthSideRolesListCommand}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRolesListRequestToCommandMapper extends AuthSideBaseMapper<AuthSideRolesListRequest, AuthSideRolesListCommand> {

    /**
     * Initializes and returns an instance of the {@link AuthSideRolesListRequestToCommandMapper}.
     *
     * @return An instance of the mapper for converting {@link AuthSideRolesListRequest} to {@link AuthSideRolesListCommand}.
     */
    static AuthSideRolesListRequestToCommandMapper initialize() {
        return Mappers.getMapper(AuthSideRolesListRequestToCommandMapper.class);
    }

}
