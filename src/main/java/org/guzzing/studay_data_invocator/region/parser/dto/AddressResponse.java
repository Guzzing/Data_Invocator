package org.guzzing.studay_data_invocator.region.parser.dto;

import java.util.List;

public record AddressResponse(
        AddressDtos admVOList
) {

    public record AddressDtos(
            List<AddressDto> admVOList
    ) {

        public record AddressDto(
                String lowestAdmCodeNm,
                String admCodeNm,
                String admCode
        ) {

        }

    }

    public String lowestAdmCodeNm() {
        return this.admVOList()
                .admVOList()
                .get(0)
                .lowestAdmCodeNm();
    }

    public String admCodeNm() {
        return this.admVOList()
                .admVOList()
                .get(0)
                .admCodeNm();
    }

    public long admCode() {
        String code = this.admVOList()
                .admVOList()
                .get(0)
                .admCode();

        return Long.parseLong(code);
    }

}
