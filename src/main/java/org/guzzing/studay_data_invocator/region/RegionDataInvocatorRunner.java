package org.guzzing.studay_data_invocator.region;

import java.util.Set;
import org.guzzing.studay_data_invocator.region.parser.AddressDataParser;
import org.guzzing.studay_data_invocator.region.parser.AreaDataParser;
import org.guzzing.studay_data_invocator.region.parser.PointDataParser;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.springframework.stereotype.Component;

@Component
public class RegionDataInvocatorRunner {

    public static final Set<String> TARGET_REGION = Set.of("서울특별시", "경기도");

    private final AreaDataParser areaDataParser;
    private final AddressDataParser addressDataParser;
    private final PointDataParser pointDataParser;
    private final RegionService service;

    public RegionDataInvocatorRunner(
            AreaDataParser areaDataParser,
            AddressDataParser addressDataParser,
            PointDataParser pointDataParser,
            RegionService service
    ) {
        this.areaDataParser = areaDataParser;
        this.addressDataParser = addressDataParser;
        this.pointDataParser = pointDataParser;
        this.service = service;
    }

    public void invocateData() {

    }

}
