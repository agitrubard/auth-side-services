package com.agitrubard.authside.auth.adapter.out.persistence.repository;

import com.agitrubard.authside.auth.adapter.out.persistence.entity.AuthSideParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * The {@link AuthSideParameterRepository} interface defines a repository for managing and accessing authentication parameters
 * on the authentication side of the application. It extends the JpaRepository interface, providing CRUD operations for the
 * {@link AuthSideParameterEntity} entities in the database.
 * <p>
 * This repository includes methods for querying parameters based on their names and prefixes, as well as retrieving parameters by their exact name.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Repository
public interface AuthSideParameterRepository extends JpaRepository<AuthSideParameterEntity, Long> {

    /**
     * Finds a set of {@link AuthSideParameterEntity} entities whose names start with a specified prefix.
     *
     * @param prefixOfName The prefix to search for at the beginning of parameter names.
     * @return A set of parameter entities matching the specified prefix.
     */
    Set<AuthSideParameterEntity> findByNameStartingWith(String prefixOfName);

    /**
     * Retrieves an {@link AuthSideParameterEntity} based on its exact name.
     *
     * @param name The exact name of the parameter to retrieve.
     * @return An {@link Optional} containing the parameter entity, or an empty {@link Optional} if no matching entry is found.
     */
    Optional<AuthSideParameterEntity> findByName(String name);

}
