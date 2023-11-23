package org.guzzing.studay_data_invocator.region.model;

import org.locationtech.jts.geom.MultiPolygon;

public record Area(
        long code,
        String name,
        MultiPolygon geometry
) {

    public static Area of(String code, String name, Object geometry) {
        return new Area(
                Long.parseLong(code),
                name,
                (MultiPolygon) geometry);
    }

}
