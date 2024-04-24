package com.agitrubard.authside.auth.adapter.in.web.mapper;

import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideRolesResponse;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideRoleToRolesResponseMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRole} to their corresponding domain model representations, {@link AuthSideRolesResponse}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRoleToRolesResponseMapper extends AuthSideBaseMapper<AuthSideRole, AuthSideRolesResponse> {

    /**
     * Initializes and returns an instance of the {@link AuthSideRoleToRolesResponseMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideRole} and {@link AuthSideRolesResponse}.
     */
    static AuthSideRoleToRolesResponseMapper initialize() {
        return Mappers.getMapper(AuthSideRoleToRolesResponseMapper.class);
    }

}
