package com.agitrubard.authside.auth.application.config;

import com.agitrubard.authside.auth.application.filter.AuthSideBearerTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * The {@code AuthSideSecurityConfiguration} class is responsible for configuring security settings for the authentication side of the application.
 * It is annotated with {@code @Configuration}, {@code @EnableWebSecurity}, {@code @EnableMethodSecurity}, and {@code @EnableGlobalAuthentication}
 * to enable security features and customize security behaviors.
 * <p>
 * This configuration class sets up security filters, exception handling, and authentication strategies to control access to endpoints.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
class AuthSideSecurityConfiguration {

    /**
     * Creates and returns a session authentication strategy, which is responsible for managing user sessions and registrations.
     *
     * @return The session authentication strategy.
     */
    @Bean
    SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    /**
     * Configures the security filter chain, defining access control for different HTTP endpoints and request methods.
     *
     * @param httpSecurity                    The {@link HttpSecurity} instance for configuring security.
     * @param bearerTokenAuthenticationFilter The custom bearer token authentication filter.
     * @param customAuthenticationEntryPoint  The custom authentication entry point for handling authentication failures.
     * @return The configured security filter chain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                    AuthSideBearerTokenAuthenticationFilter bearerTokenAuthenticationFilter,
                                    AuthSideCustomAuthenticationEntryPoint customAuthenticationEntryPoint)
            throws Exception {

        httpSecurity
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(customAuthenticationEntryPoint))
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/authentication/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/permissions").hasAnyAuthority("role:create", "role:update")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/role").hasAnyAuthority("role:create", "role:update")
                        .anyRequest().authenticated()
                )
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(bearerTokenAuthenticationFilter, BearerTokenAuthenticationFilter.class);

        return httpSecurity.build();
    }

    /**
     * Returns a new instance of the {@link UrlBasedCorsConfigurationSource} class that registers the
     * allowed origins, methods and headers for cross-origin resource sharing (CORS).
     *
     * @return the {@link CorsConfigurationSource} instance
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Returns a new instance of the {@link BCryptPasswordEncoder} class that sets up the password encoder
     * for the application.
     *
     * @return the new instance of {@link PasswordEncoder}
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
