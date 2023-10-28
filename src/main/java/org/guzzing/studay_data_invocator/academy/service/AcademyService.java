package org.guzzing.studay_data_invocator.academy.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Course;
import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.guzzing.studay_data_invocator.academy.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final CourseRepository courseRepository;
    private final AcademyDataParser dataParser;

    public AcademyService(
            final AcademyRepository academyRepository,
            final CourseRepository courseRepository,
            final AcademyDataParser dataParser
    ) {
        this.academyRepository = academyRepository;
        this.courseRepository = courseRepository;
        this.dataParser = dataParser;
    }

    public void importAllData() {
        Arrays.stream(AcademyDataFile.values())
                .forEach(file -> this.importData(file.getFileName()));
    }

    public void importData(final String fileName) {
        Map<Academy, List<Course>> dataMap = dataParser.parseData(fileName);

        academyRepository.saveAll(dataMap.keySet());
        dataMap.values().forEach(courseRepository::saveAll);
    }

    public void deleteAllData() {
        academyRepository.deleteAll();
    }

}
