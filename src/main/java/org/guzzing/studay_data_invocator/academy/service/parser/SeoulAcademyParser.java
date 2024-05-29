package org.guzzing.studay_data_invocator.academy.service.parser;

import org.guzzing.studay_data_invocator.academy.infra.geocode.Geocoder;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.global.GeometryTypeFactory;
import org.guzzing.studay_data_invocator.global.exception.GeocoderException;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SeoulAcademyParser {

    private static final Map<String, Location> cache = new ConcurrentHashMap<>();
    private final Geocoder geocoder;

    public SeoulAcademyParser(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public Optional<AcademiesAndLessonInfos> parser(SeoulSourceAcademy seoulSourceAcademy) {
        Optional<Academy> academy = getAcademy(seoulSourceAcademy);
        if (academy.isPresent()) {
            Academy validAcademy = academy.get();
            List<Lesson> lesson = getLesson(seoulSourceAcademy);

            return Optional.of(AcademiesAndLessonInfos.of(validAcademy, lesson));
        }
        return Optional.empty();
    }

    private Optional<Academy> getAcademy(SeoulSourceAcademy seoulSourceAcademy) {
        try {
            Location location = getLocation(seoulSourceAcademy.getAddress() + seoulSourceAcademy.getDetailAddress());
            Point point = GeometryTypeFactory.createPoint(
                    location.getLatitude(),
                    location.getLongitude()
            );
            return Optional.of(Academy.to(seoulSourceAcademy, location, point));
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return Optional.empty();
        } catch (GeocoderException e) {
            return Optional.empty();
        }
    }

    private List<Lesson> getLesson(SeoulSourceAcademy seoulSourceAcademy) {
        return LessonParser.parseLessons(seoulSourceAcademy.getContentsOfCourseFeePerPerson())
                .stream()
                .map(seoulLessonInfo -> Lesson.of(
                        seoulSourceAcademy,
                        seoulLessonInfo.lessonName(),
                        seoulLessonInfo.lessonPrice(),
                        seoulLessonInfo.duration()
                ))
                .toList();
    }

    private Location getLocation(String address) {
        return cache.computeIfAbsent(address, geocoder::addressToLocation);
    }
}
