package com.app.microparada.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO utilizado para la creación de una parada")
public record CreateParadaDTO(
        @Schema(description = "Ubicación de la parada", required = true)
        String ubicacion
) {
}
