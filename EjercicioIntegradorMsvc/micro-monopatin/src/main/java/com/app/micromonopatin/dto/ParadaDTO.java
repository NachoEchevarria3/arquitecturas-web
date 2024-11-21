package com.app.micromonopatin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa una parada")
public record ParadaDTO(
        @Schema(description = "ID único de la parada")
        String id,
        @Schema(description = "Ubicación de la parada")
        String ubicacion
) {
}
