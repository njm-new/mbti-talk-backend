package com.mbtitalkbackend.client.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoClient {
    private final WebClient webClient;
    private final String GRANT_TYPE;
    private final String CLIENT_ID;
    private final String REDIRECT_URI;
    private final Logger logger = LoggerFactory.getLogger(KakaoClient.class);

    public KakaoClient(WebClient webClient,
                       @Value("${cred.kakao.grant-type}") String grantType,
                       @Value("${cred.kakao.client-id}") String clientId,
                       @Value("${cred.kakao.redirect-uri}") String redirectUri) {
        this.webClient = webClient;
        this.GRANT_TYPE = grantType;
        this.CLIENT_ID = clientId;
        this.REDIRECT_URI = redirectUri;
    }

    public String getAccessToken(String code) throws JsonProcessingException {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("grant_type", GRANT_TYPE);
        payload.add("client_id", CLIENT_ID);
        payload.add("redirect_uri", REDIRECT_URI);
        payload.add("code", code);

        KaKaoAccessTokenEntity kaKaoAccessTokenEntity;
        try {
            Mono<KaKaoAccessTokenEntity> kaKaoAccessTokenEntityMono = webClient.mutate()
                    .baseUrl("https://kauth.kakao.com")
                    .build()
                    .post()
                    .uri("/oauth/token")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(payload)
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode() != HttpStatus.OK) {
                            return null;
                        }
                        return clientResponse.bodyToMono(KaKaoAccessTokenEntity.class);
                    });

            kaKaoAccessTokenEntity = kaKaoAccessTokenEntityMono
                    .share()
                    .block();
        } catch (NullPointerException e) {
            throw new KakaoAuthenticationException();
        }
        return kaKaoAccessTokenEntity.getAccess_token();
    }

    public String getMemberId(String accessToken) throws JsonProcessingException {
        KakaoMemberEntity kakaoMemberEntity;

        try {
            Mono<KakaoMemberEntity> kakaoMemberEntityMono = webClient.mutate()
                    .baseUrl("https://kapi.kakao.com")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .build()
                    .post()
                    .uri("/v2/user/me")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(KakaoMemberEntity.class);

            kakaoMemberEntity = kakaoMemberEntityMono
                    .share()
                    .block();
        } catch (NullPointerException e) {
            throw new KakaoAuthenticationException();
        }

        return kakaoMemberEntity.getId();
    }

    /* Local 테스트용 임시 */
    public String getAccessTokenForLocal(String code) throws JsonProcessingException {
        MultiValueMap<String, String> payload = new LinkedMultiValueMap<>();
        payload.add("grant_type", GRANT_TYPE);
        payload.add("client_id", CLIENT_ID);
        payload.add("redirect_uri", "http://localhost:3000/callback");
        payload.add("code", code);

        KaKaoAccessTokenEntity kaKaoAccessTokenEntity;
        try {
            Mono<KaKaoAccessTokenEntity> kaKaoAccessTokenEntityMono = webClient.mutate()
                    .baseUrl("https://kauth.kakao.com")
                    .build()
                    .post()
                    .uri("/oauth/token")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(payload)
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode() != HttpStatus.OK) {
                            return null;
                        }
                        return clientResponse.bodyToMono(KaKaoAccessTokenEntity.class);
                    });

            kaKaoAccessTokenEntity = kaKaoAccessTokenEntityMono
                    .share()
                    .block();
        } catch (NullPointerException e) {
            throw new KakaoAuthenticationException();
        }
        return kaKaoAccessTokenEntity.getAccess_token();
    }
}
