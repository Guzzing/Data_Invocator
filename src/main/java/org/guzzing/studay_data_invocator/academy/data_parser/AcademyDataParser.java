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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.academy.data_parser.gecode.Geocoder;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.EscapeToken;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Institute;
import org.guzzing.studay_data_invocator.academy.model.InvalidAcademy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.global.reader.DataFileReader;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AcademyDataParser {

    private static final int REQUIRED_CELL_SIZE = 22;
    private final DataFileReader dataFileReader;
    private final Geocoder geocoder;

    public AcademyDataParser(DataFileReader dataFileReader, Geocoder geocoder) {
        this.dataFileReader = dataFileReader;
        this.geocoder = geocoder;
    }

    public Map<Institute, List<Lesson>> parseData(final String fileName) {
        final Map<Institute, List<Lesson>> dataMap = new ConcurrentHashMap<>();
        final Map<String, Location> cache = new ConcurrentHashMap<>();

        List<String> filteredData = filterData(fileName);
        int dataSize = filteredData.size();

        for (int i = 0; i < dataSize; i++) {
            String dataLine = filteredData.get(i);
            List<String> splitData = Arrays.asList(dataLine.split(","));

            if (splitData.size() >= REQUIRED_CELL_SIZE) {
                Optional<Institute> institute = getAcademy(splitData, cache);

                if (institute.isPresent()) {
                    Institute existedInstitute =  institute.get();
                    if (institute.isPresent() && institute.get() instanceof Academy) {
                        Lesson lesson = getLesson((Academy) existedInstitute, splitData);
                        List<Lesson> lessons = dataMap.getOrDefault(existedInstitute, new ArrayList<>());
                        lessons.add(lesson);


                        dataMap.put(existedInstitute, lessons);
                        continue;
                    }
                    dataMap.put(existedInstitute,new ArrayList<>());
                }

            }
        }

        return dataMap;
    }

    private Lesson getLesson(Academy academy, List<String> splitData) {
        return Lesson.of(academy,
                splitData.get(COURSE_CURRICULUM.getIndex()),
                splitData.get(COURSE_SUBJECT.getIndex()),
                splitData.get(COURSE_CAPACITY.getIndex()),
                splitData.get(COURSE_DURATION.getIndex()),
                splitData.get(COURSE_TOTAL_FEE.getIndex()));
    }

    private Optional<Institute> getAcademy(List<String> splitData, Map<String, Location> cache) {
        try {
            String academyName = splitData.get(ACADEMY_NAME.getIndex());
            String contact = splitData.get(ACADEMY_CONTACT.getIndex());
            String shuttleFee = splitData.get(ACADEMY_SHUTTLE_FEE.getIndex());
            String academyAddress = splitData.get(ACADEMY_ADDRESS.getIndex());

            AcademyInfo academyInfo = AcademyInfo.of(academyName, contact, shuttleFee);
            Address address = Address.of(academyAddress);
            Optional<Location> location = getLocation(cache, academyAddress);

            if(location.isPresent()) {
                return Optional.of(Academy.of(academyInfo, address, location.get()));
            }
            return Optional.of(InvalidAcademy.of(academyAddress, academyName));

        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Optional<Location> getLocation(Map<String, Location> cache, String fullAddress) {
        return  geocoder.addressToLocation(fullAddress);
    }

    private List<String> filterData(final String fileName) {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(EscapeToken::isEscapeToken)
                .toList();
    }

}
