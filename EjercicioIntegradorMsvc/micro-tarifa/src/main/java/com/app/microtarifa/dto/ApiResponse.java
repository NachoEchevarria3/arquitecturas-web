package com.app.microtarifa.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
