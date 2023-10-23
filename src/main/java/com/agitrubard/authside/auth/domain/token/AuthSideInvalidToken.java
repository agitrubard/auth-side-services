package com.agitrubard.authside.auth.domain.token;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an entity for storing information about invalidated authentication tokens.
 * This class is used to track tokens that have been invalidated and should not be accepted for further use.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@Getter
@Builder
public class AuthSideInvalidToken {

    /**
     * The unique identifier for the invalidated token.
     */
    private Long id;

    /**
     * The token ID or key that is marked as invalid.
     */
    private String tokenId;

}
