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
     * Creates a new role based on the provided request data.
     * <p>
     * This method is mapped to handle HTTP POST requests to "/role". It requires
     * the user to have either 'role:create' or 'role:update' authority to access.
     *
     * @param createRequest The request object containing data for creating the role.
     * @return An {@code AuthSideResponse} indicating the success of the operation.
     */
    @PostMapping("/role")
    public AuthSideResponse<Void> create(@RequestBody @Valid AuthSideRoleCreateRequest createRequest) {
        final AuthSideRoleCreateCommand createCommand = roleCreateRequestToCommandMapper.map(createRequest);
        roleUseCase.create(createCommand);
        return AuthSideResponse.SUCCESS;
    }

}
