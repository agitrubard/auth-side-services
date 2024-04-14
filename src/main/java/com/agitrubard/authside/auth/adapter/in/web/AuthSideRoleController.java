package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleUseCase;
import com.agitrubard.authside.auth.mapper.AuthSideRoleCreateRequestToCommandMapper;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user roles.
 * <p>
 * This controller provides endpoints for creating user roles.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class AuthSideRoleController {

    private final AuthSideRoleUseCase roleUseCase;

    private final AuthSideRoleCreateRequestToCommandMapper roleCreateRequestToCommandMapper = AuthSideRoleCreateRequestToCommandMapper.initialize();

    /**
     * Endpoint for creating a new user role.
     *
     * @param createRequest The request body containing the data for creating the role.
     * @return A success response if the role is created successfully.
     */
    @PostMapping("/role")
    public AuthSideResponse<Void> create(@RequestBody @Valid AuthSideRoleCreateRequest createRequest) {
        final AuthSideRoleCreateCommand createCommand = roleCreateRequestToCommandMapper.map(createRequest);
        roleUseCase.create(createCommand);
        return AuthSideResponse.SUCCESS;
    }

}
