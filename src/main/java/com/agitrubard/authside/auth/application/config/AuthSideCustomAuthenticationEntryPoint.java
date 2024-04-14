package com.agitrubard.authside.auth.application.config;

import com.agitrubard.authside.common.adapter.in.web.response.AuthSideErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;

/**
 * Custom authentication entry point that implements the {@link AuthenticationEntryPoint} interface.
 * It sends an "Unauthorized" response with the HTTP status code 401 (SC_UNAUTHORIZED)
 * for unauthorized requests.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Component
class AuthSideCustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * Handles the unauthorized request by sending an "Unauthorized"
     * response with the HTTP status code 401 (SC_UNAUTHORIZED).
     *
     * @param httpServletRequest      the {@link HttpServletRequest} object representing the incoming request
     * @param httpServletResponse     the {@link HttpServletResponse} object used to send the response
     * @param authenticationException the {@link AuthenticationException} that occurred during authentication
     * @throws IOException if an I/O error occurs while sending the response
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException {

        AuthSideErrorResponse.AuthSideErrorResponseBuilder errorResponseBuilder = AuthSideErrorResponse.builder()
                .header(AuthSideErrorResponse.Header.AUTH_ERROR.getName())
                .isSuccess(false);

        if (httpServletRequest.getHeader("Authorization") != null) {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            errorResponseBuilder.httpStatus(HttpStatus.FORBIDDEN);
        } else {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            errorResponseBuilder.httpStatus(HttpStatus.UNAUTHORIZED);
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final String responseBody = OBJECT_MAPPER
                .writer(DateFormat.getDateInstance())
                .writeValueAsString(errorResponseBuilder.build());
        httpServletResponse.getOutputStream().write(responseBody.getBytes());
    }

}
