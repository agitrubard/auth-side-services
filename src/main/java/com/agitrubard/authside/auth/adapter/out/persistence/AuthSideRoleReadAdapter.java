package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideRoleEntityToRoleMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleReadPort;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.application.port.out.AuthSideListPort;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Adapter class that implements both the {@link AuthSideRoleReadPort} and {@link AuthSideListPort} interfaces.
 * This adapter is responsible for reading authentication side roles and handling listing operations.
 * It interacts with the {@link AuthSideRoleRepository} for data access.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideRoleReadAdapter implements AuthSideRoleReadPort, AuthSideListPort {

    private final AuthSideRoleRepository roleRepository;

    private final AuthSideRoleEntityToRoleMapper roleEntityToRoleMapper = AuthSideRoleEntityToRoleMapper.initialize();

    /**
     * Retrieves a page of authentication side roles based on the provided listing criteria.
     *
     * @param listing The criteria for listing authentication side roles.
     * @return A page of authentication side roles.
     */
    @Override
    public AuthSidePage<AuthSideRole> findAll(AuthSideRolesListing listing) {

        final Page<AuthSideRoleEntity> roleEntities = roleRepository.findAll(
                listing.toSpecification(),
                this.toPageable(listing.getPagination(), listing.getSort())
        );

        return AuthSidePage.of(
                roleEntities,
                roleEntityToRoleMapper.map(roleEntities.getContent())
        );
    }

}
