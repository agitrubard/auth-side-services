package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.port.in.command.AuthSideParameterSaveCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideParameterUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideParameterReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideParameterSavePort;
import com.agitrubard.authside.auth.domain.parameter.model.AuthSideParameter;
import com.agitrubard.authside.auth.mapper.AuthSideParameterSaveCommandToParameterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service responsible for managing parameters, including retrieval and storage operations.
 * <p>
 * This service interacts with the database through the read and save ports to retrieve and save parameters.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideParameterService implements AuthSideParameterUseCase {

    private final AuthSideParameterReadPort parameterReadPort;
    private final AuthSideParameterSavePort parameterSavePort;

    private final AuthSideParameterSaveCommandToParameterMapper parameterSaveCommandToParameterMapper = AuthSideParameterSaveCommandToParameterMapper.initialize();

    /**
     * Retrieves a set of parameters based on the provided prefix of the parameter name.
     *
     * @param prefixOfName The prefix of the parameter name to search for.
     * @return A set of parameters matching the provided prefix of the name.
     */
    @Override
    public Set<AuthSideParameter> getAllByPrefixOfName(final String prefixOfName) {
        return parameterReadPort.findAllByPrefixOfName(prefixOfName);
    }

    /**
     * Saves a set of parameter save commands by mapping them to parameters and then saving them.
     *
     * @param saveCommands The set of parameter save commands to be saved.
     */
    @Override
    public void saveAll(final Set<AuthSideParameterSaveCommand> saveCommands) {
        final Set<AuthSideParameter> parameters = parameterSaveCommandToParameterMapper.map(saveCommands);
        parameterSavePort.saveAll(parameters);
    }

}
