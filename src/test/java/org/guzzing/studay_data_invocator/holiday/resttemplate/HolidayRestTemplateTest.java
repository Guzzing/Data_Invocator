package org.guzzing.studay_data_invocator.holiday.resttemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class HolidayRestTemplateTest {

    @Autowired
    private HolidayRestTemplate holidayRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${holiday.api.key}")
    private String testServiceKey;

    @BeforeEach
    public void setup() {
        System.out.println(restTemplate);
        System.out.println(testServiceKey);
        holidayRestTemplate = new HolidayRestTemplate(restTemplate, testServiceKey);
    }

    @Test
    void testGetHolidays() {
        String year = "2023";
        String month = "01";

        KoreanHolidayResponse response = holidayRestTemplate.getHolidays(year, month);

        assertThat(response).isNotNull();
        assertThat(response.getBody().getItems()).hasSize(5);
    }
}
