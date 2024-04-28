package com.agitrubard.authside.common.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for authentication side operations.
 */
@UtilityClass
public class AuthSideCollectionUtil {

    /**
     * Converts an object to a Set of the specified class type.
     *
     * @param object the object to convert
     * @param <C>    the type parameter for the class
     * @return a Set of objects of the specified class type
     */
    @SuppressWarnings({"unchecked"})
    public <C> Set<C> convertToSet(Object object) {

        if (object == null) {
            return Set.of();
        }

        return new HashSet<>((List<C>) object);
    }

}
