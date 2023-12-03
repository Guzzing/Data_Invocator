package org.guzzing.studay_data_invocator.region.parser;

import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.guzzing.studay_data_invocator.global.config.WebClientConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.parser.dto.PointResponses;
import org.guzzing.studay_data_invocator.region.parser.dto.PointResponses.PointResponse;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PointDataParser {

    private final GeocodeConfig geocodeConfig;
    private final WebClientConfig webClientConfig;

    public PointDataParser(final GeocodeConfig geocodeConfig, final WebClientConfig webClientConfig) {
        this.geocodeConfig = geocodeConfig;
        this.webClientConfig = webClientConfig;
    }

    public Point parseData(final Address address) {
        try {
            PointResponse pointResponse = requestData(address.getFullAddress())
                    .addresses()
                    .get(0);

            return getPoint(pointResponse);
        } catch (Exception e) {
            log.info("위경도 조회 실패 - address : {}", address);
            throw e;
        }
    }

    private PointResponses requestData(String address) {
        return webClientConfig.webClient()
                .get()
                .uri(geocodeConfig.buildApiUrl(address))
                .header(geocodeConfig.getClientIdProperty(), geocodeConfig.getClientId())
                .header(geocodeConfig.getClientSecretProperty(), geocodeConfig.getClientSecret())
                .retrieve()
                .bodyToMono(PointResponses.class)
                .doOnError(Throwable::getCause)
                .doOnSuccess(this::validateResponse)
                .block();
    }

    private void validateResponse(PointResponses pointResponses) {
        if (pointResponses == null || pointResponses.addresses() == null || pointResponses.addresses().isEmpty()) {
            throw new IllegalStateException("위치 정보 요청에 실패했습니다.");
        }
    }

    private Point getPoint(PointResponse pointResponse) {
        GeometryFactory geometryFactory = new GeometryFactory();

        double latitude = Double.parseDouble(pointResponse.y());
        double longitude = Double.parseDouble(pointResponse.x());

        Coordinate coordinate = new Coordinate(longitude, latitude);

        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326);

        return point;
    }

}
