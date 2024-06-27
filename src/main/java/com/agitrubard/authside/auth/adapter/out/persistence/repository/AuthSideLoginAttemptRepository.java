package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideLoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@link AuthSideLoginAttemptRepository} interface defines a repository for managing and accessing user login attempts
 * on the authentication side of the application. It extends the JpaRepository interface, providing CRUD operations for the
 * {@link AuthSideLoginAttemptEntity} entities in the database.
 * <p>
 * This repository includes methods for querying login attempts based on user identifiers, which is useful for tracking and analyzing login activities.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSideLoginAttemptRepository extends JpaRepository<AuthSideLoginAttemptEntity, String> {

    /**
     * Retrieves an {@link AuthSideLoginAttemptEntity} based on the user's unique identifier.
     *
     * @param userId The user's unique identifier.
     * @return An {@link Optional} containing the login attempt entity, or an empty {@link Optional} if no matching entry is found.
     */
    Optional<AuthSideLoginAttemptEntity> findByUserId(String userId);

}
