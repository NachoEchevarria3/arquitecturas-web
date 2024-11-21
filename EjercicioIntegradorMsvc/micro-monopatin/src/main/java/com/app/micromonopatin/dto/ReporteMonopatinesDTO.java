package com.app.micromonopatin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO que representa un reporte de estadísticas de monopatines")
public record ReporteMonopatinesDTO(
        @Schema(description = "Tipo de reporte, por ejemplo, 'kilómetros recorridos', 'tiempo de uso', etc.", required = true)
        String tipoReporte,
        @Schema(description = "Lista de estadísticas asociadas a los monopatines en el reporte", required = true)
        List<EstadisticaMonopatinDTO> monopatines
) {
}
