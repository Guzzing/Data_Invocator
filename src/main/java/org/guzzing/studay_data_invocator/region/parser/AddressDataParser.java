package org.guzzing.studay_data_invocator.region.parser;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.OpenApiConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.guzzing.studay_data_invocator.region.parser.dto.AddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class AddressDataParser {

    private final OpenApiConfig config;

    public AddressDataParser(OpenApiConfig config) {
        this.config = config;
    }

    public Address parseData(Area area) {
        AddressResponse addressResponse = requestData(area.code());

        try {
            validateData(area, addressResponse);

            return Address.of(addressResponse.admCodeNm());
        } catch (NullPointerException e) {
            log.info("위경도 조회 실패!");
            log.info("area: {}", area);
            log.info("addressResponse: {}", addressResponse);
            throw e;
        }
    }

    private void validateData(Area area, AddressResponse addressResponse) {
        if (area == null) {
            throw new IllegalStateException("영역 정보가 없습니다.");
        }

        if (addressResponse == null) {
            throw new IllegalStateException("법정동 응답이 없습니다.");
        }

        if (area.code() != addressResponse.admCode()) {
            throw new IllegalStateException("법정동 코드가 일치하지 않습니다.");
        }

        if (!Objects.equals(area.name(), addressResponse.lowestAdmCodeNm())) {
            throw new IllegalStateException("읍면동 이름이 일치하지 않습니다.");
        }
    }

    private AddressResponse requestData(long code) {
        return WebClient.builder()
                .baseUrl(config.getApiUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("admCode", code)
                        .queryParam("key", config.getApiKey())
                        .build())
                .retrieve()
                .bodyToMono(AddressResponse.class)
                .block();
    }
}
