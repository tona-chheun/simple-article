package com.example.simplearticle.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {
    private static final String BEARER_AUTH = "bearerAuth";
    private static final String API_KEY_AUTH = "ApiKeyAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Article API")
                        .version("1.0")
                        .description("Article API documentation"))
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_AUTH)
                        .addList(API_KEY_AUTH))
                .components(new Components()
                        .addSecuritySchemes(API_KEY_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("x-api-key"))
                        .addSecuritySchemes(BEARER_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                ;
    }
}
