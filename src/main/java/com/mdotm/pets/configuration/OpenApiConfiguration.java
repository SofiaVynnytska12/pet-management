package com.mdotm.pets.configuration;

import com.mdotm.pets.configuration.properties.OpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI(OpenApiProperties openApiProperties) {
        return new OpenAPI()
                .info(getInfo(openApiProperties));
    }

    private Info getInfo(OpenApiProperties properties) {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion());
    }

}
