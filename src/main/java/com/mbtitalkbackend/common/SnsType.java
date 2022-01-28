package com.mbtitalkbackend.common;

import lombok.Getter;

@Getter
public enum SnsType {
    KAKAO, NONE;

    public static SnsType compare(String str) {
        for (SnsType t : SnsType.values()) {
            if (t.name().equals(str.toUpperCase())) {
                return t;
            }
        }
        return NONE;
    }
}