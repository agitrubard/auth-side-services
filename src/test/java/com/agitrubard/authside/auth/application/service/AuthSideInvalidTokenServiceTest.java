package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.application.exception.AuthSideTokenAlreadyInvalidatedException;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideInvalidTokenSavePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Set;
import java.util.UUID;

class AuthSideInvalidTokenServiceTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideInvalidTokenService invalidTokenService;

    @Mock
    private AuthSideInvalidTokenReadPort invalidTokenReadPort;

    @Mock
    private AuthSideInvalidTokenSavePort invalidTokenSavePort;

    @Test
    void givenInvalidTokenIds_whenTokensInvalidated_thenDoNothing() {
        // Given
        Set<String> mockInvalidTokenIds = Set.of(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );

        // When
        Mockito.doNothing()
                .when(invalidTokenSavePort).saveAll(Mockito.anySet());

        // Then
        invalidTokenService.invalidateTokens(mockInvalidTokenIds);

        // Verify
        Mockito.verify(invalidTokenSavePort, Mockito.times(1))
                .saveAll(Mockito.anySet());
    }


    @Test
    void givenValidTokenId_whenInvalidTokenExist_thenThrowAuthSideTokenAlreadyInvalidatedException() {
        // Given
        String mockId = UUID.randomUUID().toString();

        // When
        Mockito.when(invalidTokenReadPort.exists(Mockito.anyString()))
                .thenReturn(true);

        // Then
        Assertions.assertThrows(
                AuthSideTokenAlreadyInvalidatedException.class,
                () -> invalidTokenService.validateInvalidityOfToken(mockId)
        );

        // Verify
        Mockito.verify(invalidTokenReadPort, Mockito.times(1))
                .exists(Mockito.anyString());
    }

    @Test
    void givenValidTokenId_whenInvalidTokenNotExist_thenDoNothing() {
        // Given
        String mockId = UUID.randomUUID().toString();

        // When
        Mockito.when(invalidTokenReadPort.exists(Mockito.anyString()))
                .thenReturn(false);

        // Then
        invalidTokenService.validateInvalidityOfToken(mockId);

        // Verify
        Mockito.verify(invalidTokenReadPort, Mockito.times(1))
                .exists(Mockito.anyString());
    }

}
