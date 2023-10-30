package org.guzzing.studay_data_invocator.global.gecode.dto;

import java.util.List;

public record LocationResponse(
        String status,
        List<LocationDto> addresses,
        String errorMessage
) {

}
