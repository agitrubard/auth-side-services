package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideInvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@link AuthSideInvalidTokenRepository} interface defines a repository for managing and accessing invalidated authentication tokens
 * on the authentication side of the application. It extends the JpaRepository interface, providing CRUD operations for the
 * {@link AuthSideInvalidTokenEntity} entities in the database.
 * <p>
 * This repository allows querying whether a token with a specific token ID exists, which is useful for token validation and security checks.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSideInvalidTokenRepository extends JpaRepository<AuthSideInvalidTokenEntity, Long> {

    /**
     * Checks if an invalidated token with the specified token ID exists in the repository.
     *
     * @param tokenId The unique identifier of the invalidated token.
     * @return {@code true} if a token with the specified ID exists; {@code false} otherwise.
     */
    boolean existsByTokenId(String tokenId);

}
