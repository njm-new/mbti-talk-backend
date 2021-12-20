package com.mbtitalkbackend.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO { //apirespose, 바디(데이터) 추가
    private HttpStatus code;
    private String message;
    private Object body;

    public ResultVO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
        this.body = null;
    }
}
