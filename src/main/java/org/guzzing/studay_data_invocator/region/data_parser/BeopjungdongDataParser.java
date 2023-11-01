package org.guzzing.studay_data_invocator.region.data_parser;

import static org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode.NONE;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.guzzing.studay_data_invocator.global.config.BeopjungdongConfig;
import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongDto;
import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongResponses;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BeopjungdongDataParser {

    private static final int PAGE_SIZE = 1_000;
    private static final Set<String> TARGET_REGION = Set.of("서울특별시", "경기도");

    private final DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();

    private final BeopjungdongConfig beopjungdongConfig;

    public BeopjungdongDataParser(BeopjungdongConfig beopjungdongConfig) {
        this.beopjungdongConfig = beopjungdongConfig;
    }

    public BeopjungdongResponses parseData(final int pageNumber) {
        String urlString = getUrlString(pageNumber);

        defaultUriBuilderFactory.setEncodingMode(NONE);

        BeopjungdongResponses responses = WebClient.builder()
                .uriBuilderFactory(defaultUriBuilderFactory)
                .build()
                .get()
                .uri(urlString)
                .retrieve()
                .bodyToMono(BeopjungdongResponses.class)
                .block();

        return regulateResponse(responses);
    }

    private String getUrlString(final int pageNumber) {
        return UriComponentsBuilder.fromUriString(beopjungdongConfig.getBaseUrl())
                .queryParam("page", pageNumber)
                .queryParam("perPage", PAGE_SIZE)
                .queryParam("serviceKey", beopjungdongConfig.getApiKey())
                .build(true)
                .encode()
                .toUriString();
    }

    private BeopjungdongResponses regulateResponse(final BeopjungdongResponses responses) {
        List<BeopjungdongDto> data = filterData(responses);
        Set<BeopjungdongDto> dataSet = new CopyOnWriteArraySet<>(data);

        return new BeopjungdongResponses(dataSet.stream().toList(), responses.currentCount(), responses.totalCount());
    }

    private List<BeopjungdongDto> filterData(BeopjungdongResponses responses) {
        return responses.data().stream()
                .filter(beopjungdongDto -> beopjungdongDto.삭제일자() == null)
                .filter(beopjungdongDto -> beopjungdongDto.읍면동명() != null)
                .filter(beopjungdongDto -> TARGET_REGION.contains(beopjungdongDto.시도명()))
                .toList();
    }

}
