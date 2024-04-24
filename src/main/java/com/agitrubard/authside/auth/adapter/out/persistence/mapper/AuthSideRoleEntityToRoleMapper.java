package com.agitrubard.authside.auth.adapter.out.persistence.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideRoleEntityToRoleMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRoleEntity} to their corresponding domain model representations, {@link AuthSideRole}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRoleEntityToRoleMapper extends AuthSideBaseMapper<AuthSideRoleEntity, AuthSideRole> {

    /**
     * Initializes and returns an instance of the {@code AuthSideRoleEntityToRoleMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideRoleEntity} and {@link AuthSideRole}.
     */
    static AuthSideRoleEntityToRoleMapper initialize() {
        return Mappers.getMapper(AuthSideRoleEntityToRoleMapper.class);
    }

}
