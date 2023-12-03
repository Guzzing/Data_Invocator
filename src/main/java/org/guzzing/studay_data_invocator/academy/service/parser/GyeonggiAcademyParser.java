package org.guzzing.studay_data_invocator.academy.service.parser;

import org.guzzing.studay_data_invocator.academy.infra.geocode.Geocoder;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.global.GeometryTypeFactory;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GyeonggiAcademyParser {

    private static final Map<String, Location> cache = new ConcurrentHashMap<>();
    private final Geocoder geocoder;

    public GyeonggiAcademyParser(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public Optional<AcademyAndLessonInfo> parse(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        Optional<Academy> academy = getAcademy(gyeonggiSourceAcademy);

        if (academy.isPresent()) {
            Academy existedAcademy = academy.get();
            Lesson lesson = getLesson(existedAcademy, gyeonggiSourceAcademy);

            return Optional.of(AcademyAndLessonInfo.of(existedAcademy,lesson));
        }
        return Optional.empty();
    }

    private Optional<Academy> getAcademy(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        try {
            Location location = getLocation(gyeonggiSourceAcademy.getAddress());
            Point point = GeometryTypeFactory.createPoint(
                    location.getLatitude(),
                    location.getLongitude()
            );
            return Optional.of(Academy.to(gyeonggiSourceAcademy, location, point));
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return Optional.empty();
        } catch (GeocoderException e) {
            return Optional.empty();
        }
    }

    private Lesson getLesson(Academy academy, GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        return Lesson.of(academy, gyeonggiSourceAcademy);

    }

    private Location getLocation(String address) {
        return cache.computeIfAbsent(address, geocoder::addressToLocation);
    }
}
