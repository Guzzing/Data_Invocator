package org.guzzing.studay_data_invocator.region;

import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeoJsonConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.parser.AddressDataParser;
import org.guzzing.studay_data_invocator.region.parser.AreaDataParser;
import org.guzzing.studay_data_invocator.region.parser.PointDataParser;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegionDataInvocatorRunner {

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

    public void invocateData(final String geoJsonFilePath) {
        log.info("데이터 파싱 시작");

        areaDataParser.parseData(geoJsonFilePath)
                .stream()
                .parallel()
                .forEach(area -> {
                    try {
                        Address address = addressDataParser.parseData(area);
                        Point point = pointDataParser.parseData(address);

                        Region region = Region.of(area, address, point);

                        service.saveRegion(region);
                    } catch (Exception e) {
                        log.info("{} 데이터는 파싱 오류로 건너뜀", area);
                    }
                });

        log.info("데이터 파싱 종료");
    }

}
