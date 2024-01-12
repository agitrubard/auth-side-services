package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideUserEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideUserEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideUserRepository;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import com.agitrubard.authside.auth.mapper.AuthSideUserEntityToUserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

class AuthSideUserAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideUserAdapter userAdapter;

    @Mock
    private AuthSideUserRepository userRepository;


    private final AuthSideUserEntityToUserMapper userEntityToUserMapper = AuthSideUserEntityToUserMapper.initialize();


    @Test
    void givenValidId_whenFindUserEntity_thenReturnAuthSideUser() {

        // Initialize
        AuthSideUserEntity mockUserEntity = new AuthSideUserEntityBuilder()
                .withValidFields()
                .build();

        AuthSideUser mockUser = userEntityToUserMapper.map(mockUserEntity);

        // Given
        String mockId = mockUser.getId();

        // When
        Mockito.when(userRepository.findById(mockId))
                .thenReturn(Optional.of(mockUserEntity));

        // Then
        Optional<AuthSideUser> user = userAdapter.findById(mockId);

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(mockUser.getId(), user.get().getId());
        Assertions.assertEquals(mockUser.getUsername(), user.get().getUsername());
        Assertions.assertEquals(mockUser.getEmailAddress(), user.get().getEmailAddress());
        Assertions.assertEquals(mockUser.getPassword(), user.get().getPassword());
        Assertions.assertEquals(mockUser.getFirstName(), user.get().getFirstName());
        Assertions.assertEquals(mockUser.getLastName(), user.get().getLastName());
        Assertions.assertEquals(mockUser.getStatus(), user.get().getStatus());
        Assertions.assertEquals(mockUser.getRoles().size(), user.get().getRoles().size());

        // Verify
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(mockId);
    }

    @Test
    void givenInvalidId_whenNotFindUserEntity_thenReturnOptionalEmpty() {

        // Given
        String mockId = UUID.randomUUID().toString();

        // When
        Mockito.when(userRepository.findById(mockId))
                .thenReturn(Optional.empty());

        // Then
        Optional<AuthSideUser> user = userAdapter.findById(mockId);

        Assertions.assertFalse(user.isPresent());

        // Verify
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(mockId);
    }


    @Test
    void givenValidUsername_whenFindUserEntity_thenReturnAuthSideUser() {

        // Initialize
        AuthSideUserEntity mockUserEntity = new AuthSideUserEntityBuilder()
                .withValidFields()
                .build();

        AuthSideUser mockUser = userEntityToUserMapper.map(mockUserEntity);

        // Given
        String mockUsername = mockUser.getUsername();

        // When
        Mockito.when(userRepository.findByUsername(mockUsername))
                .thenReturn(Optional.of(mockUserEntity));

        // Then
        Optional<AuthSideUser> user = userAdapter.findByUsername(mockUsername);

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(mockUser.getId(), user.get().getId());
        Assertions.assertEquals(mockUser.getUsername(), user.get().getUsername());
        Assertions.assertEquals(mockUser.getEmailAddress(), user.get().getEmailAddress());
        Assertions.assertEquals(mockUser.getPassword(), user.get().getPassword());
        Assertions.assertEquals(mockUser.getFirstName(), user.get().getFirstName());
        Assertions.assertEquals(mockUser.getLastName(), user.get().getLastName());
        Assertions.assertEquals(mockUser.getStatus(), user.get().getStatus());
        Assertions.assertEquals(mockUser.getRoles().size(), user.get().getRoles().size());

        // Verify
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(mockUsername);
    }

    @Test
    void givenInvalidUsername_whenNotFindUserEntity_thenReturnOptionalEmpty() {

        // Given
        String mockUsername = "mockUsername";

        // When
        Mockito.when(userRepository.findByUsername(mockUsername))
                .thenReturn(Optional.empty());

        // Then
        Optional<AuthSideUser> user = userAdapter.findByUsername(mockUsername);

        Assertions.assertFalse(user.isPresent());

        // Verify
        Mockito.verify(userRepository, Mockito.times(1))
                .findByUsername(mockUsername);
    }

}
