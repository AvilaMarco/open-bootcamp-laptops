package com.example.ejercicio101112.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI () {
        return new OpenAPI()
          .info(apiDetails())
          .externalDocs(externalDocs());
    }

    private Info apiDetails () {
        return new Info()
          .title("Exercise 7, 8, 9 OB")
          .description("Open Bootcamp 's Spring course, CRUD with Swagger and Testing Rest Template")
          .version("v1.0.0")
          .license(apiLicense());
    }

    private License apiLicense () {
        return new License()
          .name("Spring IO")
          .url("https://spring.io/");
    }

    private ExternalDocumentation externalDocs () {
        return new ExternalDocumentation()
          .description("Spring Course Open Bootcamp")
          .url("https://campus.open-bootcamp.com/cursos/14/leccion/352");
    }
}
