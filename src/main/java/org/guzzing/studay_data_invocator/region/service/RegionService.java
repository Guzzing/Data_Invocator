package org.guzzing.studay_data_invocator.region.service;

import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.exception.AddressException;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.global.gecode.GeocoderV2;
import org.guzzing.studay_data_invocator.global.location.Address;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.guzzing.studay_data_invocator.region.data_parser.BeopjungdongDataParser;
import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongDto;
import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongResponses;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.repository.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class RegionService {

    private final BeopjungdongDataParser beopjungdongDataParser;
    private final RegionRepository regionRepository;
    private final GeocoderV2 geocoder;

    public RegionService(BeopjungdongDataParser beopjungdongDataParser, RegionRepository regionRepository,
            GeocoderV2 geocoder) {
        this.beopjungdongDataParser = beopjungdongDataParser;
        this.regionRepository = regionRepository;
        this.geocoder = geocoder;
    }

    public void importAllData() {
        int pageNumber = 1;

        int currentCount = 1;
        int totalCount = currentCount + 1;

        while (currentCount != 0) {
            BeopjungdongResponses beopjungdongResponses = beopjungdongDataParser.parseData(pageNumber);

            beopjungdongResponses.data().stream()
                    .filter(this::isTargetRegion)
                    .filter(beopjungdongDto -> beopjungdongDto.읍면동명() != null)
                    .forEach(beopjungdongDto -> {
                        try {
                            String fullAddress = beopjungdongDto.getFullAddress();

                            Address address = Address.of(fullAddress);
                            Location location = geocoder.addressToLocation(fullAddress);

                            Region region = Region.of(address, location);

                            regionRepository.save(region);
                        } catch (GeocoderException e) {

                        } catch (AddressException e) {

                        }
                    });

            currentCount = (int) beopjungdongResponses.currentCount() * pageNumber;
            totalCount = (int) beopjungdongResponses.totalCount();

            pageNumber++;

            log.info("currentCount : {} / totalCount : {}", currentCount, totalCount);
        }
    }

    private boolean isTargetRegion(BeopjungdongDto beopjungdongDto) {
        return beopjungdongDto.시도명().contains("서울시") || beopjungdongDto.시도명().contains("경기도");
    }
}
