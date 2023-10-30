package org.guzzing.studay_data_invocator.academy.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Institute;
import org.guzzing.studay_data_invocator.academy.model.InvalidAcademy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.guzzing.studay_data_invocator.academy.repository.InvalidAddressRepository;
import org.guzzing.studay_data_invocator.academy.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final InvalidAddressRepository invalidAddressRepository;
    private final AcademyDataParser dataParser;

    public AcademyService(
            final AcademyRepository academyRepository,
            final LessonRepository lessonRepository,
            InvalidAddressRepository invalidAddressRepository, final AcademyDataParser dataParser) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.invalidAddressRepository = invalidAddressRepository;
        this.dataParser = dataParser;
    }

    public void importAllData() {
        Arrays.stream(AcademyDataFile.values())
                .forEach(file -> importData(file.getFileName()));
    }

    public void importData(final String fileName) {
        Map<Institute, List<Lesson>> dataMap = dataParser.parseData(fileName);

        for (Institute institute : dataMap.keySet()) {
            if(institute instanceof Academy) {
                List<Lesson> lessons = dataMap.get(institute);
                Academy savedAcademy = academyRepository.save((Academy) institute);

                Long maxEducationFee = saveLessonsAndCalculateMaxFee(savedAcademy, lessons);
                savedAcademy.changeEducationFee(maxEducationFee);
                continue;
            }

            invalidAddressRepository.save((InvalidAcademy) institute);
        }

    }

    private Long saveLessonsAndCalculateMaxFee(Academy academy, List<Lesson> lessons) {
        Long maxEducationFee = Long.MIN_VALUE;

        for (Lesson lesson : lessons) {
            lesson.addAcademy(academy);
            lessonRepository.save(lesson);

            maxEducationFee = lesson.biggerThanTotalFee(maxEducationFee);
        }

        return maxEducationFee;
    }

}
