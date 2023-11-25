package org.guzzing.studay_data_invocator.region;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.parser.AddressDataParser;
import org.guzzing.studay_data_invocator.region.parser.PointDataParser;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegionDataInvocatorRunner {

    public static final Set<String> TARGET_REGION = Set.of("서울특별시", "경기도");

    private final AddressDataParser addressDataParser;
    private final PointDataParser pointDataParser;
    private final RegionService service;

    public RegionDataInvocatorRunner(
            AddressDataParser addressDataParser,
            PointDataParser pointDataParser,
            RegionService service
    ) {
        this.addressDataParser = addressDataParser;
        this.pointDataParser = pointDataParser;
        this.service = service;
    }

    public void invocateData(final String geoJsonFilePath) {
        log.info("{} 데이터 파싱 시작", geoJsonFilePath);

        SimpleFeatureIterator iterator = getSimpleFeatureIterator(geoJsonFilePath);

        while (iterator.hasNext()) {
            try {
                Area area = getArea(iterator);
                Address address = addressDataParser.parseData(area);
                Point point = pointDataParser.parseData(address);

                Region region = Region.of(area, address, point);

                service.saveRegion(region);
            } catch (Exception e) {
                log.warn(String.valueOf(e));
            }
        }

        iterator.close();

        log.info("{} 데이터 파싱 종료", geoJsonFilePath);
    }

    private Area getArea(SimpleFeatureIterator iterator) {
        SimpleFeature feature = iterator.next();

        String emdCd = feature.getAttribute("EMD_CD").toString();
        String emdNm = feature.getAttribute("EMD_NM").toString();

        Object geometry = feature.getDefaultGeometry();

        return Area.of(emdCd, emdNm, geometry);
    }

    private SimpleFeatureIterator getSimpleFeatureIterator(final String geojsonFilePath) {
        try {
            File geoJsonFile = new File(geojsonFilePath);

            SimpleFeatureCollection collection = (SimpleFeatureCollection) new FeatureJSON()
                    .readFeatureCollection(geoJsonFile);

            return collection.features();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
