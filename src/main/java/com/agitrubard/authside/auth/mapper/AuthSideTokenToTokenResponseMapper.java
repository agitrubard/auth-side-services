package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideTokenResponse;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.common.mapper.AuthSideBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * The {@link AuthSideTokenToTokenResponseMapper} interface defines mapping methods for converting instances of
 * {@link AuthSideToken} to their corresponding response representations, {@link AuthSideTokenResponse}.
 * This mapper interface provides a static initialization method to obtain an instance of the mapper.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSideTokenToTokenResponseMapper extends AuthSideBaseMapper<AuthSideToken, AuthSideTokenResponse> {

    /**
     * Initializes and returns an instance of the {@link AuthSideTokenToTokenResponseMapper}.
     *
     * @return An instance of the mapper for converting between {@link AuthSideToken} and {@link AuthSideTokenResponse}.
     */
    static AuthSideTokenToTokenResponseMapper initialize() {
        return Mappers.getMapper(AuthSideTokenToTokenResponseMapper.class);
    }

}
