package org.guzzing.studay_data_invocator.academy.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Institute;
import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.guzzing.studay_data_invocator.academy.model.NotValidAcademy;
import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.guzzing.studay_data_invocator.academy.repository.LessonRepository;
import org.guzzing.studay_data_invocator.academy.repository.NotValidAcademyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final AcademyDataParser dataParser;
    private final NotValidAcademyRepository notValidAcademyRepository;

    public AcademyService(
            final AcademyRepository academyRepository,
            final LessonRepository lessonRepository,
            final AcademyDataParser dataParser,
            NotValidAcademyRepository notValidAcademyRepository) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.dataParser = dataParser;
        this.notValidAcademyRepository = notValidAcademyRepository;
    }

    public void importAllData() {
        Arrays.stream(AcademyDataFile.values())
                .forEach(file -> this.importData(file.getFileName()));
    }

    public void importData(final String fileName) {
        Map<Institute, List<Lesson>> dataMap = dataParser.parseData(fileName);

        for(Institute institute :dataMap.keySet()) {
            if(institute instanceof Academy) {
                academyRepository.save((Academy) institute);
                if(dataMap.get(institute)!=null) {
                    dataMap.get(institute).stream().filter(lesson -> lesson != null).forEach(lesson -> lessonRepository.save(lesson));
                }
                continue;
            }
            notValidAcademyRepository.save((NotValidAcademy) institute);
        }
    }

    public void deleteAllData() {
        academyRepository.deleteAll();
    }

}
