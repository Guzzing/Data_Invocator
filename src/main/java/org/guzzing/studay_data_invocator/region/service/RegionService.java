package org.guzzing.studay_data_invocator.region.service;

import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.exception.AddressException;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.guzzing.studay_data_invocator.region.data_parser.BeopjungdongDataParser;
import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongResponses;
import org.guzzing.studay_data_invocator.region.data_parser.gecode.GeocoderV2;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.model.vo.Address;
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

    public RegionService(
            final BeopjungdongDataParser beopjungdongDataParser,
            final RegionRepository regionRepository,
            final GeocoderV2 geocoder
    ) {
        this.beopjungdongDataParser = beopjungdongDataParser;
        this.regionRepository = regionRepository;
        this.geocoder = geocoder;
    }

    public void importAllData() {
        int pageNumber = 1;

        AddressSet totalAddressSet = AddressSet.init();

        while (true) {
            BeopjungdongResponses beopjungdongResponses = beopjungdongDataParser.parseData(pageNumber);

            AddressSet addressSet = regulateAddressData(totalAddressSet, beopjungdongResponses);

            List<Region> regions = getRegions(addressSet);

            regionRepository.saveAll(regions);

            int currentCount = (int) beopjungdongResponses.currentCount() * pageNumber;
            int totalCount = (int) beopjungdongResponses.totalCount();

            pageNumber++;

            log.info("currentCount : {} / totalCount : {}", currentCount, totalCount);

            if (currentCount == 0) {
                break;
            }

            totalAddressSet.storeCurrentSet(addressSet);
        }
    }

    private AddressSet regulateAddressData(
            final AddressSet totalAddressSet,
            final BeopjungdongResponses beopjungdongResponses
    ) {
        AddressSet addressSet = AddressSet.init();

        beopjungdongResponses.data()
                .forEach(beopjungdongDto -> {
                    try {
                        Address address = Address.of(
                                beopjungdongDto.시도명(),
                                beopjungdongDto.시군구명(),
                                beopjungdongDto.읍면동명());

                        if (addressSet.notContainAddress(address) && totalAddressSet.notContainAddress(address)) {
                            addressSet.addAddress(address);
                        }

                    } catch (AddressException e) {
                        log.debug("올바르지 않은 주소는 정보를 생성하지 않고 넘어갑니다  : {}", beopjungdongDto);
                    }
                });

        return addressSet;
    }

    private List<Region> getRegions(final AddressSet addressSet) {
        return addressSet.addresses().stream()
                .map(address -> {
                    try {
                        Location location = geocoder.addressToLocation(address.getFullAddress());

                        return Region.of(address, location);
                    } catch (GeocoderException e) {
                        log.debug("해당 주소에 매핑되는 위경도 조회에 실패하여 넘어갑니다 : {}", address);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

}
