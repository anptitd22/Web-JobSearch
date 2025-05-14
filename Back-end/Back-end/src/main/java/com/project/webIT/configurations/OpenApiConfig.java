package com.project.webIT.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "My WEB API",
                version = "2.0.0",
                description = "Description of my Awesome API"
        ),
        servers = {
                @Server(url = "http://localhost:8088", description = "Local Development Server"),
//                @Server(url = "http://45.117.179.16:8088", description = "Production Server"),
        }
)
@SecurityScheme(
        name = "bearer-key", // Can be any name,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

@Configuration
public class OpenApiConfig {
}
