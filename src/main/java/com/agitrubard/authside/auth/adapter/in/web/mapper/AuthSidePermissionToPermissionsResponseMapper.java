package com.agitrubard.authside.auth.adapter.in.web.mapper;

import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSidePermissionToPermissionsResponseMapper} interface defines mapping methods for converting instances of
 * {@link AuthSidePermissionsResponse} to their corresponding domain model representations, {@link AuthSidePermission}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSidePermissionToPermissionsResponseMapper extends AuthSideBaseMapper<AuthSidePermission, AuthSidePermissionsResponse> {

    /**
     * Initializes and returns an instance of AuthSidePermissionToPermissionsResponseMapper.
     *
     * @return An instance of AuthSidePermissionToPermissionsResponseMapper.
     */
    static AuthSidePermissionToPermissionsResponseMapper initialize() {
        return Mappers.getMapper(AuthSidePermissionToPermissionsResponseMapper.class);
    }

}
