package com.app.microviaje.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
