package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSidePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository interface for managing authentication side permissions.
 * Extends JpaRepository for CRUD operations on AuthSidePermissionEntity entities.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSidePermissionRepository extends JpaRepository<AuthSidePermissionEntity, String> {
}
