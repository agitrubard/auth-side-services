package com.agitrubard.authside.auth.adapter.in.web.response;

import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a response object containing authentication side roles.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRolesResponse {

    /**
     * The unique identifier of the authentication side role.
     */
    private String id;

    /**
     * The name of the authentication side role.
     */
    private String name;

    /**
     * The status of the authentication side role.
     */
    private AuthSideRoleStatus status;

    /**
     * The username of the creator of the authentication side role.
     */
    private String createdBy;

    /**
     * The timestamp when the authentication side role was created.
     */
    private LocalDateTime createdAt;

}
