package org.guzzing.studay_data_invocator.academy.data_parser;

import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.ACADEMY_ADDRESS;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.ACADEMY_CONTACT;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.ACADEMY_NAME;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.ACADEMY_SHUTTLE_FEE;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.COURSE_CAPACITY;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.COURSE_CURRICULUM;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.COURSE_DURATION;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.COURSE_SUBJECT;
import static org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataColumnIndex.COURSE_TOTAL_FEE;

import java.util.Arrays;
import java.util.List;
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

    public AcademyDataParser(DataFileReader dataFileReader) {
        this.dataFileReader = dataFileReader;
    }

    public List<Academy> parseData(final String fileName) {

        return filterData(fileName).stream()
                .map(dataLine -> {
                    List<String> splitData = Arrays.stream(dataLine.split(",")).toList();

                    return new Academy(
                            AcademyInfo.of(
                                    splitData.get(ACADEMY_NAME.ordinal()),
                                    splitData.get(ACADEMY_CONTACT.ordinal()),
                                    splitData.get(ACADEMY_SHUTTLE_FEE.ordinal())
                            ),
                            new Address(splitData.get(ACADEMY_ADDRESS.ordinal())),
                            new Location(splitData.get(ACADEMY_ADDRESS.ordinal())),
                            Course.of(
                                    splitData.get(COURSE_CURRICULUM.ordinal()),
                                    splitData.get(COURSE_SUBJECT.ordinal()),
                                    splitData.get(COURSE_CAPACITY.ordinal()),
                                    splitData.get(COURSE_DURATION.ordinal()),
                                    splitData.get(COURSE_TOTAL_FEE.ordinal())
                            ));
                })
                .toList();
    }

    private List<String> filterData(String fileName) {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(EscapeToken::isEscapeToken)
                .toList();
    }

}
