package com.mbtitalkbackend.common;

import lombok.Getter;

@Getter
public enum SnsType {
    KAKAO, NOVALUE;

    public static SnsType compare(String str) {
        try {
            return valueOf(str.toUpperCase());
        } catch (Exception ex) {
            return NOVALUE;
        }
    }
}