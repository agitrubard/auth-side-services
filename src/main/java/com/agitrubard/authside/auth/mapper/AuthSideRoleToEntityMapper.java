package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideRoleToEntityMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideRole} to their corresponding domain model representations, {@link AuthSideRoleEntity}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideRoleToEntityMapper extends AuthSideBaseMapper<AuthSideRole, AuthSideRoleEntity> {

    /**
     * Initializes and returns an instance of the {@link AuthSideRoleToEntityMapper}.
     *
     * @return An instance of the mapper for converting {@link AuthSideRole} to {@link AuthSideRoleEntity}.
     */
    static AuthSideRoleToEntityMapper initialize() {
        return Mappers.getMapper(AuthSideRoleToEntityMapper.class);
    }

}
