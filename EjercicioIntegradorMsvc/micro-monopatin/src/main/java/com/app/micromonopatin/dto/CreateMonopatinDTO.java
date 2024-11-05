package com.app.micromonopatin.dto;

import jakarta.validation.constraints.Min;

public record CreateMonopatinDTO(
        @Min(value = 1, message = "limiteKilometros debe ser mayor o igual a 1")
        int limiteKilometros,

        @Min(value = 1, message = "limiteTiempoDeUso debe ser mayor o igual a 1")
        int limiteTiempoDeUso
) {
}
