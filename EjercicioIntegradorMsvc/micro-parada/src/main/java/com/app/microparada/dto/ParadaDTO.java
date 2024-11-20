package com.app.microparada.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para representar la información de una parada")
public record ParadaDTO(
        @Schema(description = "ID único de la parada")
        String id,
        @Schema(description = "Ubicación de la parada")
        String ubicacion
) {
}
