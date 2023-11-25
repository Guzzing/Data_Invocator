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
import org.guzzing.studay_data_invocator.global.config.GeoJsonConfig;
import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.parser.AddressDataParser;
import org.guzzing.studay_data_invocator.region.parser.PointDataParser;
import org.guzzing.studay_data_invocator.region.repository.RegionJpaRepository;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegionDataInvocatorRunner {

    public static final Set<String> TARGET_REGION = Set.of("서울특별시", "경기도");

    private final GeoJsonConfig config;
    private final AddressDataParser addressDataParser;
    private final PointDataParser pointDataParser;
    private final RegionService service;

    public RegionDataInvocatorRunner(
            GeoJsonConfig config,
            AddressDataParser addressDataParser,
            PointDataParser pointDataParser,
            RegionService service
    ) {
        this.config = config;
        this.addressDataParser = addressDataParser;
        this.pointDataParser = pointDataParser;
        this.service = service;
    }

    public void invocateData() {
        SimpleFeatureIterator iterator = getSimpleFeatureIterator();

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
    }

    private Area getArea(SimpleFeatureIterator iterator) {
        SimpleFeature feature = iterator.next();

        String emdCd = feature.getAttribute("EMD_CD").toString();
        String emdNm = feature.getAttribute("EMD_NM").toString();

        Object geometry = feature.getDefaultGeometry();

        return Area.of(emdCd, emdNm, geometry);
    }

    private SimpleFeatureIterator getSimpleFeatureIterator() {
        try {
            File geoJsonFile = new File(config.getPath());

            SimpleFeatureCollection collection = (SimpleFeatureCollection) new FeatureJSON()
                    .readFeatureCollection(geoJsonFile);

            return collection.features();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
