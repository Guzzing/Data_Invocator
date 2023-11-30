package org.guzzing.studay_data_invocator.academy.infra.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ApiEndPointProvider {

    @Value("${api.academy.seoul.prefix_url}")
    private String seoulPrefixApiUrl;

    @Value("${api.academy.seoul.postfix_url}")
    private String seoulPostfixApiUrl;

    @Value("${api.academy.seoul.key}")
    private String seoulKey;

    public String buildSeoulUrl(@PathVariable int startPage, @PathVariable int endPage) {
        return UriComponentsBuilder.fromHttpUrl(
                        seoulPrefixApiUrl + seoulKey + seoulPostfixApiUrl + "/{startPage}/{endPage}/"
                )
                .buildAndExpand(startPage, endPage)
                .toUriString();
    }

}
