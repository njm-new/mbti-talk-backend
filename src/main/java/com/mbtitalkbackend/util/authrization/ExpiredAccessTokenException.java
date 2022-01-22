package com.mbtitalkbackend.util.authrization;

public class ExpiredAccessTokenException extends RuntimeException{
    public static final String MESSAGE = "토큰이 만료되었습니다.";

    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }
}
