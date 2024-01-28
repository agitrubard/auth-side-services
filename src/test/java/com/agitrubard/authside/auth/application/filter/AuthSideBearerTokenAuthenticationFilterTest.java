package com.agitrubard.authside.auth.application.filter;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenAlreadyInvalidatedException;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideInvalidTokenUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttemptBuilder;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUserBuilder;
import com.agitrubard.authside.common.util.AuthSideKeyPairUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Date;

class AuthSideBearerTokenAuthenticationFilterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideBearerTokenAuthenticationFilter bearerTokenAuthenticationFilter;

    @Mock
    private AuthSideTokenUseCase tokenUseCase;

    @Mock
    private AuthSideInvalidTokenUseCase invalidTokenUseCase;


    @Test
    void givenValidHttpServletRequestAndResponseAndFilterChain_whenTokenNotExist_thenDoFilter() throws ServletException, IOException {

        // Given
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        // When
        Mockito.when(mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(null);

        // Then
        bearerTokenAuthenticationFilter.doFilterInternal(
                mockHttpServletRequest,
                mockHttpServletResponse,
                mockFilterChain
        );

        // Verify
        Mockito.verify(mockHttpServletRequest, Mockito.times(1))
                .getHeader(HttpHeaders.AUTHORIZATION);
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .verifyAndValidate(Mockito.anyString());
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getPayload(Mockito.anyString());
        Mockito.verify(invalidTokenUseCase, Mockito.times(0))
                .validateInvalidityOfToken(Mockito.anyString());
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getClaims(Mockito.anyString());
    }

    @Test
    void givenValidHttpServletRequestAndResponseAndFilterChain_whenAuthorizationHeaderIsValid_thenDoFilter() throws ServletException, IOException {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Claims mockPayload = mockUser.getPayload(mockLoginAttempt);

        KeyPair mockKeyPair = AuthSideKeyPairUtil.generateKeyPair();
        String mockToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(mockKeyPair.getPrivate())
                .claims(mockPayload)
                .compact();

        Jws<Claims> mockClaims = Jwts.parser()
                .verifyWith(mockKeyPair.getPublic())
                .build()
                .parseSignedClaims(mockToken);

        // Given
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        // When
        Mockito.when(mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(STR."Bearer \{mockToken}");

        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(Mockito.anyString()))
                .thenReturn(mockPayload);

        Mockito.doNothing()
                .when(invalidTokenUseCase)
                .validateInvalidityOfToken(mockPayload.getId());

        Mockito.when(tokenUseCase.getClaims(Mockito.anyString()))
                .thenReturn(mockClaims);

        // Then
        bearerTokenAuthenticationFilter.doFilterInternal(
                mockHttpServletRequest,
                mockHttpServletResponse,
                mockFilterChain
        );

        // Verify
        Mockito.verify(mockHttpServletRequest, Mockito.times(1))
                .getHeader(HttpHeaders.AUTHORIZATION);
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());
        Mockito.verify(invalidTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(mockPayload.getId());
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getClaims(Mockito.anyString());
    }

    @Test
    void givenValidHttpServletRequestAndResponseAndFilterChain_whenTokenNotVerified_thenThrowAuthSideTokenNotValidException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Claims mockPayload = mockUser.getPayload(mockLoginAttempt);

        KeyPair mockKeyPair = AuthSideKeyPairUtil.generateKeyPair();
        String mockToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(mockKeyPair.getPrivate())
                .claims(mockPayload)
                .compact();

        // Given
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        // When
        Mockito.when(mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(STR."Bearer \{mockToken}");

        Mockito.doThrow(AuthSideTokenNotValidException.class)
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> bearerTokenAuthenticationFilter.doFilterInternal(
                        mockHttpServletRequest,
                        mockHttpServletResponse,
                        mockFilterChain
                )
        );

        // Verify
        Mockito.verify(mockHttpServletRequest, Mockito.times(1))
                .getHeader(HttpHeaders.AUTHORIZATION);
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getPayload(Mockito.anyString());
        Mockito.verify(invalidTokenUseCase, Mockito.times(0))
                .validateInvalidityOfToken(mockPayload.getId());
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getClaims(Mockito.anyString());
    }

    @Test
    void givenValidHttpServletRequestAndResponseAndFilterChain_whenTokenAlreadyInvalidated_thenThrowAuthSideTokenAlreadyInvalidatedException() throws ServletException, IOException {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Claims mockPayload = mockUser.getPayload(mockLoginAttempt);

        KeyPair mockKeyPair = AuthSideKeyPairUtil.generateKeyPair();
        String mockToken = Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(mockKeyPair.getPrivate())
                .claims(mockPayload)
                .compact();

        // Given
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

        // When
        Mockito.when(mockHttpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn(STR."Bearer \{mockToken}");

        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(Mockito.anyString()))
                .thenReturn(mockPayload);

        Mockito.doThrow(AuthSideTokenAlreadyInvalidatedException.class)
                .when(invalidTokenUseCase)
                .validateInvalidityOfToken(mockPayload.getId());

        // Then
        Assertions.assertThrows(
                AuthSideTokenAlreadyInvalidatedException.class,
                () -> bearerTokenAuthenticationFilter.doFilterInternal(
                        mockHttpServletRequest,
                        mockHttpServletResponse,
                        mockFilterChain
                )
        );

        // Verify
        Mockito.verify(mockHttpServletRequest, Mockito.times(1))
                .getHeader(HttpHeaders.AUTHORIZATION);
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());
        Mockito.verify(invalidTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(mockPayload.getId());
        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getClaims(Mockito.anyString());
    }

}
