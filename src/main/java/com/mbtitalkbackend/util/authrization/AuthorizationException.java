package com.mbtitalkbackend.util.authrization;

public class AuthorizationException extends RuntimeException {
    public static final String MESSASGE = "회원인증에 실패했습니다.";

    public AuthorizationException() {
        super(MESSASGE);
    }
}
