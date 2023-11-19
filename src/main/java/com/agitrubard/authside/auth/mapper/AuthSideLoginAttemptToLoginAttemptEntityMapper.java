package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntity;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideLoginAttemptToLoginAttemptEntityMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideLoginAttempt} to their corresponding entity representations, {@link AuthSideLoginAttemptEntity}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideLoginAttemptToLoginAttemptEntityMapper extends AuthSideBaseMapper<AuthSideLoginAttempt, AuthSideLoginAttemptEntity> {

    /**
     * Initializes and returns an instance of the {@code AuthSideLoginAttemptToLoginAttemptEntityMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideLoginAttempt} and {@link AuthSideLoginAttemptEntity}.
     */
    static AuthSideLoginAttemptToLoginAttemptEntityMapper initialize() {
        return Mappers.getMapper(AuthSideLoginAttemptToLoginAttemptEntityMapper.class);
    }

}
