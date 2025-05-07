package com.example.newai.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("News AI Project API")
                .version("v1.0")
                .description("SW 경진대회 준비용 API 명세서")
                .contact(new Contact()
                    .name("H0LYW4T3R"))
            );
    }
}
