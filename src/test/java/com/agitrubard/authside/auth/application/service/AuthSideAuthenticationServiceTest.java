package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.application.exception.AuthSidePasswordNotValidException;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenAlreadyInvalidatedException;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenNotValidException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserMaximumLoginAttemptsExceedException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserNotActiveException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserNotFoundException;
import com.agitrubard.authside.auth.application.exception.AuthSideUsernameNotValidException;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommandBuilder;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommandBuilder;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommandBuilder;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideInvalidTokenUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptSavePort;
import com.agitrubard.authside.auth.application.port.out.AuthSideUserReadPort;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttemptBuilder;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.AuthSideTokenBuilder;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.enums.AuthSideUserStatus;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUserBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class AuthSideAuthenticationServiceTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideAuthenticationService authenticationService;

    @Mock
    private AuthSideUserReadPort userReadPort;

    @Mock
    private AuthSideTokenUseCase tokenUseCase;
    @Mock
    private AuthSideInvalidTokenUseCase invalidateTokenUseCase;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthSideLoginAttemptReadPort loginAttemptReadPort;
    @Mock
    private AuthSideLoginAttemptSavePort loginAttemptSavePort;

    @Test
    void givenValidLoginCommand_whenUserValidAndTokensCreated_thenReturnAuthSideToken() {

        // Given
        AuthSideLoginCommand mockLoginCommand = new AuthSideLoginCommandBuilder().build();

        // When
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Mockito.when(userReadPort.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockUser));

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Mockito.when(loginAttemptReadPort.findByUserId(Mockito.anyString()))
                .thenReturn(mockLoginAttempt);

        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);

        AuthSideToken mockToken = new AuthSideTokenBuilder().build();
        Mockito.when(tokenUseCase.generate(Mockito.any(Claims.class)))
                .thenReturn(mockToken);

        Mockito.doNothing()
                .when(loginAttemptSavePort)
                .save(Mockito.any(AuthSideLoginAttempt.class));

        // Then
        AuthSideToken token = authenticationService.authenticate(mockLoginCommand);

        Assertions.assertEquals(mockToken.getAccessToken(), token.getAccessToken());
        Assertions.assertEquals(mockToken.getAccessTokenExpiresAt(), token.getAccessTokenExpiresAt());
        Assertions.assertEquals(mockToken.getRefreshToken(), token.getRefreshToken());

        // Verify
        Mockito.verify(userReadPort, Mockito.times(1))
                .findByUsername(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(1))
                .findByUserId(Mockito.anyString());

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .matches(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .generate(Mockito.any(Claims.class));

        Mockito.verify(loginAttemptSavePort, Mockito.times(1))
                .save(Mockito.any(AuthSideLoginAttempt.class));
    }

    @Test
    void givenInvalidLoginCommand_whenUserNotFound_thenThrowAuthSideUsernameNotValidException() {

        // Given
        AuthSideLoginCommand mockLoginCommand = new AuthSideLoginCommandBuilder().build();

        // When
        Mockito.when(userReadPort.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(
                AuthSideUsernameNotValidException.class,
                () -> authenticationService.authenticate(mockLoginCommand)
        );

        // Verify
        Mockito.verify(userReadPort, Mockito.times(1))
                .findByUsername(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(0))
                .findByUserId(Mockito.anyString());

        Mockito.verify(passwordEncoder, Mockito.times(0))
                .matches(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class));

        Mockito.verify(loginAttemptSavePort, Mockito.times(0))
                .save(Mockito.any(AuthSideLoginAttempt.class));
    }

    @Test
    void givenValidLoginCommand_whenUserLoginAttemptIsBlocked_thenThrowAuthSideUserMaximumLoginAttemptsExceedException() {

        // Given
        AuthSideLoginCommand mockLoginCommand = new AuthSideLoginCommandBuilder().build();

        // When
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Mockito.when(userReadPort.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockUser));

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .withFailedTryCount(4)
                .withLastFailedTryDate(LocalDateTime.now().minusMinutes(1))
                .build();
        Mockito.when(loginAttemptReadPort.findByUserId(Mockito.anyString()))
                .thenReturn(mockLoginAttempt);

        Mockito.doNothing()
                .when(loginAttemptSavePort)
                .save(Mockito.any(AuthSideLoginAttempt.class));

        // Then
        Assertions.assertThrows(
                AuthSideUserMaximumLoginAttemptsExceedException.class,
                () -> authenticationService.authenticate(mockLoginCommand)
        );

        // Verify
        Mockito.verify(userReadPort, Mockito.times(1))
                .findByUsername(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(1))
                .findByUserId(Mockito.anyString());

        Mockito.verify(loginAttemptSavePort, Mockito.times(1))
                .save(Mockito.any(AuthSideLoginAttempt.class));

        Mockito.verify(passwordEncoder, Mockito.times(0))
                .matches(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class));
    }

    @Test
    void givenInvalidLoginCommand_whenUserPasswordIsWrong_thenThrowAuthSidePasswordNotValidException() {

        // Given
        AuthSideLoginCommand mockLoginCommand = new AuthSideLoginCommandBuilder().build();

        // When
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Mockito.when(userReadPort.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockUser));

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Mockito.when(loginAttemptReadPort.findByUserId(Mockito.anyString()))
                .thenReturn(mockLoginAttempt);

        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(false);

        Mockito.doNothing()
                .when(loginAttemptSavePort)
                .save(Mockito.any(AuthSideLoginAttempt.class));

        // Then
        Assertions.assertThrows(
                AuthSidePasswordNotValidException.class,
                () -> authenticationService.authenticate(mockLoginCommand)
        );

        // Verify
        Mockito.verify(userReadPort, Mockito.times(1))
                .findByUsername(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(1))
                .findByUserId(Mockito.anyString());

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .matches(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(loginAttemptSavePort, Mockito.times(1))
                .save(Mockito.any(AuthSideLoginAttempt.class));

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class));
    }

    @Test
    void givenValidLoginCommand_whenUserInactive_thenThrowAuthSideUserNotActiveException() {

        // Given
        AuthSideLoginCommand mockLoginCommand = new AuthSideLoginCommandBuilder().build();

        // When
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .withStatus(AuthSideUserStatus.INACTIVE)
                .build();
        Mockito.when(userReadPort.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockUser));

        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Mockito.when(loginAttemptReadPort.findByUserId(Mockito.anyString()))
                .thenReturn(mockLoginAttempt);

        Mockito.doNothing()
                .when(loginAttemptSavePort)
                .save(Mockito.any(AuthSideLoginAttempt.class));

        // Then
        Assertions.assertThrows(
                AuthSideUserNotActiveException.class,
                () -> authenticationService.authenticate(mockLoginCommand)
        );

        // Verify
        Mockito.verify(userReadPort, Mockito.times(1))
                .findByUsername(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(1))
                .findByUserId(Mockito.anyString());

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .matches(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(loginAttemptSavePort, Mockito.times(1))
                .save(Mockito.any(AuthSideLoginAttempt.class));

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class));
    }


    @Test
    void givenValidTokenRefreshCommand_whenUserValidAndAccessTokensCreated_thenReturnAuthSideToken() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claims = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokenRefreshCommand mockTokenRefreshCommand = new AuthSideTokenRefreshCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(Mockito.anyString()))
                .thenReturn(claims);

        Mockito.doNothing()
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.when(userReadPort.findById(Mockito.anyString()))
                .thenReturn(Optional.of(mockUser));

        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .withUserId(mockUser.getId())
                .build();
        Mockito.when(loginAttemptReadPort.findByUserId(Mockito.anyString()))
                .thenReturn(mockLoginAttempt);

        AuthSideToken mockToken = new AuthSideTokenBuilder().build();
        Mockito.when(tokenUseCase.generate(Mockito.any(Claims.class), Mockito.anyString()))
                .thenReturn(mockToken);

        // Then
        AuthSideToken token = authenticationService.refreshAccessToken(mockTokenRefreshCommand);

        Assertions.assertEquals(mockToken.getAccessToken(), token.getAccessToken());
        Assertions.assertEquals(mockToken.getAccessTokenExpiresAt(), token.getAccessTokenExpiresAt());
        Assertions.assertEquals(mockToken.getRefreshToken(), token.getRefreshToken());

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(userReadPort, Mockito.times(1))
                .findById(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(1))
                .findByUserId(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .generate(Mockito.any(Claims.class), Mockito.anyString());
    }

    @Test
    void givenInvalidTokenRefreshCommand_whenTokensNotValid_thenThrowAuthSideTokenNotValidException() {

        // Given
        AuthSideTokenRefreshCommand mockTokenRefreshCommand = new AuthSideTokenRefreshCommandBuilder()
                .build();

        // When
        Mockito.doThrow(AuthSideTokenNotValidException.class)
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> authenticationService.refreshAccessToken(mockTokenRefreshCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(0))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(userReadPort, Mockito.times(0))
                .findById(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(0))
                .findByUserId(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class), Mockito.anyString());
    }

    @Test
    void givenInvalidTokenRefreshCommand_whenRefreshTokenInvalidatedAlready_thenThrowAuthSideTokenAlreadyInvalidatedException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claimsOfRefreshToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokenRefreshCommand mockTokenRefreshCommand = new AuthSideTokenRefreshCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(mockTokenRefreshCommand.getRefreshToken()))
                .thenReturn(claimsOfRefreshToken);

        Mockito.doThrow(AuthSideTokenAlreadyInvalidatedException.class)
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(Mockito.anyString());

        // Then
        Assertions.assertThrows(
                AuthSideTokenAlreadyInvalidatedException.class,
                () -> authenticationService.refreshAccessToken(mockTokenRefreshCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(userReadPort, Mockito.times(0))
                .findById(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(0))
                .findByUserId(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class), Mockito.anyString());
    }

    @Test
    void givenInvalidTokenRefreshCommand_whenUserNotFound_thenThrowAuthSideUserNotFoundException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claims = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokenRefreshCommand mockTokenRefreshCommand = new AuthSideTokenRefreshCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(Mockito.anyString()))
                .thenReturn(claims);

        Mockito.doNothing()
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.when(userReadPort.findById(Mockito.anyString()))
                .thenThrow(AuthSideUserNotFoundException.class);

        // Then
        Assertions.assertThrows(
                AuthSideUserNotFoundException.class,
                () -> authenticationService.refreshAccessToken(mockTokenRefreshCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(userReadPort, Mockito.times(1))
                .findById(Mockito.anyString());

        Mockito.verify(loginAttemptReadPort, Mockito.times(0))
                .findByUserId(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .generate(Mockito.any(Claims.class), Mockito.anyString());
    }


    @Test
    void givenValidTokenInvalidateCommand_whenTokensValid_thenInvalidateTokens() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claimsOfAccessToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();
        Claims claimsOfRefreshToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokensInvalidateCommand mockTokensInvalidateCommand = new AuthSideTokensInvalidateCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getAccessToken()))
                .thenReturn(claimsOfAccessToken);

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getRefreshToken()))
                .thenReturn(claimsOfRefreshToken);

        Mockito.doNothing()
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.doNothing()
                .when(invalidateTokenUseCase)
                .invalidateTokens(Mockito.anySet());

        // Then
        authenticationService.invalidateTokens(mockTokensInvalidateCommand);

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(2))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(2))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(2))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(1))
                .invalidateTokens(Mockito.anySet());
    }

    @Test
    void givenInvalidTokenInvalidateCommand_whenTokensNotValid_thenThrowAuthSideTokenNotValidException() {

        // Given
        AuthSideTokensInvalidateCommand mockTokensInvalidateCommand = new AuthSideTokensInvalidateCommandBuilder().build();

        // When
        Mockito.doThrow(AuthSideTokenNotValidException.class)
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        // Then
        Assertions.assertThrows(
                AuthSideTokenNotValidException.class,
                () -> authenticationService.invalidateTokens(mockTokensInvalidateCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(0))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(0))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(0))
                .invalidateTokens(Mockito.anySet());
    }

    @Test
    void givenInvalidTokenInvalidateCommand_whenAccessTokenInvalidatedAlready_thenThrowAuthSideTokenAlreadyInvalidatedException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claimsOfAccessToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();
        Claims claimsOfRefreshToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokensInvalidateCommand mockTokensInvalidateCommand = new AuthSideTokensInvalidateCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getAccessToken()))
                .thenReturn(claimsOfAccessToken);

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getRefreshToken()))
                .thenReturn(claimsOfRefreshToken);

        Mockito.doThrow(AuthSideTokenAlreadyInvalidatedException.class)
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(claimsOfAccessToken.getId());

        // Then
        Assertions.assertThrows(
                AuthSideTokenAlreadyInvalidatedException.class,
                () -> authenticationService.invalidateTokens(mockTokensInvalidateCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(1))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(1))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(1))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(0))
                .invalidateTokens(Mockito.anySet());
    }

    @Test
    void givenInvalidTokenInvalidateCommand_whenRefreshTokenInvalidatedAlready_thenThrowAuthSideTokenAlreadyInvalidatedException() {

        // Initialize
        AuthSideUser mockUser = new AuthSideUserBuilder()
                .withValidFields()
                .build();
        Claims claimsOfAccessToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();
        Claims claimsOfRefreshToken = Jwts.claims()
                .id(UUID.randomUUID().toString())
                .add(AuthSideTokenClaim.USER_ID.getValue(), mockUser.getId())
                .build();

        // Given
        AuthSideTokensInvalidateCommand mockTokensInvalidateCommand = new AuthSideTokensInvalidateCommandBuilder().build();

        // When
        Mockito.doNothing()
                .when(tokenUseCase)
                .verifyAndValidate(Mockito.anyString());

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getAccessToken()))
                .thenReturn(claimsOfAccessToken);

        Mockito.when(tokenUseCase.getPayload(mockTokensInvalidateCommand.getRefreshToken()))
                .thenReturn(claimsOfRefreshToken);

        Mockito.doNothing()
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(claimsOfAccessToken.getId());

        Mockito.doThrow(AuthSideTokenAlreadyInvalidatedException.class)
                .when(invalidateTokenUseCase)
                .validateInvalidityOfToken(claimsOfRefreshToken.getId());

        // Then
        Assertions.assertThrows(
                AuthSideTokenAlreadyInvalidatedException.class,
                () -> authenticationService.invalidateTokens(mockTokensInvalidateCommand)
        );

        // Verify
        Mockito.verify(tokenUseCase, Mockito.times(2))
                .verifyAndValidate(Mockito.anyString());

        Mockito.verify(tokenUseCase, Mockito.times(2))
                .getPayload(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(2))
                .validateInvalidityOfToken(Mockito.anyString());

        Mockito.verify(invalidateTokenUseCase, Mockito.times(0))
                .invalidateTokens(Mockito.anySet());
    }

}
