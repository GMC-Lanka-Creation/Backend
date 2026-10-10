package com.gmc.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_AUTH = "bearerAuth";
    private static final String AUTH_PATH_PREFIX = "/api/auth";
    private static final String PRODUCTS_PATH_PREFIX = "/api/products";

    @Bean
    public OpenAPI backendOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GMC Lanka Backend API")
                        .description("API documentation for GMC Lanka Creations")
                        .version("v1"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer bearerAuthenticationCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) ->
                pathItem.readOperationsMap().forEach((method, operation) -> {
                    boolean publicAuthEndpoint = matchesPath(path, AUTH_PATH_PREFIX);
                    boolean publicProductRead = matchesPath(path, PRODUCTS_PATH_PREFIX)
                            && method == PathItem.HttpMethod.GET;
                    if (!publicAuthEndpoint && !publicProductRead) {
                        operation.addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
                    }
                }));
    }

    private boolean matchesPath(String path, String pathPrefix) {
        return path.equals(pathPrefix) || path.startsWith(pathPrefix + "/");
    }
}

