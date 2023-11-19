package com.agitrubard.authside.auth.mapper;

import com.agitrubard.authside.auth.adapter.in.web.response.AuthSidePermissionsResponse;
import com.agitrubard.authside.auth.domain.permission.enums.PermissionCategory;
import com.agitrubard.authside.auth.domain.permission.model.AuthSidePermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AuthSidePermissionToPermissionsResponseMapper is a mapper interface responsible for
 * converting a Set of AuthSidePermission objects to an AuthSidePermissionsResponse object.
 * It groups permissions by PermissionCategory and creates a response with a map of permissions.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Mapper
public interface AuthSidePermissionToPermissionsResponseMapper {

    /**
     * Maps a Set of AuthSidePermission objects to an AuthSidePermissionsResponse object.
     *
     * @param permissions Set of AuthSidePermission objects to be mapped.
     * @return An AuthSidePermissionsResponse object representing the mapped permissions.
     */
    default AuthSidePermissionsResponse map(Set<AuthSidePermission> permissions) {
        final Map<PermissionCategory, Set<String>> permissionsMap = permissions.stream()
                .collect(
                        Collectors.groupingBy(
                                AuthSidePermission::getCategory,
                                Collectors.mapping(
                                        AuthSidePermission::getName,
                                        Collectors.toSet()
                                )
                        )
                );
        return AuthSidePermissionsResponse.builder()
                .permissions(permissionsMap)
                .build();
    }

    /**
     * Initializes and returns an instance of AuthSidePermissionToPermissionsResponseMapper.
     *
     * @return An instance of AuthSidePermissionToPermissionsResponseMapper.
     */
    static AuthSidePermissionToPermissionsResponseMapper initialize() {
        return Mappers.getMapper(AuthSidePermissionToPermissionsResponseMapper.class);
    }

}
