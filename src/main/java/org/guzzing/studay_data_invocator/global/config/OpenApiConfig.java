package org.guzzing.studay_data_invocator.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@Configuration
@ConfigurationProperties(value = "openapi")
public class OpenApiConfig {

    private String baseApiUrl;
    private String apiKey;

    public String buildApiUrl(long admCode) {
        return UriComponentsBuilder
                .fromHttpUrl(baseApiUrl)
                .queryParam("admCode", admCode)
                .queryParam("key", apiKey)
                .build()
                .toUriString();
    }

}
