package com.agitrubard.authside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AuthSideServicesApplication is the main class that serves as the entry point
 * for the authentication side services application.
 * <p>
 * This class is annotated with @SpringBootApplication, indicating that it is the
 * starting point for Spring Boot application and includes configuration, component scanning,
 * and autoconfiguration.
 * <p>
 * The main method launches the Spring Boot application.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@SpringBootApplication
public class AuthSideServicesApplication {


    /**
     * The main method to start the authentication side services application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthSideServicesApplication.class, args);
    }

}
