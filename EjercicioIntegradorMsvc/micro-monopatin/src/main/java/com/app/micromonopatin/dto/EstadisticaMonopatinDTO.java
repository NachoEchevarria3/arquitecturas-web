package com.app.micromonopatin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa las estadísticas de un monopatín según el reporte solicitado")
public record EstadisticaMonopatinDTO(
        @Schema(description = "ID del monopatín", required = true)
        Long idMonopatin,
        @Schema(description = "Cantidad de la métrica relacionada al tipo de reporte (por ejemplo kilómetros recorridos o tiempo de uso)")
        int cantidad
) {
}
