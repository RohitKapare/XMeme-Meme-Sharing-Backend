package com.crio.starter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger. v3.oas.models. info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Configuration
 * Configures API documentation
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI xmemeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XMeme API")
                        . description("REST API for XMeme - A meme streaming application where users can post and view memes")
                        .version("v1. 0.0")
                        . contact(new Contact()
                                .name("XMeme Team")
                                .email("support@xmeme.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}