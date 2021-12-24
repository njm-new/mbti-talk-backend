package com.mbtitalkbackend.common;

public enum ResponseCode {

    SUCCESS("success"),
    FAIL("fail");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }
}
