package com.mbtitalkbackend.util.authrization;

import com.mbtitalkbackend.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizeExceptionAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse> AuthorizationExceptionHandler() {
        return new ResponseEntity<>(ApiResponse.fail(AuthorizationException.MESSASGE), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(ExpiredAccessTokenException.class)
    public ResponseEntity<ApiResponse> ExpiredAccessTokenExceptionHandler() {
        return new ResponseEntity<>(ApiResponse.fail(ExpiredAccessTokenException.MESSAGE), HttpStatus.GONE);
    }
}
