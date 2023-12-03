package org.guzzing.studay_data_invocator.holiday.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HolidayRestTemplate {

    public static final String URL = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
    private final RestTemplate restTemplate;
    private final String serviceKey;

    public HolidayRestTemplate(RestTemplate restTemplate, @Value("${holiday.api.key}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
    }

    public KoreanHolidayResponse getHolidays(String year, String month) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("solYear", year)
                .queryParam("solMonth", month);

        return restTemplate.getForObject(builder.toUriString(), KoreanHolidayResponse.class);
    }
}
