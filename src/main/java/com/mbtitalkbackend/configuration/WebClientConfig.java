package com.mbtitalkbackend.configuration;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        //Set HTTP codec
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
                .build();

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(120))
                .doOnConnected(connection -> connection
                        .addHandler(new ReadTimeoutHandler(180, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(180, TimeUnit.SECONDS)))
                .secure(ThrowingConsumer.unchecked(
                        sslContextSpec -> sslContextSpec.sslContext(
                                SslContextBuilder
                                        .forClient()
                                        .trustManager(InsecureTrustManagerFactory.INSTANCE)
                                        .build()
                        )
                ));

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
