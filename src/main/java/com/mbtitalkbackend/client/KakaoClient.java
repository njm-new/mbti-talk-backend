package com.mbtitalkbackend.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Service
public class KakaoClient {
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(KakaoClient.class);

    @Autowired
    public KakaoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getAccessToken(String code) throws JsonProcessingException {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("grant_type", "authorization_code");
        payload.add("client_id", "51ebe5eee7b316d51aaf6b3aa6a87496");
        payload.add("redirect_uri", "http://localhost:3000/callback");
        payload.add("code", code);

        Mono<String> responseMonoString;
        responseMonoString = webClient.mutate()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(payload)
                .exchangeToMono(clientResponse -> {
                    if (!clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return null;
                    }
                    return clientResponse.bodyToMono(String.class);
                });

        String reseponseString = responseMonoString.flux()
                .toStream()
                .findFirst()
                .orElse(null);

        //Convert String to Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseJson;

        responseJson = objectMapper.readValue(reseponseString, new TypeReference<>() {
        });

        return (String) responseJson.get("access_token");
    }

    public int getMemberId(String accessToken) throws JsonProcessingException {
        String responseString = webClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build()
                .post()
                .uri("/v2/user/me")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .flux()
                .toStream()
                .findFirst()
                .orElse(null);

        //Convert String to Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseJson = objectMapper.readValue(responseString, new TypeReference<>() {
        });

        return (int) responseJson.get("id");
    }
}
