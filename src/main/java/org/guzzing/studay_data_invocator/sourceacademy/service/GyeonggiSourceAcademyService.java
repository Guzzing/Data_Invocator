package org.guzzing.studay_data_invocator.sourceacademy.service;


import org.guzzing.studay_data_invocator.sourceacademy.model.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.sourceacademy.repository.GyeonggiSourceAcademyJpaRepository;
import org.guzzing.studay_data_invocator.sourceacademy.util.xlsx.RowParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class GyeonggiSourceAcademyService {

    private final GyeonggiSourceAcademyJpaRepository sourceAcademyRepository;

    public GyeonggiSourceAcademyService(GyeonggiSourceAcademyJpaRepository sourceAcademyRepository) {
        this.sourceAcademyRepository = sourceAcademyRepository;
    }

    @Transactional
    public void saveSourceAcademiesPerfect(String fileLocation) throws Exception {

        List<List<GyeonggiSourceAcademy>> parser = RowParser.parser(fileLocation);

        parser.forEach(
                sourceAcademies -> sourceAcademyRepository.saveAll(sourceAcademies)
        );
    }

}
