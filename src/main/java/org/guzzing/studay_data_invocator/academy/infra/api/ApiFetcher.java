package org.guzzing.studay_data_invocator.sourceacademy.infra.api;

import org.guzzing.studay_data_invocator.global.config.WebClientConfig;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto.SeoulAcademyInfo;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto.SeoulAcademyInfoResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ApiFetcher {

    private final WebClientConfig webClient;

    public ApiFetcher(WebClientConfig webClient) {
        this.webClient = webClient;
    }

    public List<SeoulAcademyInfo> getResponseBody(String apiUrl) {
        SeoulAcademyInfoResponse response =
                webClient.webClient().method(HttpMethod.GET)
                        .uri(apiUrl)
                        .retrieve()
                        .bodyToMono(SeoulAcademyInfoResponse.class)
                        .timeout(Duration.ofMillis(50000))
                        .block();

        if (response == null || response.getNeisAcademyInfo() == null || response.getNeisAcademyInfo().getRow() == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(response.getNeisAcademyInfo().getRow());
    }

    public Flux<SeoulAcademyInfo> getResponseBodyByAsync(String apiUrl) {
        return webClient.webClient().method(HttpMethod.GET)
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(SeoulAcademyInfoResponse.class)
                .timeout(Duration.ofMillis(50000))
                .flatMap(response -> {
                    if (response == null || response.getNeisAcademyInfo() == null || response.getNeisAcademyInfo().getRow() == null) {
                        return Flux.empty();
                    } else {
                        return Flux.fromIterable(response.getNeisAcademyInfo().getRow());
                    }
                });
    }

}
