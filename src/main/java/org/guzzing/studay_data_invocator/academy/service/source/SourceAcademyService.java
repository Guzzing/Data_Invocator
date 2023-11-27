package org.guzzing.studay_data_invocator.academy.service.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studay_data_invocator.academy.infra.api.ApiEndPointProvider;
import org.guzzing.studay_data_invocator.academy.infra.api.ApiFetcher;
import org.guzzing.studay_data_invocator.academy.infra.api.dto.SeoulAcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.repository.source.GyeonggiSourceAcademyJpaRepository;
import org.guzzing.studay_data_invocator.academy.infra.xlsx.RowParser;
import org.guzzing.studay_data_invocator.academy.repository.source.SeoulSourceAcademyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import reactor.core.scheduler.Schedulers;
import reactor.core.publisher.Mono;

@Service
public class SourceAcademyService {

    private static final int START_PAGE = 1;
    private static final int PLUS_PAGE = 5;
    private final ObjectMapper objectMapper;
    private final ApiEndPointProvider apiEndPointProvider;
    private final ApiFetcher apiFetcher;
    private final GyeonggiSourceAcademyJpaRepository gyeonggiSourceAcademyJpaRepository;
    private final SeoulSourceAcademyRepository seoulSourceAcademyRepository;

    public SourceAcademyService(
            ObjectMapper objectMapper,
            ApiEndPointProvider apiEndPointProvider, ApiFetcher apiFetcher, GyeonggiSourceAcademyJpaRepository gyeonggiSourceAcademyJpaRepository, SeoulSourceAcademyRepository seoulSourceAcademyRepository) {
        this.objectMapper = objectMapper;
        this.apiEndPointProvider = apiEndPointProvider;
        this.apiFetcher = apiFetcher;
        this.gyeonggiSourceAcademyJpaRepository = gyeonggiSourceAcademyJpaRepository;
        this.seoulSourceAcademyRepository = seoulSourceAcademyRepository;
    }

    @Transactional
    public void saveGyeonggiSourceAcademies(String fileLocation) throws Exception {

        List<List<GyeonggiSourceAcademy>> parser = RowParser.parser(fileLocation);

        parser.forEach(
                sourceAcademies -> gyeonggiSourceAcademyJpaRepository.saveAll(sourceAcademies)
        );
    }

    public void saveSeoulSourceAcademyAsync() {
        int startPage = START_PAGE;
        AtomicBoolean continueFetching = new AtomicBoolean(true);

        Flux.fromStream(Stream.iterate(startPage, page -> page + PLUS_PAGE + 1))
                .takeWhile(page -> continueFetching.get())
                .flatMap(page -> {
                    String buildSeoulUrl = apiEndPointProvider.buildSeoulUrl(page, page + PLUS_PAGE);
                    return apiFetcher.getResponseBodyByAsync(buildSeoulUrl)
                            .flatMap(seoulAcademyInfo ->
                                    Mono.fromRunnable(() ->
                                                    seoulSourceAcademyRepository.save(SeoulSourceAcademy.to(seoulAcademyInfo)))
                                            .subscribeOn(Schedulers.parallel()) // Use a different scheduler
                            )
                            .doOnComplete(() -> continueFetching.set(false))
                            .retry(100);
                })
                .then()
                .subscribe();
    }

    @Transactional
    public void saveSeoulSourceAcademy() {
        int startPage = START_PAGE;
        String buildSeoulUrl = apiEndPointProvider.buildSeoulUrl(startPage, startPage + PLUS_PAGE);
        List<SeoulAcademyInfo> responseBody = apiFetcher.getResponseBody(buildSeoulUrl);

        while (!responseBody.isEmpty()) {
            responseBody.forEach(seoulAcademyInfo ->
                    seoulSourceAcademyRepository.save(SeoulSourceAcademy.to(seoulAcademyInfo))
            );
            startPage += PLUS_PAGE + 1;
            buildSeoulUrl = apiEndPointProvider.buildSeoulUrl(startPage, startPage + PLUS_PAGE);
            responseBody = apiFetcher.getResponseBody(buildSeoulUrl);
        }

    }
}
