package com.mbtitalkbackend.member.exception;

public class KakaoAuthenticationException extends RuntimeException{
    private static final String MESSASGE = "카카오 인증에 실패했습니다.";
    public KakaoAuthenticationException() {
        super(MESSASGE);
    }
}
