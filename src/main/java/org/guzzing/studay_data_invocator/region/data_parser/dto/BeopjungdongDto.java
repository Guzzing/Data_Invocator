package org.guzzing.studay_data_invocator.region.data_parser.dto;

import java.text.MessageFormat;

public record BeopjungdongDto(
        String 시도명,
        String 시군구명,
        String 읍면동명
) {

    public String getFullAddress() {
        return MessageFormat.format("{0} {1} {2}", this.시도명(), this.시군구명(), this.읍면동명());
    }

}
