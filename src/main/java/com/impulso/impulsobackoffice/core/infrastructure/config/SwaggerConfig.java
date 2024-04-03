package com.impulso.impulsobackoffice.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenApi() {
        var securitySchemeName = "bearerAuth";

        var openApi = new OpenAPI().info(new Info()
                .title("RESTful API by Carlos Almanza with Spring Boot")
                .version("v1")
                .description("Esta api presta los servicios necesarios para el cliente de impulso")
                .termsOfService("url github")
                .license(new License()
                        .name("Apache 2.0")
                        .url("url de github")));

        openApi.setComponents(new Components());

        openApi.getComponents()
                .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT"));

        // Cree un objeto SecurityRequirement usando el esquema de seguridad.
        var securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        // Agregue el objeto SecurityRequirement al objeto OpenAPI.
        openApi.addSecurityItem(securityRequirement);

        return openApi;
    }
}
