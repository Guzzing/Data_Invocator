package org.guzzing.studay_data_invocator.region.gecode.dto;

public record LocationDto(
        String roadAddress,
        String jibunAddress,
        String englishAddress,
        String x,
        String y,
        double distance
) {

}
