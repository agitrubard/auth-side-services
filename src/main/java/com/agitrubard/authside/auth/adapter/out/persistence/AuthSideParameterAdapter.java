package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideParameterEntityToParameterMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideParameterToParameterEntityMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideParameterRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSideParameterReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideParameterSavePort;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code AuthSideParameterAdapter} class is an adapter component responsible for bridging the gap between the application's
 * business logic and the data access layer. It implements the interfaces {@link AuthSideParameterReadPort} and
 * {@link AuthSideParameterSavePort}, providing methods to read and save authentication parameters.
 * <p>
 * This adapter relies on the {@link AuthSideParameterRepository} for data storage and uses mappers to convert between
 * business objects ({@link AuthSideParameter}) and entity objects ({@link AuthSideParameterEntity}).
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideParameterAdapter implements AuthSideParameterReadPort, AuthSideParameterSavePort {

    private final AuthSideParameterRepository parameterRepository;

    private final AuthSideParameterEntityToParameterMapper parameterEntityToParameterMapper = AuthSideParameterEntityToParameterMapper.initialize();
    private final AuthSideParameterToParameterEntityMapper parameterToParameterEntityMapper = AuthSideParameterToParameterEntityMapper.initialize();

    /**
     * Finds and returns a set of authentication parameters whose names start with the specified prefix.
     *
     * @param prefixOfName The prefix to search for at the beginning of parameter names.
     * @return A set of authentication parameters matching the specified prefix.
     */
    @Override
    public Set<AuthSideParameter> findAllByPrefixOfName(String prefixOfName) {
        return parameterRepository.findByNameStartingWith(prefixOfName)
                .stream()
                .map(parameterEntityToParameterMapper::map)
                .collect(Collectors.toSet());
    }

    /**
     * Saves a set of authentication parameters in the data store. If a parameter with the same name already exists, its definition is updated.
     *
     * @param parameters The set of authentication parameters to save.
     */
    @Override
    public void saveAll(final Set<AuthSideParameter> parameters) {

        List<AuthSideParameterEntity> parameterEntitiesToBeSaved = new ArrayList<>();
        parameters.forEach(parameter -> {

            final Optional<AuthSideParameterEntity> parameterFromDatabase = parameterRepository
                    .findByName(parameter.getName());

            if (parameterFromDatabase.isPresent()) {
                parameterFromDatabase.get().setDefinition(parameter.getDefinition());
                parameterEntitiesToBeSaved.add(parameterFromDatabase.get());
                return;
            }

            final AuthSideParameterEntity parameterEntity = parameterToParameterEntityMapper.map(parameter);
            parameterEntitiesToBeSaved.add(parameterEntity);
        });

        parameterRepository.saveAll(parameterEntitiesToBeSaved);
    }

}
