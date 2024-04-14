package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideRoleToEntityMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRoleCreateRequest} to their corresponding domain model representations, {@link AuthSideRoleCreateCommand}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRoleCreateRequestToCommandMapper extends AuthSideBaseMapper<AuthSideRoleCreateRequest, AuthSideRoleCreateCommand> {

    /**
     * Initializes and returns an instance of the {@link AuthSideRoleCreateRequestToCommandMapper}.
     *
     * @return An instance of the mapper for converting {@link AuthSideRoleCreateRequest} to {@link AuthSideRoleCreateCommand}.
     */
    static AuthSideRoleCreateRequestToCommandMapper initialize() {
        return Mappers.getMapper(AuthSideRoleCreateRequestToCommandMapper.class);
    }

}
