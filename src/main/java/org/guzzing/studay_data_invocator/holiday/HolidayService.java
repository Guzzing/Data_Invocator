package org.guzzing.studay_data_invocator.holiday;

import java.util.List;
import org.guzzing.studay_data_invocator.holiday.resttemplate.HolidayRestTemplate;
import org.guzzing.studay_data_invocator.holiday.resttemplate.KoreanHolidayResponse;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private final HolidayRestTemplate holidayRestTemplate;
    private final KoreanHolidayRepository koreanHolidayRepository;

    public HolidayService(HolidayRestTemplate holidayRestTemplate, KoreanHolidayRepository koreanHolidayRepository) {
        this.holidayRestTemplate = holidayRestTemplate;
        this.koreanHolidayRepository = koreanHolidayRepository;
    }

    public void fetchAndSaveHolidays(String year, String month) {
        KoreanHolidayResponse response = holidayRestTemplate.getHolidays(year, month);

        List<KoreanHoliday> holidays = response.getBody().getItems().stream()
                .map(item -> new KoreanHoliday(item.getDateName(), item.getLocdate()))
                .toList();

        koreanHolidayRepository.saveAll(holidays);
    }
}
