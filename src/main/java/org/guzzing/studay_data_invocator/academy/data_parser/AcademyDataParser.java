package org.guzzing.studay_data_invocator.academy.data_parser;

import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.ACADEMY_ADDRESS;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.ACADEMY_CONTACT;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.ACADEMY_NAME;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.ACADEMY_SHUTTLE_FEE;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.COURSE_CAPACITY;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.COURSE_CURRICULUM;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.COURSE_DURATION;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.COURSE_SUBJECT;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataColumnIndex.COURSE_TOTAL_FEE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studay_data_invocator.academy.data_parser.gecode.Geocoder;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.EscapeToken;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.address.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.class_info.Course;
import org.guzzing.studay_data_invocator.academy.model.vo.location.Location;
import org.guzzing.studay_data_invocator.global.reader.DataFileReader;
import org.springframework.stereotype.Component;

@Component
public class AcademyDataParser {

    private final DataFileReader dataFileReader;
    private final Geocoder geocoder;

    public AcademyDataParser(DataFileReader dataFileReader, Geocoder geocoder) {
        this.dataFileReader = dataFileReader;
        this.geocoder = geocoder;
    }

    public Map<Academy, List<Course>> parseData(final String fileName) {
        final Map<Academy, List<Course>> dataMap = new ConcurrentHashMap<>();
        final Map<String, Location> cache = new ConcurrentHashMap<>();

        filterData(fileName).forEach(dataLine -> {
            List<String> splitData = Arrays.stream(dataLine.split(",")).toList();

            Academy academy = getAcademy(splitData, cache);
            Course course = getCourse(academy, splitData);

            List<Course> courses = dataMap.getOrDefault(academy,
                    Collections.synchronizedList(new ArrayList<Course>()));
            courses.add(course);

            dataMap.put(academy, courses);
        });

        return dataMap;
    }

    private Course getCourse(Academy academy, List<String> splitData) {
        return Course.of(academy,
                splitData.get(COURSE_CURRICULUM.ordinal()),
                splitData.get(COURSE_SUBJECT.ordinal()),
                splitData.get(COURSE_CAPACITY.ordinal()),
                splitData.get(COURSE_DURATION.ordinal()),
                splitData.get(COURSE_TOTAL_FEE.ordinal()));
    }

    private Academy getAcademy(List<String> splitData, Map<String, Location> cache) {
        AcademyInfo academyInfo = AcademyInfo.of(
                splitData.get(ACADEMY_NAME.ordinal()),
                splitData.get(ACADEMY_CONTACT.ordinal()),
                splitData.get(ACADEMY_SHUTTLE_FEE.ordinal()));
        Address address = Address.of(splitData.get(ACADEMY_ADDRESS.ordinal()));
        Location location = getLocation(cache, splitData.get(ACADEMY_ADDRESS.ordinal()));

        return Academy.of(academyInfo, address, location);
    }

    private Location getLocation(Map<String, Location> cache, String fullAddress) {
        return cache.computeIfAbsent(fullAddress, geocoder::addressToLocationV2);
    }

    private List<String> filterData(final String fileName) {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(EscapeToken::isEscapeToken)
                .toList();
    }

}
