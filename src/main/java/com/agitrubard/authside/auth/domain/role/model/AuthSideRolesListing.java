package com.agitrubard.authside.auth.domain.role.model;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import com.agitrubard.authside.auth.domain.role.enums.AuthSideRoleStatus;
import com.agitrubard.authside.common.domain.model.AuthSideFiltering;
import com.agitrubard.authside.common.domain.model.AuthSideListing;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

/**
 * Represents a listing of roles on the authentication side, extending from {@link AuthSideListing}.
 * This class provides methods to generate specifications for querying role entities based on filtering criteria.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Getter
@Setter
public class AuthSideRolesListing extends AuthSideListing {

    /**
     * The filter criteria to apply when listing roles.
     */
    private Filter filter;

    /**
     * Represents the filtering parameters for roles.
     */
    @Getter
    @Setter
    public static class Filter implements AuthSideFiltering {

        /**
         * The name to filter roles by.
         */
        private String name;

        /**
         * The set of statuses to filter roles by.
         */
        private Set<AuthSideRoleStatus> statuses;
    }

    /**
     * Converts the filter criteria into a {@link Specification} object for querying roles.
     * If no filter is provided, returns a specification representing all roles.
     *
     * @return A {@link Specification} object representing the filtering criteria for roles.
     */
    public Specification<AuthSideRoleEntity> toSpecification() {

        if (this.filter == null) {
            return Specification.allOf();
        }

        Specification<AuthSideRoleEntity> specification = Specification.where(null);

        if (this.filter.name != null) {
            Specification<AuthSideRoleEntity> tempSpecification = (root, _, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), STR."%\{this.filter.name}%");
            specification = specification.and(tempSpecification);
        }

        if (this.filter.statuses != null) {
            Specification<AuthSideRoleEntity> tempSpecification = (root, _, _) ->
                    root.get("status").in(this.filter.statuses);
            specification = specification.and(tempSpecification);
        }

        return specification;
    }

}
