package com.agitrubard.authside.auth.application.service;

import com.agitrubard.authside.auth.application.exception.AuthSidePasswordNotValidException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserMaximumLoginAttemptsExceedException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserNotActiveException;
import com.agitrubard.authside.auth.application.exception.AuthSideUserNotFoundException;
import com.agitrubard.authside.auth.application.exception.AuthSideUsernameNotValidException;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideLoginCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokenRefreshCommand;
import com.agitrubard.authside.auth.application.port.in.command.AuthSideTokensInvalidateCommand;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideAuthenticationUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideInvalidTokenUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptReadPort;
import com.agitrubard.authside.auth.application.port.out.AuthSideLoginAttemptSavePort;
import com.agitrubard.authside.auth.application.port.out.AuthSideUserReadPort;
import com.agitrubard.authside.auth.domain.login.model.AuthSideLoginAttempt;
import com.agitrubard.authside.auth.domain.token.AuthSideToken;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.auth.domain.user.model.AuthSideUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * The {@code AuthSideAuthenticationService} class provides authentication and token management services.
 * <p>
 * It implements the {@link AuthSideAuthenticationUseCase} interface and handles user authentication, token generation,
 * token validation, and token invalidation.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0.0
 */
@Service
@RequiredArgsConstructor
class AuthSideAuthenticationService implements AuthSideAuthenticationUseCase {

    private final AuthSideUserReadPort userReadPort;

    private final AuthSideTokenUseCase tokenUseCase;
    private final AuthSideInvalidTokenUseCase invalidateTokenUseCase;
    private final PasswordEncoder passwordEncoder;

    private final AuthSideLoginAttemptReadPort loginAttemptReadPort;
    private final AuthSideLoginAttemptSavePort loginAttemptSavePort;

    /**
     * Authenticates a user and generates an access token.
     *
     * @param loginCommand The login command containing username and password.
     * @return An access token upon successful authentication.
     * @throws AuthSideUsernameNotValidException               If the provided username is not valid.
     * @throws AuthSidePasswordNotValidException               If the provided password is not valid.
     * @throws AuthSideUserNotActiveException                  If the user is not active.
     * @throws AuthSideUserMaximumLoginAttemptsExceedException If the user exceeds the maximum login attempts.
     */
    @Override
    public AuthSideToken authenticate(final AuthSideLoginCommand loginCommand) {

        final AuthSideUser user = userReadPort.findByUsername(loginCommand.getUsername())
                .orElseThrow(() -> new AuthSideUsernameNotValidException(loginCommand.getUsername()));

        final AuthSideLoginAttempt loginAttempt = loginAttemptReadPort.findByUserId(user.getId());

        this.validateUserLogin(loginCommand, user, loginAttempt);

        loginAttempt.success();

        final Claims claims = user.getPayload(loginAttempt);
        final AuthSideToken token = tokenUseCase.generate(claims);

        loginAttemptSavePort.save(loginAttempt);

        return token;
    }

    /**
     * Validates the user's login credentials and updates the login attempt record.
     *
     * @param loginCommand The login command containing user credentials.
     * @param user         The user trying to log in.
     * @param loginAttempt The login attempt record.
     * @throws AuthSidePasswordNotValidException               If the password is not valid.
     * @throws AuthSideUserNotActiveException                  If the user is not active.
     * @throws AuthSideUserMaximumLoginAttemptsExceedException If the user exceeded the maximum login attempts.
     */
    private void validateUserLogin(final AuthSideLoginCommand loginCommand,
                                   final AuthSideUser user,
                                   final AuthSideLoginAttempt loginAttempt) {

        try {

            if (loginAttempt.isBlocked()) {
                throw new AuthSideUserMaximumLoginAttemptsExceedException(user.getId());
            }

            final boolean isPasswordWrong = !passwordEncoder.matches(loginCommand.getPassword(), user.getPassword());
            if (isPasswordWrong) {
                throw new AuthSidePasswordNotValidException();
            }

            this.validateUserStatus(user);

        } catch (AuthSidePasswordNotValidException | AuthSideUserNotActiveException |
                 AuthSideUserMaximumLoginAttemptsExceedException exception) {

            loginAttempt.failed();
            loginAttemptSavePort.save(loginAttempt);
            throw exception;
        }
    }


    /**
     * Refreshes an access token using a refresh token.
     *
     * @param tokenRefreshCommand The token refresh command containing the refresh token.
     * @return A new access token upon successful refresh.
     * @throws AuthSideUserNotFoundException  If the user is not found.
     * @throws AuthSideUserNotActiveException If the user is not active.
     */
    @Override
    public AuthSideToken refreshAccessToken(final AuthSideTokenRefreshCommand tokenRefreshCommand) {

        final String refreshToken = tokenRefreshCommand.getRefreshToken();
        final Claims claimsOfToken = this.validateTokenAndGetTokenPayload(refreshToken);

        final String userId = claimsOfToken.get(AuthSideTokenClaim.USER_ID.getValue()).toString();

        final AuthSideUser user = userReadPort.findById(userId)
                .orElseThrow(() -> new AuthSideUserNotFoundException(userId));

        this.validateUserStatus(user);

        final AuthSideLoginAttempt loginAttempt = loginAttemptReadPort.findByUserId(user.getId());
        final Claims claimsOfUser = user.getPayload(loginAttempt);
        return tokenUseCase.generate(claimsOfUser, refreshToken);
    }

    /**
     * Validates the status of a user.
     *
     * @param user The user to validate.
     * @throws AuthSideUserNotActiveException If the user is not active.
     */
    private void validateUserStatus(final AuthSideUser user) {
        if (!user.isActive()) {
            throw new AuthSideUserNotActiveException(user.getId());
        }
    }


    /**
     * Invalidates the provided access and refresh tokens.
     *
     * @param tokenInvalidateCommand The token invalidate command containing access and refresh tokens.
     */
    @Override
    public void invalidateTokens(final AuthSideTokensInvalidateCommand tokenInvalidateCommand) {

        final String accessToken = tokenInvalidateCommand.getAccessToken();
        final String accessTokenId = this.validateTokenAndGetTokenPayload(accessToken).getId();

        final String refreshToken = tokenInvalidateCommand.getRefreshToken();
        final String refreshTokenId = this.validateTokenAndGetTokenPayload(refreshToken).getId();

        invalidateTokenUseCase.invalidateTokens(Set.of(accessTokenId, refreshTokenId));
    }

    /**
     * Validates a token and returns the token payload.
     *
     * @param token The token to validate.
     * @return The token payload.
     */
    private Claims validateTokenAndGetTokenPayload(final String token) {

        tokenUseCase.verifyAndValidate(token);

        final Claims payload = tokenUseCase.getPayload(token);
        final String tokenId = payload.getId();

        invalidateTokenUseCase.validateInvalidityOfToken(tokenId);

        return payload;
    }

}
