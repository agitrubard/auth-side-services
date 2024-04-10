package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository interface for managing authentication side roles.
 * Extends JpaRepository for CRUD operations on AuthSideRole entities.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSideRoleRepository extends JpaRepository<AuthSideRoleEntity, String> {

    boolean existsByName(String name);

}
