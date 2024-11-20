package com.app.micromonopatin.dto;

import com.app.micromonopatin.constant.EstadoMonopatin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa un monopatín")
public record MonopatinDTO(
        @Schema(description = "ID único del monopatín")
        Long id,
        @Schema(description = "Estado actual del monopatín")
        EstadoMonopatin estado,
        @Schema(description = "Kilómetros recorridos por el monopatín desde su último mantenimiento")
        int kilometros,
        @Schema(description = "Historial de kilómetros recorridos por el monopatín")
        int historialkilometros,
        @Schema(description = "Límite de kilómetros que el monopatín puede recorrer antes de requerir mantenimiento")
        int limiteKilometros,
        @Schema(description = "Tiempo de uso actual del monopatín en minutos")
        int tiempoDeUso,
        @Schema(description = "Historial de tiempo de uso del monopatín en minutos")
        int historialTiempoDeUso,
        @Schema(description = "Límite de tiempo de uso en minutos antes de requerir mantenimiento")
        int limiteTiempoDeUso,
        @Schema(description = "ID de la parada en la que se encuentra el monopatín")
        String paradaId
) {
}
