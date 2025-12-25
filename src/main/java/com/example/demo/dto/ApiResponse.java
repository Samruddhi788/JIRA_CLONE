package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private String error;
    private T data;

    private ApiResponse(boolean success, String message, String error, T data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    // SUCCESS RESPONSE
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, null, data);
    }

    // FAILURE RESPONSE
    public static <T> ApiResponse<T> failure(String error, String message) {
        return new ApiResponse<>(false, message, error, null);
    }

    // getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getError() { return error; }
    public T getData() { return data; }
}
