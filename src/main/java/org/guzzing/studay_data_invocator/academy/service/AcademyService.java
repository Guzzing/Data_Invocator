package org.guzzing.studay_data_invocator.academy.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.guzzing.studay_data_invocator.academyparser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academyparser.AcademyDataFile;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.ReviewCount;
import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.guzzing.studay_data_invocator.academy.repository.LessonRepository;
import org.guzzing.studay_data_invocator.academy.repository.ReviewCountJpaRepository;
import org.guzzing.studay_data_invocator.global.GeometryTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.locationtech.jts.geom.Point;

@Service
@Transactional
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final ReviewCountJpaRepository reviewCountJpaRepository;
    private final AcademyDataParser dataParser;

    public AcademyService(
            final AcademyRepository academyRepository,
            final LessonRepository lessonRepository,
            ReviewCountJpaRepository reviewCountJpaRepository, final AcademyDataParser dataParser) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountJpaRepository = reviewCountJpaRepository;
        this.dataParser = dataParser;
    }

    public void importAllData() {
        Arrays.stream(AcademyDataFile.values())
                .forEach(file -> {
                        importData(file.getFileName());
                });
    }

    public void importData(final String fileName) {
        Map<Academy, List<Lesson>> dataMap = dataParser.parseData(fileName);

        for (Academy institute : dataMap.keySet()) {
            List<Lesson> lessons = dataMap.get(institute);
            Point point = GeometryTypeFactory.createPoint(
                    institute.getLocation().getLatitude(),
                    institute.getLocation().getLongitude()
            );
            institute.changePoint(point);

            Academy savedAcademy = academyRepository.save(institute);

            Long maxEducationFee = saveLessonsAndCalculateMaxFee(savedAcademy, lessons);
            savedAcademy.changeEducationFee(maxEducationFee);

            reviewCountJpaRepository.save(ReviewCount.makeDefaultReviewCount(savedAcademy));

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
