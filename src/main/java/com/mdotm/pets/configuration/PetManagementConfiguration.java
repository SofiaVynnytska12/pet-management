package com.mdotm.pets.configuration;

import com.mdotm.pets.configuration.properties.OpenApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties(value = OpenApiProperties.class)
@Import({
        OpenApiConfiguration.class,
})
@Configuration
public class PetManagementConfiguration {
}
