package com.agitrubard.authside.auth.application.filter;

import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideInvalidTokenUseCase;
import com.agitrubard.authside.auth.application.port.in.usecase.AuthSideTokenUseCase;
import com.agitrubard.authside.auth.domain.token.enums.AuthSideTokenClaim;
import com.agitrubard.authside.common.util.AuthSideCollectionUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link AuthSideBearerTokenAuthenticationFilter} class is a custom Spring Security filter responsible for
 * authenticating API requests using bearer tokens. It extends {@link OncePerRequestFilter} to ensure it's applied
 * only once per request.
 * <p>
 * This filter validates bearer tokens and sets the user's authentication context if a valid token is present in the
 * request's authorization header. It also performs token verification and checks for token invalidity.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthSideBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthSideTokenUseCase tokenUseCase;
    private final AuthSideInvalidTokenUseCase invalidTokenUseCase;

    /**
     * Overrides the {@link OncePerRequestFilter#doFilterInternal} method to implement the token authentication logic.
     *
     * @param httpServletRequest  The incoming HTTP request.
     * @param httpServletResponse The HTTP response to be generated.
     * @param filterChain         The filter chain to execute after authentication.
     * @throws ServletException If an error occurs during the filter execution.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final @NonNull HttpServletResponse httpServletResponse,
                                    final @NonNull FilterChain filterChain) throws ServletException, IOException {


        log.debug("API Request was secured with Auth Side Security!");

        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (this.isBearerToken(authorizationHeader)) {
            final String accessToken = this.getAccessToken(authorizationHeader);

            tokenUseCase.verifyAndValidate(accessToken);

            final String accessTokenId = tokenUseCase.getPayload(accessToken).getId();
            invalidTokenUseCase.validateInvalidityOfToken(accessTokenId);

            final var authentication = this.generateAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private static final String TOKEN_PREFIX = STR."\{OAuth2AccessToken.TokenType.BEARER.getValue()} ";

    /**
     * Checks if the provided authorization header contains a bearer token.
     *
     * @param authorizationHeader The authorization header to check.
     * @return {@code true} if the header is a bearer token; otherwise, {@code false}.
     */
    private boolean isBearerToken(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    /**
     * Extracts the access token from the provided authorization header.
     *
     * @param authorizationHeader The authorization header containing the bearer token.
     * @return The extracted access token.
     */
    private String getAccessToken(String authorizationHeader) {
        return authorizationHeader.replace(TOKEN_PREFIX, "");
    }

    /**
     * Generates an authentication object based on the access token's claims and permissions.
     *
     * @param accessToken The access token to generate authentication from.
     * @return The generated {@link UsernamePasswordAuthenticationToken}.
     */
    private UsernamePasswordAuthenticationToken generateAuthentication(final String accessToken) {

        final Jws<Claims> claims = tokenUseCase.getClaims(accessToken);

        JwsHeader header = claims.getHeader();
        Claims payload = claims.getPayload();

        final Jwt jwt = new Jwt(
                accessToken,
                payload.getIssuedAt().toInstant(),
                payload.getExpiration().toInstant(),
                Map.of(
                        AuthSideTokenClaim.TYPE.getValue(), header.getType(),
                        AuthSideTokenClaim.ALGORITHM.getValue(), header.getAlgorithm()
                ),
                payload
        );

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        final Set<String> permissions = AuthSideCollectionUtil
                .convertToSet(payload.get(AuthSideTokenClaim.USER_PERMISSIONS.getValue()));
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return UsernamePasswordAuthenticationToken.authenticated(jwt, null, authorities);
    }

}
