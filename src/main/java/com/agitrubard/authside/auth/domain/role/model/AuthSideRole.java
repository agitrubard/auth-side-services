package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * The {@link AuthSideRole} class represents a role in the authentication system, providing details such as the role's ID,
 * name, status, and associated permissions. It extends the {@link AuthSideBaseDomainModel} class, which serves as the
 * base class for domain models in the authentication system.
 * <p>
 * The class includes methods to check the status of the role, providing information about whether the role is active
 * or not.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
@SuperBuilder
public class AuthSideRole extends AuthSideBaseDomainModel {

    /**
     * The unique identifier for the role.
     */
    private String id;

    /**
     * The name of the role, which provides a human-readable label for the role.
     */
    private String name;

    /**
     * The status of the role, which indicates whether the role is active, passive, or deleted.
     */
    @Builder.Default
    private AuthSideRoleStatus status = AuthSideRoleStatus.ACTIVE;

    /**
     * A set of permissions associated with the role. Permissions define what actions or resources users with this role can access.
     */
    private Set<AuthSidePermission> permissions;

}
