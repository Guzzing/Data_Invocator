package org.guzzing.studay_data_invocator.sourceacademy.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.ApiEndPointProvider;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.ApiFetcher;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto.SeoulAcademyInfo;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto.SeoulAcademyInfoResponse;
import org.guzzing.studay_data_invocator.sourceacademy.model.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.sourceacademy.repository.GyeonggiSourceAcademyJpaRepository;
import org.guzzing.studay_data_invocator.sourceacademy.infra.xlsx.RowParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;


@Service
public class SourceAcademyService {

    private final ObjectMapper objectMapper;
    private final ApiEndPointProvider apiEndPointProvider;
    private final ApiFetcher apiFetcher;
    private final GyeonggiSourceAcademyJpaRepository sourceAcademyRepository;

    public SourceAcademyService(
            ObjectMapper objectMapper,
            ApiEndPointProvider apiEndPointProvider, ApiFetcher apiFetcher, GyeonggiSourceAcademyJpaRepository sourceAcademyRepository) {
        this.objectMapper = objectMapper;
        this.apiEndPointProvider = apiEndPointProvider;
        this.apiFetcher = apiFetcher;
        this.sourceAcademyRepository = sourceAcademyRepository;
    }

    @Transactional
    public void saveSourceAcademiesPerfect(String fileLocation) throws Exception {

        List<List<GyeonggiSourceAcademy>> parser = RowParser.parser(fileLocation);

        parser.forEach(
                sourceAcademies -> sourceAcademyRepository.saveAll(sourceAcademies)
        );
    }

    public void saveSeoulSourceAcademy() {
        String buildSeoulUrl = apiEndPointProvider.buildSeoulUrl(26046, 26070);

        Flux<SeoulAcademyInfo> flux = apiFetcher.getResponseBody(buildSeoulUrl);


    }

    private SeoulAcademyInfoResponse parseResponse(String jsonResponse) {
        try {
            return objectMapper.readValue(jsonResponse, SeoulAcademyInfoResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

}
