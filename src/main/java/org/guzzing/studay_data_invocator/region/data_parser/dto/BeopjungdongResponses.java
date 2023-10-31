package org.guzzing.studay_data_invocator.region.data_parser.dto;

import java.util.List;

public record BeopjungdongResponses(
        List<BeopjungdongDto> data,
        double currentCount,
        double totalCount
) {

}
