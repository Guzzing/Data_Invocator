package org.guzzing.studay_data_invocator.global.gecode;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.global.gecode.dto.LocationDto;
import org.guzzing.studay_data_invocator.global.gecode.dto.LocationResponse;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class GeocoderV2 {

    private final GeocodeConfig geocodeConfig;

    public GeocoderV2(final GeocodeConfig geocodeConfig) {
        this.geocodeConfig = geocodeConfig;
    }

    public Location addressToLocation(final String address) {
        LocationResponse response = WebClient.builder()
                .baseUrl(geocodeConfig.getApiUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultHeader(geocodeConfig.getClientIdProperty(), geocodeConfig.getClientId())
                .defaultHeader(geocodeConfig.getClientSecretProperty(), geocodeConfig.getClientSecret())
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", address)
                        .build())
                .retrieve()
                .bodyToMono(LocationResponse.class)
                .block();

        return extractLocationFromResponse(response);
    }

    private Location extractLocationFromResponse(final LocationResponse locationResponse) {
        List<LocationDto> addresses = locationResponse.addresses();
        if (addresses == null || addresses.isEmpty()) {
            throw new GeocoderException("해당 주소에 매핑되는 위경도 값 요청에 실패했습니다.");
        }

        LocationDto locationDto = addresses.get(0);

        double latitute = Double.parseDouble(locationDto.y());
        double longitute = Double.parseDouble(locationDto.x());

        return Location.of(latitute, longitute);
    }

}
