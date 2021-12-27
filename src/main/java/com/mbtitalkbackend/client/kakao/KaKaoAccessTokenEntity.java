package com.mbtitalkbackend.client.kakao;

import lombok.Getter;

@Getter
public class KaKaoAccessTokenEntity {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String refresh_token_expires_in;
}
