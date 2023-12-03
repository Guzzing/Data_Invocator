package org.guzzing.studay_data_invocator.region.parser;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.OpenApiConfig;
import org.guzzing.studay_data_invocator.global.config.WebClientConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.guzzing.studay_data_invocator.region.parser.dto.AddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
public class AddressDataParser {

    private final OpenApiConfig openApiConfig;
    private final WebClientConfig webClientConfig;

    public AddressDataParser(
            OpenApiConfig openApiConfig,
            WebClientConfig webClientConfig
    ) {
        this.openApiConfig = openApiConfig;
        this.webClientConfig = webClientConfig;
    }

    public Address parseData(Area area) {
        try {
            AddressResponse addressResponse = requestData(area);

            return Address.of(addressResponse.admCodeNm());
        } catch (Exception e) {
            log.info("주소 정보 요청 실패 {}", e.getMessage());
            throw e;
        }
    }

    private AddressResponse requestData(Area area) {
        return webClientConfig.webClient()
                .get()
                .uri(openApiConfig.buildApiUrl(area.code()))
                .retrieve()
                .bodyToMono(AddressResponse.class)
                .doOnError(Throwable::getCause)
                .doOnSuccess(addressResponse -> validateData(area, addressResponse))
                .block();
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

}
