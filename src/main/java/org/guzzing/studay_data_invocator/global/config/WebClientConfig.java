package org.guzzing.studay_data_invocator.global.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer
                        -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
                .build();

        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(httpMessageWriter
                        -> ((LoggingCodecSupport) httpMessageWriter).setEnableLoggingRequestDetails(true));

        HttpClient httpClient = HttpClient.create(connectionProvider())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 30)
                .responseTimeout(Duration.ofSeconds(60))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(30000000, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(3000000, TimeUnit.SECONDS)));

        return WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(
                                httpClient))
                .exchangeStrategies(exchangeStrategies)
                .filter(
                        ExchangeFilterFunction.ofRequestProcessor(
                                clientRequest -> {
                                    log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                                    clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                                    return Mono.just(clientRequest);
                                }
                        ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse -> {
                            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
                            return Mono.just(clientResponse);
                        }
                ))
                .build();
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("http-pool")
                .maxConnections(5000)
                .pendingAcquireTimeout(Duration.ofMillis(45000))
                .pendingAcquireMaxCount(100)
                .maxIdleTime(Duration.ofMillis(8000L))
                .maxLifeTime(Duration.ofMillis(58000L))
                .lifo()
                .build();
    }
}
