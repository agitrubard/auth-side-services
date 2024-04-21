package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideUserEntity;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideUserEntityToUserMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideUserEntity} to their corresponding domain model representations, {@link AuthSideUser}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideUserEntityToUserMapper extends AuthSideBaseMapper<AuthSideUserEntity, AuthSideUser> {

    /**
     * Initializes and returns an instance of the {@link AuthSideUserEntityToUserMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideUserEntity} and {@link AuthSideUser}.
     */
    static AuthSideUserEntityToUserMapper initialize() {
        return Mappers.getMapper(AuthSideUserEntityToUserMapper.class);
    }

}
