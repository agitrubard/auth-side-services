package com.agitrubard.authside.common.application.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@link AuthSideOpenAPIConfiguration} class is a Spring configuration class responsible for defining and configuring the OpenAPI (formerly known as Swagger) documentation for the Auth Side API.
 *
 * <p>It creates an OpenAPI bean with information about the API, including the title, version, description, and contact details. This configuration is used to generate interactive API documentation and can be accessed through Swagger UI or other API documentation tools.
 *
 * @author Agit Rubar Demir | @agitrubard
 * @version 1.0.0
 */
@Configuration
class AuthSideOpenAPIConfiguration {

    /**
     * Creates and configures an OpenAPI bean for the Auth Side API documentation.
     *
     * @return An OpenAPI instance containing API metadata.
     */
    @Bean
    public OpenAPI openAPI() {

        final Contact contact = new Contact()
                .name(" with Agit Rubar Demir")
                .email("demiragitrubar@gmail.com");

        final Info info = new Info()
                .title("Auth Side")
                .version("v1.0.0")
                .description("Auth Side APIs Documentation")
                .contact(contact);

        return new OpenAPI().info(info);
    }

}
