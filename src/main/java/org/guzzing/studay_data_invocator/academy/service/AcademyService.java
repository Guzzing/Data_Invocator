package org.guzzing.studay_data_invocator.academy.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.guzzing.studay_data_invocator.academy.model.AcademyCategory;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;
import org.guzzing.studay_data_invocator.academy.repository.AcademyCategoryRepository;
import org.guzzing.studay_data_invocator.academy.repository.source.GyeonggiSourceAcademyRepository;
import org.guzzing.studay_data_invocator.academy.service.category.*;
import org.guzzing.studay_data_invocator.academy.service.parser.AcademyAndLessonInfo;
import org.guzzing.studay_data_invocator.academy.service.parser.GyeonggiAcademyParser;
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
    private final AcademyCategoryRepository academyCategoryRepository;
    private final GyeonggiSourceAcademyRepository gyeonggiSourceAcademyRepository;
    private final AcademyDataParser dataParser;
    private final GyeonggiAcademyParser gyeonggiAcademyParser;
    private final CategorySearcher categorySearcher;

    public AcademyService(
            final AcademyRepository academyRepository,
            final LessonRepository lessonRepository,
            final ReviewCountJpaRepository reviewCountJpaRepository,
            final AcademyCategoryRepository academyCategoryRepository,
            final GyeonggiSourceAcademyRepository gyeonggiSourceAcademyRepository,
            final AcademyDataParser dataParser,
            final GyeonggiAcademyParser gyeonggiAcademyParser,
            final CategorySearcher categorySearcher) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountJpaRepository = reviewCountJpaRepository;
        this.academyCategoryRepository = academyCategoryRepository;
        this.gyeonggiSourceAcademyRepository = gyeonggiSourceAcademyRepository;
        this.dataParser = dataParser;
        this.gyeonggiAcademyParser = gyeonggiAcademyParser;
        this.categorySearcher = categorySearcher;
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

    public void saveEfficientGyeonggiAcademies() {
        Map<Academy, List<Lesson>> dataMap = new ConcurrentHashMap<>();
        Map<Long, Set<Long>> categoriesPair = new ConcurrentHashMap<>();

        findAcademiesAndCategory(dataMap, categoriesPair);
        saveAcademiesAndLesson(dataMap, categoriesPair);
    }

    private void findAcademiesAndCategory(
            Map<Academy, List<Lesson>> dataMap,
            Map<Long, Set<Long>> categoriesPair) {
        gyeonggiSourceAcademyRepository.findAll()
                .stream()
                .parallel()
                .forEach(
                        gyeonggiSourceAcademy -> {
                            Optional<AcademyAndLessonInfo> academyAndLessonInfo = gyeonggiAcademyParser.parse(gyeonggiSourceAcademy);
                            if (academyAndLessonInfo.isPresent()) {
                                Academy existedAcademy = academyAndLessonInfo.get().academy();
                                List<Lesson> lessons = dataMap.computeIfAbsent(existedAcademy, k -> new ArrayList<>());
                                lessons.add(academyAndLessonInfo.get().lesson());

                                dataMap.put(existedAcademy, lessons);

                                ApplicableCategoryInfo applicableCategoryInfo = categorySearcher.getCategoryIds(gyeonggiSourceAcademy);
                                Set<Long> categoryIds = categoriesPair.computeIfAbsent(applicableCategoryInfo.sourceAcademyIdentifier(), c -> new HashSet<>());
                                applicableCategoryInfo.categoryIds().forEach(
                                        categoryId -> categoryIds.add(categoryId));

                                categoriesPair.put(applicableCategoryInfo.sourceAcademyIdentifier(), categoryIds);
                            }
                        }
                );
    }

    private void saveAcademiesAndLesson(
            Map<Academy, List<Lesson>> dataMap,
            Map<Long, Set<Long>> categoriesPair) {
        dataMap.keySet()
                .stream()
                .parallel()
                .forEach(academy -> {
                            List<Lesson> lessons = dataMap.get(academy);
                            Academy savedAcademy = academyRepository.save(academy);

                            Long maxEducationFee = saveLessonsAndCalculateMaxFee(savedAcademy, lessons);
                            savedAcademy.changeEducationFee(maxEducationFee);

                            reviewCountJpaRepository.save(ReviewCount.makeDefaultReviewCount(savedAcademy));

                            for (Long id : categoriesPair.get(savedAcademy.getSourceAcademyIdentifier())) {
                                AcademyCategory academyCategory = AcademyCategory.of(savedAcademy, id);
                                academyCategoryRepository.save(academyCategory);
                            }
                        }
                );
    }

    public void makeEtcCategory() {
        List<Academy> academies = academyRepository.findAllNotCategory();
        academies
                .stream()
                .parallel()
                .forEach(
                        academy -> academyCategoryRepository.save(
                                AcademyCategory.of(
                                        academy,
                                        CategoryName.ETC.getId()))
                );
    }

}
