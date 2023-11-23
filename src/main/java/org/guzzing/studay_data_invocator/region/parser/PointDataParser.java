package org.guzzing.studay_data_invocator.region.parser;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.region.parser.dto.LocationDto;
import org.guzzing.studay_data_invocator.region.parser.dto.LocationResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class PointDataParser {

    private final GeocodeConfig geocodeConfig;

    public PointDataParser(final GeocodeConfig geocodeConfig) {
        this.geocodeConfig = geocodeConfig;
    }

    public Point addressToPoint(final String address) {
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

        if (response == null) {
            throw new GeocoderException("위치 정보 요청에 실패했습니다.");
        }

        return extractLocationFromResponse(response);
    }

    private Point extractLocationFromResponse(final LocationResponse locationResponse) {
        List<LocationDto> addresses = locationResponse.addresses();
        if (addresses == null || addresses.isEmpty()) {
            throw new GeocoderException("해당 주소에 매핑되는 위경도 값 요청에 실패했습니다.");
        }

        LocationDto locationDto = addresses.get(0);

        return getPoint(locationDto);
    }

    private Point getPoint(LocationDto locationDto) {
        GeometryFactory geometryFactory = new GeometryFactory();

        double latitude = Double.parseDouble(locationDto.y());
        double longitude = Double.parseDouble(locationDto.x());

        Coordinate coordinate = new Coordinate(latitude, longitude);

        return geometryFactory.createPoint(coordinate);
    }

}
