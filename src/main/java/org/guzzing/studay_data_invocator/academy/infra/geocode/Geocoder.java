package org.guzzing.studay_data_invocator.academy.infra.geocode;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class Geocoder {

    private final GeocodeConfig geocodeConfig;

    public Geocoder(GeocodeConfig geocodeConfig) {
        this.geocodeConfig = geocodeConfig;
    }

    public Location addressToLocation(final String address) {

        WebClient webClient = WebClient.builder()
                .baseUrl(geocodeConfig.getBaseApiUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
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

        if (addresses == null || addresses.size() == 0) {
            throw new GeocoderException("해당 주소에 매핑되는 위경도 값 요청에 실패했습니다.");
        }

        Map<String, Object> addressInfo = (Map<String, Object>) addresses.get(0);
        double x = Double.parseDouble(addressInfo.get("x").toString());
        double y = Double.parseDouble(addressInfo.get("y").toString());

        return Location.of(y, x);
    }

}
