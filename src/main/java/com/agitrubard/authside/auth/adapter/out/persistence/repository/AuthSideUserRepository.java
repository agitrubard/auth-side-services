package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code AuthSideUserRepository} interface defines a repository for managing and accessing user profiles
 * on the authentication side of the application. It extends the JpaRepository interface, providing CRUD operations for the
 * {@link AuthSideUserEntity} entities in the database.
 * <p>
 * This repository includes a method for querying user profiles based on their usernames, which is useful for user authentication and retrieval.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSideUserRepository extends JpaRepository<AuthSideUserEntity, String> {

    /**
     * Retrieves an {@link AuthSideUserEntity} based on the user's username.
     *
     * @param username The username of the user to retrieve.
     * @return An {@link Optional} containing the user entity, or an empty {@link Optional} if no matching entry is found.
     */
    Optional<AuthSideUserEntity> findByUsername(String username);

}
