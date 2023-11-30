package org.guzzing.studay_data_invocator.region.parser;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AreaDataParser {

    public List<Area> parseData(String geoJsonFilePath) {
        List<Area> areas = Collections.synchronizedList(new ArrayList<>());

        try (SimpleFeatureIterator iterator = getSimpleFeatureIterator(geoJsonFilePath)) {
            while (iterator.hasNext()) {
                areas.add(getArea(iterator));
            }
        } catch (Exception e) {
            log.warn(e.toString());
        }

        return areas;
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
