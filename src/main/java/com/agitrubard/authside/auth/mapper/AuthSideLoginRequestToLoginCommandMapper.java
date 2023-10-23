package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideLoginRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideLoginRequestToLoginCommandMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideLoginRequest} to their corresponding command representations, {@link AuthSideLoginCommand}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideLoginRequestToLoginCommandMapper extends AuthSideBaseMapper<AuthSideLoginRequest, AuthSideLoginCommand> {

    /**
     * Initializes and returns an instance of the {@code AuthSideLoginRequestToLoginCommandMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideLoginRequest} and {@link AuthSideLoginCommand}.
     */
    static AuthSideLoginRequestToLoginCommandMapper initialize() {
        return Mappers.getMapper(AuthSideLoginRequestToLoginCommandMapper.class);
    }

}
