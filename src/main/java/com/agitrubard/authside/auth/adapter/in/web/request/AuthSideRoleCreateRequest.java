package com.agitrubard.authside.auth.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.Set;

/**
 * Represents a request to create a new role.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRoleCreateRequest {

    /**
     * The name of the role to be created.
     */
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    /**
     * Set of permission IDs to be associated with the role.
     */
    @NotEmpty
    private Set<@UUID String> permissionIds;

}
