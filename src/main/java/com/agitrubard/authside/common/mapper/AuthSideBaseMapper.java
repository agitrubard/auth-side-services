package com.agitrubard.authside.common.mapper;

import java.util.List;
import java.util.Set;

/**
 * {@link AuthSideBaseMapper} defines methods for mapping between objects of type {@link S} (source)
 * and {@link T} (target). Implementing classes or interfaces are responsible for providing
 * implementations for these mapping methods.
 * <p>
 * This interface supports mapping operations for:
 * <ul>
 * <li>Mapping a single source object ({@link S}) to a target object ({@link T}) using {@link #map(Object)}</li>
 * <li>Mapping a collection of source objects ({@link List<S>}) to a list of target objects ({@link List<T>})
 * using {@link #map(List)}</li>
 * <li>Mapping a set of source objects ({@link Set<S>}) to a set of target objects ({@link Set<T>})
 * using {@link #map(Set)}</li>
 * </ul>
 * </p>
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * @Mapper
 * public class AuthSideUserEntityToDomainMapper implements AuthSideBaseMapper<AuthSideUserEntity, AuthSideUser> {
 *
 *     static AuthSideUserEntityToDomainMapper initialize() {
 *         return Mappers.getMapper(AuthSideUserEntityToDomainMapper.class);
 *     }
 *
 * }
 *
 * }</pre>
 *
 * @param <S> the type of the source object to map from
 * @param <T> the type of the target object to map to
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
public interface AuthSideBaseMapper<S, T> {

    /**
     * Maps the specified source object to an object of type {@link T}.
     *
     * @param source the source object to be mapped
     * @return the resulting object of type {@link T}
     */
    T map(S source);

    /**
     * Maps the specified collection of source objects to a list of objects of type {@link T}.
     *
     * @param sources the collection of source objects to be mapped
     * @return the list of resulting objects of type {@link T}
     */
    List<T> map(List<S> sources);

    /**
     * Maps the specified set of source objects to a set of objects of type {@link T}.
     *
     * @param sources the set of source objects to be mapped
     * @return the set of resulting objects of type {@link T}
     */
    Set<T> map(Set<S> sources);

}
