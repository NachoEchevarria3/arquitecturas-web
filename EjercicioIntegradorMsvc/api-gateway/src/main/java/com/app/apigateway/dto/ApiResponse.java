package com.app.apigateway.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
