package com.joseph.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI taskManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .description("API para gestión de tareas internas")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Joseph Arias")
                                .email("joseph@example.com"))
                        .license(new License()
                                .name("Uso interno")));
    }
}
