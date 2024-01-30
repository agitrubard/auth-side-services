package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.application.config.AuthSideTokenConfigurationParameter;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttemptBuilder;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUserBuilder;
import com.agitrubard.authside.common.util.AuthSideKeyPairUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

class AuthSideTokenServiceTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideTokenService tokenService;

    @Mock
    private AuthSideTokenConfigurationParameter tokenConfigurationParameter;


    @Test
    void givenValidClaims_whenTokensGenerated_thenReturnAuthSideToken() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        // Given
        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        // When
        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);
        JwtBuilder mockTokenBuilder = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuer(MOCK_ISSUER)
                .signWith(MOCK_PRIVATE_KEY)
                .issuedAt(tokenIssuedAt);

        Date mockAccessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                MOCK_ACCESS_TOKEN_EXPIRE_MINUTE
        );
        String mockAccessToken = mockTokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(mockAccessTokenExpiresAt)
                .claims(mockClaims)
                .compact();

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        String mockRefreshToken = mockTokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(refreshTokenExpiresAt)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken mockToken = AuthSideToken.builder()
                .accessToken(mockAccessToken)
                .accessTokenExpiresAt(mockAccessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(mockRefreshToken)
                .build();

        Mockito.when(tokenConfigurationParameter.getIssuer())
                .thenReturn(MOCK_ISSUER);
        Mockito.when(tokenConfigurationParameter.getPrivateKey())
                .thenReturn(MOCK_PRIVATE_KEY);
        Mockito.when(tokenConfigurationParameter.getAccessTokenExpireMinute())
                .thenReturn(MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        Mockito.when(tokenConfigurationParameter.getRefreshTokenExpireDay())
                .thenReturn(MOCK_REFRESH_TOKEN_EXPIRE_DAY);

        // Then
        AuthSideToken token = tokenService.generate(mockClaims);


        Jws<Claims> mockAccessTokenClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockToken.getAccessToken());
        Jws<Claims> accessTokenClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(token.getAccessToken());

        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().getIssuer(),
                accessTokenClaims.getPayload().getIssuer()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getHeader().getAlgorithm(),
                accessTokenClaims.getHeader().getAlgorithm()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getHeader().getType(),
                accessTokenClaims.getHeader().getType()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getRefreshTokenExpireDay();
    }


    @Test
    void givenValidClaimsAndValidRefreshToken_whenAccessTokensGenerated_thenReturnAuthSideToken() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        // Given
        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        // When
        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);
        JwtBuilder mockTokenBuilder = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuer(MOCK_ISSUER)
                .signWith(MOCK_PRIVATE_KEY)
                .issuedAt(tokenIssuedAt);

        Date mockAccessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                MOCK_ACCESS_TOKEN_EXPIRE_MINUTE
        );
        String mockAccessToken = mockTokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(mockAccessTokenExpiresAt)
                .claims(mockClaims)
                .compact();

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        String mockRefreshToken = mockTokenBuilder
                .id(UUID.randomUUID().toString())
                .expiration(refreshTokenExpiresAt)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken mockToken = AuthSideToken.builder()
                .accessToken(mockAccessToken)
                .accessTokenExpiresAt(mockAccessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(mockRefreshToken)
                .build();

        Mockito.when(tokenConfigurationParameter.getIssuer())
                .thenReturn(MOCK_ISSUER);
        Mockito.when(tokenConfigurationParameter.getPrivateKey())
                .thenReturn(MOCK_PRIVATE_KEY);
        Mockito.when(tokenConfigurationParameter.getAccessTokenExpireMinute())
                .thenReturn(MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);

        // Then
        AuthSideToken token = tokenService.generate(mockClaims, mockToken.getRefreshToken());


        Jws<Claims> mockAccessTokenClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockToken.getAccessToken());
        Jws<Claims> accessTokenClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(token.getAccessToken());

        Assertions.assertNotEquals(
                mockToken.getAccessToken(),
                token.getAccessToken()
        );
        Assertions.assertEquals(
                mockToken.getRefreshToken(),
                token.getRefreshToken()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().getIssuer(),
                accessTokenClaims.getPayload().getIssuer()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getHeader().getAlgorithm(),
                accessTokenClaims.getHeader().getAlgorithm()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getHeader().getType(),
                accessTokenClaims.getHeader().getType()
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockAccessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                accessTokenClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }


    @Test
    void givenValidAccessToken_whenTokenVerifiedAndValidated_thenDoNothing() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claims(mockClaims)
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = token.getAccessToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        tokenService.verifyAndValidate(mockAccessToken);

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidAccessToken_whenTokenTypeIsNotBearer_thenThrowAuthSideTokenNotValidException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        String accessToken = Jwts.builder()
                .header()
                .type("Invalid")
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claims(mockClaims)
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = token.getAccessToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockAccessToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidAccessToken_whenTokenSignatureAlgorithmInvalid_thenThrowAuthSideTokenNotValidException() throws NoSuchAlgorithmException {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);

        KeyPairGenerator mockKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        mockKeyPairGenerator.initialize(4096);
        KeyPair mockKeyPair = mockKeyPairGenerator.generateKeyPair();

        String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(mockKeyPair.getPrivate())
                .claims(mockClaims)
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = token.getAccessToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(mockKeyPair.getPublic());

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockAccessToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidAccessToken_whenTokenNotVerified_thenThrowAuthSideTokenNotValidException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        PrivateKey privateKey = AuthSideKeyPairUtil.generateKeyPair().getPrivate();

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(privateKey)
                .claims(mockClaims)
                .compact();

        AuthSideToken mockToken = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = mockToken.getAccessToken();

        // When

        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockAccessToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenValidRefreshToken_whenTokenVerified_thenDoNothing() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        JwtBuilder refreshTokenBuilder = Jwts.builder();
        String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        tokenService.verifyAndValidate(mockRefreshToken);

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidRefreshToken_whenTokenTypeIsNotBearer_thenThrowAuthSideTokenNotValidException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        JwtBuilder refreshTokenBuilder = Jwts.builder();
        String refreshToken = refreshTokenBuilder
                .header()
                .type("Invalid")
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockRefreshToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidRefreshToken_whenTokenSignatureAlgorithmInvalid_thenThrowAuthSideTokenNotValidException() throws NoSuchAlgorithmException {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);

        KeyPairGenerator mockKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        mockKeyPairGenerator.initialize(4096);
        KeyPair mockKeyPair = mockKeyPairGenerator.generateKeyPair();

        String refreshToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(mockKeyPair.getPrivate())
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(mockKeyPair.getPublic());

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockRefreshToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenInvalidRefreshToken_whenTokenNotVerified_thenThrowAuthSideTokenNotValidException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        PrivateKey privateKey = AuthSideKeyPairUtil.generateKeyPair().getPrivate();

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        JwtBuilder refreshTokenBuilder = Jwts.builder();
        String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(privateKey)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> tokenService.verifyAndValidate(mockRefreshToken)
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }


    @Test
    void givenValidAccessToken_whenTokenParsed_thenReturnPayload() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claims(mockClaims)
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = token.getAccessToken();

        // When
        Claims mockPayload = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockAccessToken)
                .getPayload();

        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Claims payload = tokenService.getPayload(mockAccessToken);

        Assertions.assertEquals(
                mockPayload.getIssuer(),
                payload.getIssuer()
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_ID.getValue()),
                payload.get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USERNAME.getValue()),
                payload.get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                payload.get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                payload.get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                payload.get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenValidRefreshToken_whenTokenParsed_thenReturnPayload() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        JwtBuilder refreshTokenBuilder = Jwts.builder();
        String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Claims mockPayload = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockRefreshToken)
                .getPayload();

        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Claims payload = tokenService.getPayload(mockRefreshToken);

        Assertions.assertEquals(
                mockPayload.getIssuer(),
                payload.getIssuer()
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_ID.getValue()),
                payload.get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USERNAME.getValue()),
                payload.get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                payload.get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                payload.get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockPayload.get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                payload.get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }


    @Test
    void givenValidAccessToken_whenTokenParsed_thenReturnClaims() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date accessTokenExpiresAt = DateUtils.addMinutes(new Date(currentTimeMillis), MOCK_ACCESS_TOKEN_EXPIRE_MINUTE);
        String accessToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claims(mockClaims)
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .build();

        // Given
        String mockAccessToken = token.getAccessToken();

        // When
        Jws<Claims> mockJwsClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockAccessToken);

        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Jws<Claims> jwsClaims = tokenService.getClaims(mockAccessToken);

        Assertions.assertEquals(
                mockJwsClaims.getPayload().getIssuer(),
                jwsClaims.getPayload().getIssuer()
        );
        Assertions.assertEquals(
                mockJwsClaims.getHeader().getAlgorithm(),
                jwsClaims.getHeader().getAlgorithm()
        );
        Assertions.assertEquals(
                mockJwsClaims.getHeader().getType(),
                jwsClaims.getHeader().getType()
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    @Test
    void givenValidRefreshToken_whenTokenParsed_thenReturnClaims() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();

        Claims mockClaims = mockUser.getPayload(mockLoginAttempt);

        long currentTimeMillis = System.currentTimeMillis();

        Date tokenIssuedAt = new Date(currentTimeMillis);

        Date refreshTokenExpiresAt = DateUtils.addDays(new Date(currentTimeMillis), MOCK_REFRESH_TOKEN_EXPIRE_DAY);
        JwtBuilder refreshTokenBuilder = Jwts.builder();
        String refreshToken = refreshTokenBuilder
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuer(MOCK_ISSUER)
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(MOCK_PRIVATE_KEY)
                .claim(AuthSideTokenClaim.USER_ID.getValue(), mockClaims.get(AuthSideTokenClaim.USER_ID.getValue()))
                .compact();

        AuthSideToken token = AuthSideToken.builder()
                .refreshToken(refreshToken)
                .build();

        // Given
        String mockRefreshToken = token.getRefreshToken();

        // When
        Jws<Claims> mockJwsClaims = Jwts.parser()
                .verifyWith(MOCK_PUBLIC_KEY)
                .build()
                .parseSignedClaims(mockRefreshToken);

        Mockito.when(tokenConfigurationParameter.getPublicKey())
                .thenReturn(MOCK_PUBLIC_KEY);

        // Then
        Jws<Claims> jwsClaims = tokenService.getClaims(mockRefreshToken);

        Assertions.assertEquals(
                mockJwsClaims.getPayload().getIssuer(),
                jwsClaims.getPayload().getIssuer()
        );
        Assertions.assertEquals(
                mockJwsClaims.getHeader().getAlgorithm(),
                jwsClaims.getHeader().getAlgorithm()
        );
        Assertions.assertEquals(
                mockJwsClaims.getHeader().getType(),
                jwsClaims.getHeader().getType()
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_ID.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USERNAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_FIRST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_NAME.getValue())
        );
        Assertions.assertEquals(
                mockJwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue()),
                jwsClaims.getPayload().get(AuthSideTokenClaim.USER_LAST_LOGIN_DATE.getValue())
        );

        // Verify
        Mockito.verify(tokenConfigurationParameter, Mockito.times(1))
                .getPublicKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getPrivateKey();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getIssuer();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getAccessTokenExpireMinute();
        Mockito.verify(tokenConfigurationParameter, Mockito.times(0))
                .getRefreshTokenExpireDay();
    }

    private static final String MOCK_ISSUER = "AYS";
    private static final Integer MOCK_ACCESS_TOKEN_EXPIRE_MINUTE = 120;
    private static final Integer MOCK_REFRESH_TOKEN_EXPIRE_DAY = 1;
    private static final PrivateKey MOCK_PRIVATE_KEY;
    private static final PublicKey MOCK_PUBLIC_KEY;

    static {
        KeyPair keyPair = AuthSideKeyPairUtil.generateKeyPair();
        MOCK_PRIVATE_KEY = keyPair.getPrivate();
        MOCK_PUBLIC_KEY = keyPair.getPublic();
    }

}
