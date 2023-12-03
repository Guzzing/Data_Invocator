package org.guzzing.studay_data_invocator.region.parser.dto;

import java.util.List;

public record PointResponses(
        String status,
        List<PointResponse> addresses,
        String errorMessage
) {

    public record PointResponse(
            String roadAddress,
            String jibunAddress,
            String englishAddress,
            String x,
            String y,
            double distance
    ) {

    }

}
