package com.agitrubard.authside.auth.adapter.in.web.mapper;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideTokenRefreshRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideTokenRefreshRequestToTokenRefreshCommandMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideTokenRefreshRequest} to their corresponding command representations, {@link AuthSideTokenRefreshCommand}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideTokenRefreshRequestToTokenRefreshCommandMapper extends AuthSideBaseMapper<AuthSideTokenRefreshRequest, AuthSideTokenRefreshCommand> {

    /**
     * Initializes and returns an instance of the {@link AuthSideTokenRefreshRequestToTokenRefreshCommandMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideTokenRefreshRequest} and {@link AuthSideTokenRefreshCommand}.
     */
    static AuthSideTokenRefreshRequestToTokenRefreshCommandMapper initialize() {
        return Mappers.getMapper(AuthSideTokenRefreshRequestToTokenRefreshCommandMapper.class);
    }

}
