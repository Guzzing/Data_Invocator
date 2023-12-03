package org.guzzing.studay_data_invocator.region;

import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.global.config.GeoJsonConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
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

    private static final int BASE_SIZE = 100;

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
        log.info("{} 데이터 파싱 시작", geoJsonFilePath);

        List<Area> areas = areaDataParser.parseData(geoJsonFilePath);

        for (int startIndex = 0; startIndex < areas.size(); startIndex += BASE_SIZE) {
            int endIndex = Math.min(startIndex + BASE_SIZE, areas.size());

            List<Region> regions = areas.subList(startIndex, endIndex)
                    .stream()
                    .parallel()
                    .map(area -> {
                        try {
                            Address address = addressDataParser.parseData(area);
                            Point point = pointDataParser.parseData(address);

                            return Region.of(area, address, point);
                        } catch (Exception e) {
                            log.info("{} 데이터는 파싱 오류로 건너뜀", area);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            service.saveRegions(regions);
        }

        log.info("{} 데이터 파싱 종료", geoJsonFilePath);
    }

}
