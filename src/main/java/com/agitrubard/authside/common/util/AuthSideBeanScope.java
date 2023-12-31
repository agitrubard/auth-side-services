package com.agitrubard.authside.common.util;

import lombok.experimental.UtilityClass;

/**
 * This utility class is used to define the bean scope values in the Spring Framework.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@UtilityClass
public class AuthSideBeanScope {

    /**
     * Bean instances defined with "request" scope will create an instance that is available for the duration of an HTTP request.
     */
    public static final String SCOPE_REQUEST = "request";

}
