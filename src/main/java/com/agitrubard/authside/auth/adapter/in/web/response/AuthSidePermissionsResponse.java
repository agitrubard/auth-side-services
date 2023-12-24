package com.agitrubard.authside.auth.adapter.in.web.response;

import com.agitrubard.authside.auth.domain.permission.model.enums.AuthSidePermissionCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

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
@Builder
public class AuthSidePermissionsResponse {

    /**
     * A map that associates PermissionCategory with a set of permission strings.
     */
    private Map<AuthSidePermissionCategory, Set<String>> permissions;

}
