package com.agitrubard.authside.auth.adapter.out.persistence;

import com.agitrubard.authside.AuthSideUnitTest;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntityBuilder;
import com.agitrubard.authside.auth.adapter.out.persistence.mapper.AuthSideRoleEntityToRoleMapper;
import com.agitrubard.authside.auth.adapter.out.persistence.repository.AuthSideRoleRepository;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRole;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListing;
import com.agitrubard.authside.auth.domain.role.model.AuthSideRolesListingBuilder;
import com.agitrubard.authside.common.domain.model.AuthSidePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

class AuthSideRoleReadAdapterTest extends AuthSideUnitTest {

    @InjectMocks
    private AuthSideRoleReadAdapter roleReadAdapter;

    @Mock
    private AuthSideRoleRepository roleRepository;


    private final AuthSideRoleEntityToRoleMapper roleEntityToRoleMapper = AuthSideRoleEntityToRoleMapper.initialize();

    @Test
    @SuppressWarnings("unchecked")
    void givenValidRolesListing_whenRolesFound_thenReturnPageOfRoles() {
        // Given
        AuthSideRolesListing mockListing = new AuthSideRolesListingBuilder()
                .withValidFields()
                .build();

        // When
        List<AuthSideRoleEntity> roleEntities = List.of(
                new AuthSideRoleEntityBuilder().withValidFields().build()
        );
        Page<AuthSideRoleEntity> pageOfMockRoleEntities = new PageImpl<>(roleEntities);
        Mockito.when(roleRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(pageOfMockRoleEntities);

        AuthSidePage<AuthSideRole> pageOfMockRoles = AuthSidePage.of(
                mockListing.getFilter(),
                pageOfMockRoleEntities,
                roleEntityToRoleMapper.map(pageOfMockRoleEntities.getContent())
        );


        // Then
        AuthSidePage<AuthSideRole> pageOfRoles = roleReadAdapter.findAll(mockListing);

        Assertions.assertEquals(pageOfMockRoles.getContent().size(), pageOfRoles.getContent().size());
        Assertions.assertEquals(pageOfMockRoles.getContent().getFirst().getId(), pageOfRoles.getContent().getFirst().getId());
        Assertions.assertEquals(pageOfMockRoles.getContent().getFirst().getName(), pageOfRoles.getContent().getFirst().getName());
        Assertions.assertEquals(pageOfMockRoles.getContent().getFirst().getPermissions().size(), pageOfRoles.getContent().getFirst().getPermissions().size());
        Assertions.assertEquals(pageOfMockRoles.getPageNumber(), pageOfRoles.getPageNumber());
        Assertions.assertEquals(pageOfMockRoles.getPageSize(), pageOfRoles.getPageSize());
        Assertions.assertEquals(pageOfMockRoles.getTotalPageCount(), pageOfRoles.getTotalPageCount());
        Assertions.assertEquals(pageOfMockRoles.getTotalElementCount(), pageOfRoles.getTotalElementCount());
        Assertions.assertEquals(pageOfMockRoles.getOrderedBy(), pageOfRoles.getOrderedBy());
        Assertions.assertEquals(pageOfMockRoles.getFilteredBy(), pageOfRoles.getFilteredBy());

        // Verify
        Mockito.verify(roleRepository).findAll(
                Mockito.any(Specification.class),
                Mockito.any(Pageable.class)
        );

    }

}
