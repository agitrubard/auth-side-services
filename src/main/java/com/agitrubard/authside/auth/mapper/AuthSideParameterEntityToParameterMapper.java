package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntity;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideParameterEntityToParameterMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideParameterEntity} to their corresponding domain model representations, {@link AuthSideParameter}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideParameterEntityToParameterMapper extends AuthSideBaseMapper<AuthSideParameterEntity, AuthSideParameter> {

    /**
     * Initializes and returns an instance of the {@link AuthSideParameterEntityToParameterMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideParameterEntity} and {@link AuthSideParameter}.
     */
    static AuthSideParameterEntityToParameterMapper initialize() {
        return Mappers.getMapper(AuthSideParameterEntityToParameterMapper.class);
    }

}
