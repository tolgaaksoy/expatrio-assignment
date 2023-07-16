package com.expatrio.usermanagement.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * The type Swagger config.
 */
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "User Management API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Tolga Aksoy", email = "tolgaaksoy@email.com"
                ),
                description = "${api.description}"
        ),
        servers = @Server(
                url = "${api.server.url}",
                description = "Development server"
        )
)
public class SwaggerConfig {
}
