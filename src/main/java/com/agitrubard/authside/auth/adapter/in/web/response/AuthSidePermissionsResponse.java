package com.agitrubard.authside.auth.adapter.in.web.response;

import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import lombok.Getter;
import lombok.Setter;

/**
 * AuthSidePermissionsResponse is a data transfer object (DTO) that represents
 * the response containing a map of permissions categorized by PermissionCategory.
 * This class is commonly used to convey permission-related information within
 * the authentication side of the application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSidePermissionsResponse {

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
    private AuthSidePermissionCategory category;

}
