package com.mbtitalkbackend.member.model.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenVO {
    private String accessToken;

    public static RefreshTokenVO from(String accessToken) {
        return new RefreshTokenVO(accessToken);
    }
}
