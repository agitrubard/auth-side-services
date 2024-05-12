package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideRoleEntityToRoleMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.application.port.out.AuthSideRoleReadPort;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
class AuthSideRoleReadAdapter implements AuthSideRoleReadPort {

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
                listing.toPageable()
        );

        return AuthSidePage.of(
                listing.getFilter(),
                roleEntities,
                roleEntityToRoleMapper.map(roleEntities.getContent())
        );
    }

}
