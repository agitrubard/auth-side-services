package com.agitrubard.authside.auth.adapter.in.web;

import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRoleCreateRequestToCommandMapper;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRoleToRolesResponseMapper;
import com.agitrubard.authside.auth.adapter.in.web.mapper.AuthSideRolesListRequestToCommandMapper;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRoleCreateRequest;
import com.agitrubard.authside.auth.adapter.in.web.request.AuthSideRolesListRequest;
import com.agitrubard.authside.auth.adapter.in.web.response.AuthSideRolesResponse;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRoleCreateCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideRolesListCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleCreateUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideRoleReadUseCase;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSidePageResponse;
import com.agitrubard.authside.common.adapter.in.web.response.AuthSideResponse;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final AuthSideRoleReadUseCase roleReadUseCase;
    private final AuthSideRoleCreateUseCase roleCreateUseCase;

    private final AuthSideRoleCreateRequestToCommandMapper roleCreateRequestToCommandMapper = AuthSideRoleCreateRequestToCommandMapper.initialize();
    private final AuthSideRolesListRequestToCommandMapper rolesListRequestToCommandMapper = AuthSideRolesListRequestToCommandMapper.initialize();
    private final AuthSideRoleToRolesResponseMapper roleToRolesResponseMapper = AuthSideRoleToRolesResponseMapper.initialize();

    /**
     * Endpoint to retrieve a list of authentication side roles.
     * Requires the 'role:list' authority for access.
     *
     * @param listRequest The request object containing criteria for listing authentication side roles.
     * @return An authentication side response containing a paginated list of authentication side roles.
     */
    @PostMapping("/roles")
    @PreAuthorize("hasAnyAuthority('role:list')")
    public AuthSideResponse<AuthSidePageResponse<AuthSideRolesResponse>> list(@RequestBody @Valid AuthSideRolesListRequest listRequest) {
        final AuthSideRolesListCommand listCommand = rolesListRequestToCommandMapper.map(listRequest);
        final AuthSidePage<AuthSideRole> pageOfRoles = roleReadUseCase.list(listCommand);
        return AuthSideResponse.successOf(
                AuthSidePageResponse.<AuthSideRolesResponse>builder()
                        .of(
                                pageOfRoles,
                                roleToRolesResponseMapper.map(pageOfRoles.getContent())
                        )
                        .build()
        );
    }

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
    @PreAuthorize("hasAnyAuthority('role:create', 'role:update')")
    public AuthSideResponse<Void> create(@RequestBody @Valid AuthSideRoleCreateRequest createRequest) {
        final AuthSideRoleCreateCommand createCommand = roleCreateRequestToCommandMapper.map(createRequest);
        roleCreateUseCase.create(createCommand);
        return AuthSideResponse.SUCCESS;
    }

}
