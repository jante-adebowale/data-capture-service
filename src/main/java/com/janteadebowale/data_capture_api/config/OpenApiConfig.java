package com.janteadebowale.data_capture_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-api
 * Package   : com.janteadebowale.data_capture_api.config
 **********************************************************/

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Data Capture Backend Service",
                        email = "jante.adebowale@gmail.com",
                        url = "https://janteadebowale.com"
                ),
                description = "Documentation for Data Capture Application",
                title = "Data Capture Api",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://127.0.0.1:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearer"
                )
        }
)
@SecurityScheme(
        name = "bearer",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI baseOpenAPI() {

        ApiResponse successAPI = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"success\": true,\n" +
                                        "\t\"message\": \"Successful\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Successful Request");

        ApiResponse failedApi = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("{\n" +
                                        "\t\"success\": false,\n" +
                                        "\t\"message\": \"Failed Request\",\n" +
                                        "\t\"data\": {}\n" +
                                        "}")))).description("Failed Request");

        Components components = new Components();
        components.addResponses("successApi", successAPI);
        components.addResponses("failedApi", failedApi);

        return new OpenAPI().components(components);
    }
}
