package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideInvalidTokenEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideInvalidTokenRepository;
import com.agitrubard.authside.auth.domain.token.AuthSideInvalidToken;
import com.agitrubard.authside.auth.domain.token.AuthSideInvalidTokenBuilder;
import com.agitrubard.authside.auth.mapper.AuthSideInvalidTokenToInvalidTokenEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

class AuthSideInvalidTokenAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideInvalidTokenAdapter invalidTokenAdapter;

    @Mock
    private AuthSideInvalidTokenRepository invalidTokenRepository;


    private final AuthSideInvalidTokenToInvalidTokenEntityMapper invalidTokenToInvalidTokenEntityMapper = AuthSideInvalidTokenToInvalidTokenEntityMapper.initialize();


    @Test
    void givenValidInvalidTokens_whenInvalidTokensSaved_thenDoNothing() {
        // Given
        Set<AuthSideInvalidToken> mockInvalidTokens = Set.of(
                new AuthSideInvalidTokenBuilder().withValidFields().build(),
                new AuthSideInvalidTokenBuilder().withValidFields().build()
        );

        // When
        Set<AuthSideInvalidTokenEntity> mockInvalidTokenEntities = invalidTokenToInvalidTokenEntityMapper
                .map(mockInvalidTokens);
        Mockito.when(invalidTokenRepository.saveAll(Mockito.anySet()))
                .thenReturn(new ArrayList<>(mockInvalidTokenEntities));

        // Then
        invalidTokenAdapter.saveAll(mockInvalidTokens);

        // Verify
        Mockito.verify(invalidTokenRepository, Mockito.times(1))
                .saveAll(Mockito.anySet());
    }

    @Test
    void givenValidTokenId_whenInvalidTokenExist_thenReturnTrue() {
        // Given
        String mockId = UUID.randomUUID().toString();

        // When
        Mockito.when(invalidTokenRepository.existsByTokenId(Mockito.anyString()))
                .thenReturn(true);

        // Then
        boolean invalidTokenExists = invalidTokenAdapter.exists(mockId);

        Assertions.assertTrue(invalidTokenExists);

        // Verify
        Mockito.verify(invalidTokenRepository, Mockito.times(1))
                .existsByTokenId(Mockito.anyString());
    }

    @Test
    void givenValidTokenId_whenInvalidTokenNotExist_thenReturnFalse() {
        // Given
        String mockId = UUID.randomUUID().toString();

        // When
        Mockito.when(invalidTokenRepository.existsByTokenId(Mockito.anyString()))
                .thenReturn(false);

        // Then
        boolean invalidTokenExists = invalidTokenAdapter.exists(mockId);

        Assertions.assertFalse(invalidTokenExists);

        // Verify
        Mockito.verify(invalidTokenRepository, Mockito.times(1))
                .existsByTokenId(Mockito.anyString());
    }

}
