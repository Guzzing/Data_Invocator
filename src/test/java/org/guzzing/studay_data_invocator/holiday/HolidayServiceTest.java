package org.guzzing.studay_data_invocator.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    @Test
    void fetchAndSaveHolidays() {
        String year = "2023";
        String month = "01";

        holidayService.fetchAndSaveHolidays(year, month);
    }
}
