package org.guzzing.studay_data_invocator.region.parser;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.parser.dto.PointResponses;
import org.guzzing.studay_data_invocator.region.parser.dto.PointResponse;
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

    public Point parseData(final Address address) {
        PointResponses response = requestData(address.getFullAddress());

        if (response == null) {
            throw new GeocoderException("위치 정보 요청에 실패했습니다.");
        }

        return extractLocationFromResponse(response);
    }

    private PointResponses requestData(String address) {
        return WebClient.builder()
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
                .bodyToMono(PointResponses.class)
                .block();
    }

    private Point extractLocationFromResponse(final PointResponses pointResponses) {
        List<PointResponse> addresses = pointResponses.addresses();
        if (addresses == null || addresses.isEmpty()) {
            throw new GeocoderException("해당 주소에 매핑되는 위경도 값 요청에 실패했습니다.");
        }

        PointResponse pointResponse = addresses.get(0);

        return getPoint(pointResponse);
    }

    private Point getPoint(PointResponse pointResponse) {
        GeometryFactory geometryFactory = new GeometryFactory();

        double latitude = Double.parseDouble(pointResponse.y());
        double longitude = Double.parseDouble(pointResponse.x());

        Coordinate coordinate = new Coordinate(latitude, longitude);

        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);

        return point;
    }

}
