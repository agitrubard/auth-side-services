package com.agitrubard.authside.auth.adapter.out.persistence.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntity;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideParameterToParameterEntityMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideParameter} to their corresponding entity representations, {@link AuthSideParameterEntity}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideParameterToParameterEntityMapper extends AuthSideBaseMapper<AuthSideParameter, AuthSideParameterEntity> {

    /**
     * Initializes and returns an instance of the {@link AuthSideParameterToParameterEntityMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideParameter} and {@link AuthSideParameterEntity}.
     */
    static AuthSideParameterToParameterEntityMapper initialize() {
        return Mappers.getMapper(AuthSideParameterToParameterEntityMapper.class);
    }

}
