package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideParameterSaveCommand;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideParameterSaveCommandToParameterMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideParameterSaveCommand} to their corresponding domain model representations, {@link AuthSideParameter}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideParameterSaveCommandToParameterMapper extends AuthSideBaseMapper<AuthSideParameterSaveCommand, AuthSideParameter> {

    /**
     * Initializes and returns an instance of the {@code AuthSideParameterSaveCommandToParameterMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideParameterSaveCommand} and {@link AuthSideParameter}.
     */
    static AuthSideParameterSaveCommandToParameterMapper initialize() {
        return Mappers.getMapper(AuthSideParameterSaveCommandToParameterMapper.class);
    }

}
