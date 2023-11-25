package org.guzzing.studay_data_invocator.region.parser.dto;

import java.util.List;

public record PointResponses(
        String status,
        List<PointResponse> addresses,
        String errorMessage
) {

}
