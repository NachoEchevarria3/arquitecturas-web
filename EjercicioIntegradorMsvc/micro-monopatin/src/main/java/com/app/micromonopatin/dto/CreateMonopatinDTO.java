package com.app.micromonopatin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO utilizado para crear un nuevo monopatín")
public record CreateMonopatinDTO(
        @Min(value = 1, message = "limiteKilometros debe ser mayor o igual a 1")
        @Schema(description = "Límite de kilómetros que el monopatín puede recorrer antes de requerir mantenimiento")
        int limiteKilometros,

        @Min(value = 1, message = "limiteTiempoDeUso debe ser mayor o igual a 1")
        @Schema(description = "Límite de tiempo de uso (en minutos) antes de requerir mantenimiento")
        int limiteTiempoDeUso,

        @NotNull(message = "idParada no puede ser nulo.")
        @Schema(description = "ID de la parada en la que se ubicará el monopatín")
        String idParada
) {
}
