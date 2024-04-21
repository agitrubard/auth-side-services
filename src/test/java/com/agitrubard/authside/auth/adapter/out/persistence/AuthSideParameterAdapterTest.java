package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideParameterEntityToParameterMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideParameterRepository;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

class AuthSideParameterAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideParameterAdapter parameterAdapter;

    @Mock
    private AuthSideParameterRepository parameterRepository;


    private final AuthSideParameterEntityToParameterMapper parameterEntityToParameterMapper = AuthSideParameterEntityToParameterMapper.initialize();

    @Test
    void whenFindAllParameters_thenReturnAllPermissions() {

        // Initialize
        Set<AuthSideParameterEntity> mockParameterEntities = Set.of(
                new AuthSideParameterEntityBuilder()
                        .withId(1L)
                        .withName("AUTH_SIDE_DEFAULT1")
                        .withDefinition("AUTH_SIDE_DEFAULT1")
                        .build(),
                new AuthSideParameterEntityBuilder()
                        .withId(2L)
                        .withName("AUTH_SIDE_DEFAULT2")
                        .withDefinition("AUTH_SIDE_DEFAULT2")
                        .build()
        );

        Set<AuthSideParameter> mockParameters = parameterEntityToParameterMapper.map(mockParameterEntities);

        // When
        Mockito.when(parameterRepository.findByNameStartingWith(Mockito.anyString()))
                .thenReturn(mockParameterEntities);

        // Then
        Set<AuthSideParameter> parameters = parameterAdapter.findAllByPrefixOfName("AUTH_SIDE");

        Assertions.assertEquals(mockParameters.size(), parameters.size());

        // Verify
        Mockito.verify(parameterRepository, Mockito.times(1))
                .findByNameStartingWith(Mockito.anyString());
    }

    @Test
    void whenParametersAreNotExist_thenReturnEmptySet() {

        // Initialize
        Set<AuthSideParameterEntity> mockParameterEntities = Set.of();

        Set<AuthSideParameter> mockParameters = parameterEntityToParameterMapper.map(mockParameterEntities);

        // When
        Mockito.when(parameterRepository.findByNameStartingWith(Mockito.anyString()))
                .thenReturn(mockParameterEntities);

        // Then
        Set<AuthSideParameter> parameters = parameterAdapter.findAllByPrefixOfName("AUTH_SIDE");

        Assertions.assertEquals(mockParameters.size(), parameters.size());

        // Verify
        Mockito.verify(parameterRepository, Mockito.times(1))
                .findByNameStartingWith(Mockito.anyString());
    }


    @Test
    void givenValidParameters_whenParametersNotExist_thenSaveParameters() {

        // Initialize
        AuthSideParameterEntity firstMockParameterEntity = new AuthSideParameterEntityBuilder()
                .withId(1L)
                .withName("AUTH_SIDE_DEFAULT1")
                .withDefinition("AUTH_SIDE_DEFAULT1")
                .build();
        AuthSideParameterEntity secondMockParameterEntity = new AuthSideParameterEntityBuilder()
                .withId(2L)
                .withName("AUTH_SIDE_DEFAULT2")
                .withDefinition("AUTH_SIDE_DEFAULT2")
                .build();
        Set<AuthSideParameterEntity> mockParameterEntities = Set.of(
                firstMockParameterEntity,
                secondMockParameterEntity
        );

        AuthSideParameter firstMockParameter = parameterEntityToParameterMapper.map(firstMockParameterEntity);
        AuthSideParameter secondMockParameter = parameterEntityToParameterMapper.map(secondMockParameterEntity);
        Set<AuthSideParameter> mockParameters = parameterEntityToParameterMapper.map(mockParameterEntities);

        // When
        Mockito.when(parameterRepository.findByName(firstMockParameter.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(parameterRepository.findByName(secondMockParameter.getName()))
                .thenReturn(Optional.empty());

        // Then
        parameterAdapter.saveAll(mockParameters);

        // Verify
        Mockito.verify(parameterRepository, Mockito.times(2))
                .findByName(Mockito.anyString());
        Mockito.verify(parameterRepository, Mockito.times(1))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenValidParameters_whenParametersExist_thenUpdateParameters() {

        // Initialize
        AuthSideParameterEntity firstMockParameterEntity = new AuthSideParameterEntityBuilder()
                .withId(1L)
                .withName("AUTH_SIDE_DEFAULT1")
                .withDefinition("AUTH_SIDE_DEFAULT1")
                .build();
        AuthSideParameterEntity secondMockParameterEntity = new AuthSideParameterEntityBuilder()
                .withId(2L)
                .withName("AUTH_SIDE_DEFAULT2")
                .withDefinition("AUTH_SIDE_DEFAULT2")
                .build();
        Set<AuthSideParameterEntity> mockParameterEntities = Set.of(
                firstMockParameterEntity,
                secondMockParameterEntity
        );

        AuthSideParameter firstMockParameter = parameterEntityToParameterMapper.map(firstMockParameterEntity);
        AuthSideParameter secondMockParameter = parameterEntityToParameterMapper.map(secondMockParameterEntity);
        Set<AuthSideParameter> mockParameters = parameterEntityToParameterMapper.map(mockParameterEntities);

        // When
        Mockito.when(parameterRepository.findByName(firstMockParameter.getName()))
                .thenReturn(Optional.of(firstMockParameterEntity));
        Mockito.when(parameterRepository.findByName(secondMockParameter.getName()))
                .thenReturn(Optional.of(secondMockParameterEntity));

        // Then
        parameterAdapter.saveAll(mockParameters);

        // Verify
        Mockito.verify(parameterRepository, Mockito.times(2))
                .findByName(Mockito.anyString());
        Mockito.verify(parameterRepository, Mockito.times(1))
                .saveAll(Mockito.anyList());
    }

    @Test
    void givenEmptyList_whenParametersNotExist_thenDoNothing() {

        // Initialize
        Set<AuthSideParameterEntity> mockParameterEntities = Set.of();

        Set<AuthSideParameter> mockParameters = parameterEntityToParameterMapper.map(mockParameterEntities);

        // Then
        parameterAdapter.saveAll(mockParameters);

        // Verify
        Mockito.verify(parameterRepository, Mockito.times(0))
                .findByName(Mockito.anyString());
        Mockito.verify(parameterRepository, Mockito.times(1))
                .saveAll(Mockito.anyList());
    }

}
