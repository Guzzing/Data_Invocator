package org.guzzing.studay_data_invocator.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HolidayServiceTest {

    @Autowired
    private HolidayService holidayService;

    @Test
    void fetchAndSaveHolidaysForMultipleYears() throws InterruptedException {
        for (int year = 2004; year <= 2025; year++) {
            for (int month = 1; month <= 12; month++) {
                String yearStr = String.valueOf(year);
                String monthStr = String.format("%02d", month);

                holidayService.fetchAndSaveHolidays(yearStr, monthStr);
            }
        }
    }
}
