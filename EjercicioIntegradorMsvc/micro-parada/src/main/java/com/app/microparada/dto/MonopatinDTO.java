package com.app.microparada.dto;

import com.app.microparada.constant.EstadoMonopatin;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para representar la información de un monopatín")
public record MonopatinDTO(
        @Schema(description = "DTO utilizado para representar la información de un monopatín")
        Long id,
        @Schema(description = "Estado actual del monopatín",
                enumAsRef = true,
                example = "DISPONIBLE")
        EstadoMonopatin estado,
        @Schema(description = "Kilómetros recorridos por el monopatín")
        int kilometros,
        @Schema(description = "Historial de kilómetros recorridos por el monopatín")
        int historialkilometros,
        @Schema(description = "Límite de kilómetros que el monopatín puede recorrer antes de requerir mantenimiento")
        int limiteKilometros,
        @Schema(description = "Tiempo total de uso del monopatín en minutos")
        int tiempoDeUso,
        @Schema(description = "Historial de tiempo de uso del monopatín en minutos")
        int historialTiempoDeUso,
        @Schema(description = "Límite de tiempo de uso antes de requerir mantenimiento en minutos")
        int limiteTiempoDeUso,
        @Schema(description = "ID de la parada actual donde se encuentra el monopatín")
        Long paradaId
) {
}
