package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideLoginAttemptEntityToLoginAttemptMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideLoginAttemptToLoginAttemptEntityMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideLoginAttemptRepository;
import com.agitrubard.authside.auth.application.exception.AuthSideLoginAttemptRecordNotFoundException;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttemptBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

class AuthSideLoginAttemptAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideLoginAttemptAdapter loginAttemptAdapter;

    @Mock
    private AuthSideLoginAttemptRepository loginAttemptRepository;


    private final AuthSideLoginAttemptEntityToLoginAttemptMapper loginAttemptEntityToLoginAttemptMapper = AuthSideLoginAttemptEntityToLoginAttemptMapper.initialize();
    private final AuthSideLoginAttemptToLoginAttemptEntityMapper loginAttemptToLoginAttemptEntityMapper = AuthSideLoginAttemptToLoginAttemptEntityMapper.initialize();


    @Test
    void givenValidUserId_whenFindLoginAttemptEntity_thenReturnAuthSideLoginAttempt() {

        // Initialize
        AuthSideLoginAttemptEntity mockLoginAttemptEntity = new AuthSideLoginAttemptEntityBuilder()
                .withValidFields()
                .build();

        AuthSideLoginAttempt mockLoginAttempt = loginAttemptEntityToLoginAttemptMapper.map(mockLoginAttemptEntity);

        // Given
        String mockUserId = mockLoginAttempt.getUserId();

        // When
        Mockito.when(loginAttemptRepository.findByUserId(mockUserId))
                .thenReturn(Optional.of(mockLoginAttemptEntity));

        // Then
        AuthSideLoginAttempt loginAttempt = loginAttemptAdapter.findByUserId(mockUserId);

        Assertions.assertEquals(mockLoginAttempt.getId(), loginAttempt.getId());
        Assertions.assertEquals(mockLoginAttempt.getUserId(), loginAttempt.getUserId());
        Assertions.assertEquals(mockLoginAttempt.getLastLoginDate(), loginAttempt.getLastLoginDate());
        Assertions.assertEquals(mockLoginAttempt.getFailedTryCount(), loginAttempt.getFailedTryCount());
        Assertions.assertEquals(mockLoginAttempt.getLastFailedTryDate(), loginAttempt.getLastFailedTryDate());

        // Verify
        Mockito.verify(loginAttemptRepository, Mockito.times(1))
                .findByUserId(mockUserId);
    }

    @Test
    void givenInvalidUserId_whenCannotFindLoginAttemptEntity_thenThrowsAuthSideLoginAttemptRecordNotFoundException() {

        // Given
        String mockUserId = UUID.randomUUID().toString();

        // When
        Mockito.when(loginAttemptRepository.findByUserId(mockUserId))
                .thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(
                AuthSideLoginAttemptRecordNotFoundException.class,
                () -> loginAttemptAdapter.findByUserId(mockUserId)
        );

        // Verify
        Mockito.verify(loginAttemptRepository, Mockito.times(1))
                .findByUserId(mockUserId);
    }


    @Test
    void givenValidLoginAttempt_whenSavedLoginAttempt_thenDoNothing() {

        // Given
        AuthSideLoginAttempt mockLoginAttempt = new AuthSideLoginAttemptBuilder()
                .withValidFields()
                .build();

        // When
        AuthSideLoginAttemptEntity mockLoginAttemptEntity = loginAttemptToLoginAttemptEntityMapper
                .map(mockLoginAttempt);
        Mockito.when(loginAttemptRepository.save(Mockito.any(AuthSideLoginAttemptEntity.class)))
                .thenReturn(mockLoginAttemptEntity);

        // Then
        loginAttemptAdapter.save(mockLoginAttempt);

        // Verify
        Mockito.verify(loginAttemptRepository, Mockito.times(1))
                .save(Mockito.any(AuthSideLoginAttemptEntity.class));
    }

}
