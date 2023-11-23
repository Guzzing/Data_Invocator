package org.guzzing.studay_data_invocator.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(value = "openapi")
public class OpenApiConfig {

    private String apiUrl;
    private String apiKey;

}
