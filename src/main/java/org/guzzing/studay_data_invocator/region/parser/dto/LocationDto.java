package org.guzzing.studay_data_invocator.region.parser.dto;

public record LocationDto(
        String roadAddress,
        String jibunAddress,
        String englishAddress,
        String x,
        String y,
        double distance
) {

}
