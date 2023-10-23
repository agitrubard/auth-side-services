package com.agitrubard.authside.auth.domain.permission.model;

import com.agitrubard.authside.auth.domain.permission.enums.PermissionCategory;
import com.agitrubard.authside.common.domain.model.AuthSideBaseDomainModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * The {@code AuthSidePermission} class represents a permission entity in the authentication system. Permissions are
 * used to define access rights for various categories or features within the application. Each permission has a unique
 * identifier, a name, and belongs to a specific category.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@SuperBuilder
public class AuthSidePermission extends AuthSideBaseDomainModel {

    /**
     * The unique identifier for the permission.
     */
    private String id;

    /**
     * The name or description of the permission.
     */
    private String name;

    /**
     * The category to which the permission belongs. It categorizes permissions based on their usage within the application.
     */
    private PermissionCategory category;

}
