package com.app.micromantmonopatin.dto;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
