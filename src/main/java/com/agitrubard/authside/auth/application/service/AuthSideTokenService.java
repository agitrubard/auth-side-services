package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.config.AuthSideTokenConfigurationParameter;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Service responsible for managing authentication tokens, including generation and validation.
 * <p>
 * This service interacts with the token configuration parameters to generate and validate authentication tokens.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
class AuthSideTokenService implements AuthSideTokenUseCase {

    private final AuthSideTokenConfigurationParameter tokenConfigurationParameter;

    /**
     * Generates an authentication token based on the provided claims and returns it.
     *
     * @param claims The claims to be included in the authentication token.
     * @return An authentication token containing the provided claims.
     */
    @Override
    public AuthSideToken generate(final Map<String, Object> claims) {
        final long currentTimeMillis = System.currentTimeMillis();

        final Date tokenIssuedAt = new Date(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), tokenConfigurationParameter.getAccessTokenExpireMinute());
        final String accessToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(tokenConfigurationParameter.getIssuer())
                .setIssuedAt(tokenIssuedAt)
                .setExpiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey(), SignatureAlgorithm.RS512)
                .setHeaderParam(AuthSideTokenClaim.TYPE.getValue(), OAuth2AccessToken.TokenType.BEARER.getValue())
                .addClaims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), tokenConfigurationParameter.getRefreshTokenExpireDay());
        final JwtBuilder refreshTokenBuilder = Jwts.builder();
        final String refreshToken = refreshTokenBuilder
                .setId(UUID.randomUUID().toString())
                .setIssuer(tokenConfigurationParameter.getIssuer())
                .setIssuedAt(tokenIssuedAt)
                .setExpiration(refreshTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey(), SignatureAlgorithm.RS512)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), claims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .setHeaderParam(AuthSideTokenClaim.TYPE.getValue(), OAuth2AccessToken.TokenType.BEARER.getValue())
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Generates a new authentication token based on the provided claims and a refresh token, and returns it.
     *
     * @param claims       The claims to be included in the authentication token.
     * @param refreshToken The refresh token used for generating the new access token.
     * @return An authentication token containing the provided claims and a new access token.
     */
    @Override
    public AuthSideToken generate(final Map<String, Object> claims, final String refreshToken) {

        final long currentTimeMillis = System.currentTimeMillis();
        final Date accessTokenIssuedAt = new Date(currentTimeMillis);
        final Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), tokenConfigurationParameter.getAccessTokenExpireMinute());

        final String accessToken = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(tokenConfigurationParameter.getIssuer())
                .setIssuedAt(accessTokenIssuedAt)
                .setExpiration(accessTokenExpiresAt)
                .setHeaderParam(AuthSideTokenClaim.TYPE.getValue(), OAuth2AccessToken.TokenType.BEARER.getValue())
                .signWith(tokenConfigurationParameter.getPrivateKey(), SignatureAlgorithm.RS512)
                .addClaims(claims)
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Verifies and validates an authentication token. If the token is invalid, it throws an exception.
     *
     * @param jwt The authentication token to verify and validate.
     * @throws AuthSideTokenNotValidException if the token is invalid.
     */
    @Override
    public void verifyAndValidate(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(tokenConfigurationParameter.getPublicKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (MalformedJwtException | ExpiredJwtException | SignatureException exception) {
            throw new AuthSideTokenNotValidException(jwt, exception);
        }
    }


    /**
     * Retrieves the claims (payload) from an authentication token.
     *
     * @param token The authentication token from which to retrieve the claims.
     * @return The claims extracted from the token.
     */
    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
