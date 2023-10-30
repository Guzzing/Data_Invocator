package org.guzzing.studay_data_invocator.academy.data_parser.gecode;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay_data_invocator.academy.model.NotValidAddress;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.academy.repository.NotValidAddressRepository;
import org.guzzing.studay_data_invocator.global.config.GeocodeConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class Geocoder {

    private final GeocodeConfig geocodeConfig;
    private final NotValidAddressRepository notValidAddressRepository;

    public Geocoder(GeocodeConfig geocodeConfig, NotValidAddressRepository notValidAddressRepository) {
        this.geocodeConfig = geocodeConfig;
        this.notValidAddressRepository = notValidAddressRepository;
    }

    public Location addressToLocationV2(final String address, final String academyName) {
        WebClient webClient = WebClient.builder()
                .baseUrl(geocodeConfig.getApiUrl())
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultHeader(geocodeConfig.getClientIdProperty(), geocodeConfig.getClientId())
                .defaultHeader(geocodeConfig.getClientSecretProperty(), geocodeConfig.getClientSecret())
                .build();

        String response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", address)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractLocationFromResponse(response, academyName, address);
    }

    private Location extractLocationFromResponse(final String jsonResponse, String academyName, String address) {
        Gson gson = new Gson();

        Map<String, Object> mapData = gson.fromJson(
                jsonResponse, new TypeToken<Map<String, Object>>() {
                }.getType());
        List<Object> addresses = (List<Object>) mapData.get("addresses");

        if(addresses!=null && addresses.size()>0) {
            Map<String, Object> addressInfo = (Map<String, Object>) addresses.get(0);
            double x = Double.parseDouble(addressInfo.get("x").toString());
            double y = Double.parseDouble(addressInfo.get("y").toString());

            return Location.of(y, x);
        }
        notValidAddressRepository.save(NotValidAddress.of(address, academyName));
        return Location.of(0,0);
    }

}
