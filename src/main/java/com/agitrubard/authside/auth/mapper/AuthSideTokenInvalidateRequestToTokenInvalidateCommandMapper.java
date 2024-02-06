package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokensInvalidateRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommand;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@code AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideTokensInvalidateRequest} to their corresponding command representations, {@link AuthSideTokensInvalidateCommand}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper extends AuthSideBaseMapper<AuthSideTokensInvalidateRequest, AuthSideTokensInvalidateCommand> {

    /**
     * Initializes and returns an instance of the {@code AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideTokensInvalidateRequest} and {@link AuthSideTokensInvalidateCommand}.
     */
    static AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper initialize() {
        return Mappers.getMapper(AuthSideTokenInvalidateRequestToTokenInvalidateCommandMapper.class);
    }

}
