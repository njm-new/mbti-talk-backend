package com.mbtitalkbackend.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse { //apirespose, 바디(데이터) 추가
    private ResponseCode code;
    private String message;
    private Object body;

    public static ApiResponse success (Object body) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.code = ResponseCode.SUCCESS;
        apiResponse.message = "success";
        apiResponse.body = body;

        return apiResponse;
    }

    public static ApiResponse success () {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.code = ResponseCode.SUCCESS;
        apiResponse.message = "success";
        apiResponse.body = null;

        return apiResponse;
    }

    public static ApiResponse fail(String message) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.code = ResponseCode.FAIL;
        apiResponse.message = message;
        apiResponse.body = null;

        return apiResponse;
    }
}
