package com.app.micropago.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
