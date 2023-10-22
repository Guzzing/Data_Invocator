package org.guzzing.studay_data_invocator.academy.data_parser.gecode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.guzzing.studay_data_invocator.academy.model.vo.location.Location;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class Geocoder {

    private final GeocodeConfig geocodeConfig;

    public Geocoder(GeocodeConfig geocodeConfig) {
        this.geocodeConfig = geocodeConfig;
    }

    public Location addressToLocationV1(final String address) {
        URI uri = UriComponentsBuilder
                .fromUriString(geocodeConfig.getApiUrl())
                .queryParam("query", address)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header(geocodeConfig.getClientIdProperty(), geocodeConfig.getClientId())
                .header(geocodeConfig.getClientSecretProperty(), geocodeConfig.getClientSecret())
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return extractLocationFromResponse(result.getBody());
    }

    public Location addressToLocationV2(final String address) {
        WebClient webClient = WebClient.builder()
                .baseUrl(geocodeConfig.getApiUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(geocodeConfig.getClientIdProperty(), geocodeConfig.getClientId())
                .defaultHeader(geocodeConfig.getClientSecretProperty(), geocodeConfig.getClientSecret())
                .build();

        String response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractLocationFromResponse(response);
    }

    private Location extractLocationFromResponse(final String jsonResponse) {
        Gson gson = new Gson();

        Map<String, Object> mapData = gson.fromJson(
                jsonResponse, new TypeToken<Map<String, Object>>() {
                }.getType());
        List<Object> addresses = (List<Object>) mapData.get("addresses");

        Map<String, Object> addressInfo = (Map<String, Object>) addresses.get(0);
        double x = Double.parseDouble(addressInfo.get("x").toString());
        double y = Double.parseDouble(addressInfo.get("y").toString());

        return Location.of(y, x);
    }

}
