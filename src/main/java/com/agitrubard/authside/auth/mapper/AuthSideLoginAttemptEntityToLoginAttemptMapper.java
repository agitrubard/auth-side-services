package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntity;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideLoginAttemptEntityToLoginAttemptMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideLoginAttemptEntity} to their corresponding model representations, {@link AuthSideLoginAttempt}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideLoginAttemptEntityToLoginAttemptMapper extends AuthSideBaseMapper<AuthSideLoginAttemptEntity, AuthSideLoginAttempt> {

    /**
     * Initializes and returns an instance of the {@code AuthSideLoginAttemptEntityToLoginAttemptMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideLoginAttemptEntity} and {@link AuthSideLoginAttempt}.
     */
    static AuthSideLoginAttemptEntityToLoginAttemptMapper initialize() {
        return Mappers.getMapper(AuthSideLoginAttemptEntityToLoginAttemptMapper.class);
    }

}
