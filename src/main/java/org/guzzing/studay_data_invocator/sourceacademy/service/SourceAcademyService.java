package org.guzzing.studay_data_invocator.sourceacademy.service;

import org.guzzing.studay_data_invocator.sourceacademy.model.SourceAcademy;
import org.guzzing.studay_data_invocator.sourceacademy.repository.SourceAcademyJpaRepository;
import org.guzzing.studay_data_invocator.sourceacademy.util.RowParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SourceAcademyService {

    private final SourceAcademyJpaRepository sourceAcademyRepository;

    public SourceAcademyService(SourceAcademyJpaRepository sourceAcademyRepository) {
        this.sourceAcademyRepository = sourceAcademyRepository;
    }

    @Transactional
    public void saveSourceAcademiesPerfect(String fileLocation) throws Exception {

        List<List<SourceAcademy>> parser = RowParser.parser(fileLocation);

        parser.forEach(
                sourceAcademies -> sourceAcademyRepository.saveAll(sourceAcademies)
        );
    }

}
