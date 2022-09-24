package com.geekbrains.coreservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class OpenApiConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info( new Info().title("Spring-Web").version("1"));
    }
}
