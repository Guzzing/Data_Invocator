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

import java.util.Arrays;
import java.util.List;
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

    public List<Academy> parseData(final String fileName) {
        return filterData(fileName).stream()
                .map(dataLine -> {
                    List<String> splitData = Arrays.stream(dataLine.split(",")).toList();

                    AcademyInfo academyInfo = AcademyInfo.of(
                            splitData.get(ACADEMY_NAME.ordinal()),
                            splitData.get(ACADEMY_CONTACT.ordinal()),
                            splitData.get(ACADEMY_SHUTTLE_FEE.ordinal()));
                    Address address = Address.of(splitData.get(ACADEMY_ADDRESS.ordinal()));
                    Location location = geocoder.addressToLocationV2(splitData.get(ACADEMY_ADDRESS.ordinal()));
                    Course course = Course.of(
                            splitData.get(COURSE_CURRICULUM.ordinal()),
                            splitData.get(COURSE_SUBJECT.ordinal()),
                            splitData.get(COURSE_CAPACITY.ordinal()),
                            splitData.get(COURSE_DURATION.ordinal()),
                            splitData.get(COURSE_TOTAL_FEE.ordinal()));

                    return new Academy(academyInfo, address, location, course);
                })
                .toList();
    }

    private List<String> filterData(final String fileName) {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(EscapeToken::isEscapeToken)
                .toList();
    }

}
