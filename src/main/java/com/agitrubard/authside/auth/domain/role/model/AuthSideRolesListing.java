package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.AuthSideFiltering;
import com.agitrubard.authside.common.domain.model.AuthSideListing;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@Getter
@Setter
public class AuthSideRolesListing extends AuthSideListing {

    private Filter filter;

    @Getter
    @Setter
    public static class Filter implements AuthSideFiltering {
        private String name;
        private Set<AuthSideRoleStatus> statuses;
    }

    public Specification<AuthSideRoleEntity> toSpecification() {

        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<AuthSideRoleEntity> specification = Specification.where(null);

        if (this.filter.name != null) {
            Specification<AuthSideRoleEntity> tempSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), this.filter.name);
            specification.and(tempSpecification);
        }

        if (this.filter.statuses != null) {
            Specification<AuthSideRoleEntity> tempSpecification = (root, _, _) ->
                    root.get("status").in(this.filter.statuses);
            specification.and(tempSpecification);
        }

        return Specification.allOf();
    }

}
