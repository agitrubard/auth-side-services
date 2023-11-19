package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSidePermissionEntityToPermissionMapper} interface defines mapping methods for converting instances of
 * {@link AuthSidePermissionEntity} to their corresponding domain model representations, {@link AuthSidePermission}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSidePermissionEntityToPermissionMapper extends AuthSideBaseMapper<AuthSidePermissionEntity, AuthSidePermission> {

    /**
     * Initializes and returns an instance of the {@code AuthSidePermissionEntityToPermissionMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSidePermissionEntity} and {@link AuthSidePermission}.
     */
    static AuthSidePermissionEntityToPermissionMapper initialize() {
        return Mappers.getMapper(AuthSidePermissionEntityToPermissionMapper.class);
    }

}
