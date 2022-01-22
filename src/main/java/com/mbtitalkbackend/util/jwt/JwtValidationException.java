package com.mbtitalkbackend.util.jwt;

public class JwtValidationException extends RuntimeException {
    private static final String MESSASGE = "JWT 인증에 실패했습니다.";

    public JwtValidationException() {
        super(MESSASGE);
    }
}
