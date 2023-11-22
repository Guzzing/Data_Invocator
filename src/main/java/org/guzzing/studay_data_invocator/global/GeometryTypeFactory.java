package org.guzzing.studay_data_invocator.global;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public final class GeometryTypeFactory {

    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    private GeometryTypeFactory() {
        throw new RuntimeException("유틸 클래스여서 생성자를 통해서 만들지 않습니다.");
    }

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

}
