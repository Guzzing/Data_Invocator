package org.guzzing.studay_data_invocator.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "geocode")
public class GeocodeConfig {

    private String clientId;
    private String clientSecret;
    private String baseApiUrl;
    private String clientIdProperty;
    private String clientSecretProperty;

    public String buildApiUrl(String address) {
        return UriComponentsBuilder
                .fromHttpUrl(baseApiUrl)
                .queryParam("query", address)
                .build()
                .toUriString();
    }

}
