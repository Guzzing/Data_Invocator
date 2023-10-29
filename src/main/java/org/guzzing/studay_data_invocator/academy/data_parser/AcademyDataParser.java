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
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.academy.repository.NotValidAddressRepository;
import org.guzzing.studay_data_invocator.global.reader.DataFileReader;
import org.springframework.stereotype.Component;

@Component
public class AcademyDataParser {

    private static final int CELL_SIZE = 22;
    private final DataFileReader dataFileReader;
    private final Geocoder geocoder;
    private final NotValidAddressRepository notValidAddressRepository;

    public AcademyDataParser(DataFileReader dataFileReader, Geocoder geocoder, NotValidAddressRepository notValidAddressRepository) {
        this.dataFileReader = dataFileReader;
        this.geocoder = geocoder;
        this.notValidAddressRepository = notValidAddressRepository;
    }

    public Map<Academy, List<Lesson>> parseData(final String fileName) {
        final Map<Academy, List<Lesson>> dataMap = new ConcurrentHashMap<>();
        final Map<String, Location> cache = new ConcurrentHashMap<>();

        filterData(fileName).forEach(dataLine -> {
            List<String> splitData = Arrays.stream(dataLine.split(",")).toList();

            if (splitData.size() >= CELL_SIZE) {

                Academy academy = getAcademy(splitData, cache);
                Lesson lesson = getCourse(academy, splitData);
                List<Lesson> cours = dataMap.getOrDefault(academy,
                        Collections.synchronizedList(new ArrayList<Lesson>()));
                cours.add(lesson);

                dataMap.put(academy, cours);
            }
        });

        return dataMap;
    }

    private Lesson getCourse(Academy academy, List<String> splitData) {
        return Lesson.of(academy,
                splitData.get(COURSE_CURRICULUM.getIndex()),
                splitData.get(COURSE_SUBJECT.getIndex()),
                splitData.get(COURSE_CAPACITY.getIndex()),
                splitData.get(COURSE_DURATION.getIndex()),
                splitData.get(COURSE_TOTAL_FEE.getIndex()));
    }

    private Academy getAcademy(List<String> splitData, Map<String, Location> cache) {
        AcademyInfo academyInfo = AcademyInfo.of(
                splitData.get(ACADEMY_NAME.getIndex()),
                splitData.get(ACADEMY_CONTACT.getIndex()),
                splitData.get(ACADEMY_SHUTTLE_FEE.getIndex()));
        Address address = Address.of(splitData.get(ACADEMY_ADDRESS.getIndex()));
        Location location = getLocation(cache, splitData.get(ACADEMY_ADDRESS.getIndex()), splitData.get(ACADEMY_NAME.getIndex()));

        return Academy.of(academyInfo, address, location);
    }

    private Location getLocation(Map<String, Location> cache, String fullAddress, String academyName) {
        return geocoder.addressToLocationV2(fullAddress, academyName);
    }

    private List<String> filterData(final String fileName) {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(EscapeToken::isEscapeToken)
                .toList();
    }

}
