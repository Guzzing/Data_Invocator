package org.guzzing.studay_data_invocator.academy.service;

import java.util.Arrays;
import java.util.List;
import org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyService {

    private final AcademyRepository academyRepository;
    private final AcademyDataParser dataParser;

    public AcademyService(final AcademyRepository academyRepository, final AcademyDataParser dataParser) {
        this.academyRepository = academyRepository;
        this.dataParser = dataParser;
    }

    @Transactional
    public void importAllData() {
        Arrays.stream(AcademyDataFile.values())
                .forEach(file -> this.importData(file.getFileName()));
    }

    @Transactional
    public long importData(final String fileName) {
        List<Academy> academies = dataParser.parseData(fileName);

        return academyRepository.saveAll(academies)
                .size();
    }

    @Transactional
    public void deleteAllData() {
        academyRepository.deleteAll();
    }

}