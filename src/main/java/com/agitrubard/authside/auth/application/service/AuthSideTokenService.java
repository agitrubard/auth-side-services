package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.config.AuthSideTokenConfigurationParameter;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
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
     * Generates an authentication side token based on the provided claims.
     *
     * @param claims The claims to be included in the generated token.
     * @return The generated authentication side token.
     */
    @Override
    public AuthSideToken generate(final Map<String, Object> claims) {
        final long currentTimeMillis = System.currentTimeMillis();

        final Date tokenIssuedAt = new Date(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), tokenConfigurationParameter.getAccessTokenExpireMinute());
        final String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameter.getIssuer())
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), tokenConfigurationParameter.getRefreshTokenExpireDay());
        final JwtBuilder refreshTokenBuilder = Jwts.builder();
        final String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameter.getIssuer())
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claim(AuthSideTokenClaim.USER_ID.getValue(), claims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Generates an authentication side token with an additional refresh token,
     * based on the provided claims.
     *
     * @param claims       The claims to be included in the generated token.
     * @param refreshToken The refresh token to be associated with the generated token.
     * @return The generated authentication side token with a refresh token.
     */
    @Override
    public AuthSideToken generate(final Map<String, Object> claims, final String refreshToken) {

        final long currentTimeMillis = System.currentTimeMillis();
        final Date accessTokenIssuedAt = new Date(currentTimeMillis);
        final Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), tokenConfigurationParameter.getAccessTokenExpireMinute());

        final String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(tokenConfigurationParameter.getIssuer())
                .issuedAt(accessTokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        return AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Verifies and validates the authenticity and integrity of the provided token.
     *
     * @param token The token to be verified and validated.
     * @throws AuthSideTokenNotValidException If the token verification fails.
     */
    @Override
    public void verifyAndValidate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(tokenConfigurationParameter.getPublicKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (MalformedJwtException | ExpiredJwtException | SignatureException exception) {
            throw new AuthSideTokenNotValidException(token, exception);
        }
    }


    /**
     * Retrieves the payload (claims) from the provided token.
     *
     * @param token The token from which to extract the payload.
     * @return The claims (payload) extracted from the token.
     */
    @Override
    public Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    /**
     * Retrieves the complete set of claims from the provided token as a JSON Web Signature (JWS) object.
     *
     * @param token The token from which to extract the claims.
     * @return The JWS object containing the claims.
     */
    @Override
    public Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(token);
    }

}
