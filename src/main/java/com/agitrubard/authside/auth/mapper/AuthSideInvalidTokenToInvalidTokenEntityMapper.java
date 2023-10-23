package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideInvalidTokenEntity;
import com.agitrubard.authside.auth.domain.token.AuthSideInvalidToken;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideInvalidTokenToInvalidTokenEntityMapper} interface defines mapping methods for converting instances
 * of {@link AuthSideInvalidToken} to their corresponding entity representations, {@link AuthSideInvalidTokenEntity}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideInvalidTokenToInvalidTokenEntityMapper extends AuthSideBaseMapper<AuthSideInvalidToken, AuthSideInvalidTokenEntity> {

    /**
     * Initializes and returns an instance of the {@code AuthSideInvalidTokenToInvalidTokenEntityMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideInvalidToken} and {@link AuthSideInvalidTokenEntity}.
     */
    static AuthSideInvalidTokenToInvalidTokenEntityMapper initialize() {
        return Mappers.getMapper(AuthSideInvalidTokenToInvalidTokenEntityMapper.class);
    }

}
