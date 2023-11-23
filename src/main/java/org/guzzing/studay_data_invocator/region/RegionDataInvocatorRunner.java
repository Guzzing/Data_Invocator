package org.guzzing.studay_data_invocator.region;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.guzzing.studay_data_invocator.region.area.RegionAreaInvocator;
import org.guzzing.studay_data_invocator.region.location.RegionLocationInvocator;
import org.guzzing.studay_data_invocator.region.location.model.HangjeongdongLocation;
import org.guzzing.studay_data_invocator.region.model.vo.BeopjeonngdongSet;
import org.springframework.stereotype.Component;

@Component
public class RegionDataInvocatorRunner {

    public static final Set<String> TARGET_REGION = Set.of("서울특별시", "경기도");

    private final RegionLocationInvocator locationInvocator;
    private final RegionAreaInvocator areaInvocator;

    public RegionDataInvocatorRunner(RegionLocationInvocator locationInvocator, RegionAreaInvocator areaInvocator) {
        this.locationInvocator = locationInvocator;
        this.areaInvocator = areaInvocator;
    }

    public void invocateLocationData() {
        locationInvocator.invocateData();
    }

    public void invocateAreaData() {
        areaInvocator.invocateData();
    }



    private List<HangjeongdongLocation> getRegions(final BeopjeonngdongSet addressSet) {
        return addressSet.beopjeongdongs().stream()
                .map(address -> {
                    try {
                        Location location = geocoderV2.addressToLocation(address.getFullAddress());

                        return HangjeongdongLocation.of(address, location);
                    } catch (GeocoderException e) {
                        log.debug("해당 주소에 매핑되는 위경도 조회에 실패하여 넘어갑니다 : {}", address);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

}
