package com.codingshuttle.springbootwebtutorial.response;


import com.codingshuttle.springbootwebtutorial.exceptions.ApiError;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh:mm:ss dd-MM-YYYY")
    private LocalDateTime timestamp;
    private T data;
    private ApiError  apiError;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }
    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }

}
