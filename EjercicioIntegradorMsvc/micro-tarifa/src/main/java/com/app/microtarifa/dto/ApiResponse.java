package com.app.microtarifa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta genérica de la API")
public record ApiResponse<T>(
        @Schema(description = "Código de respuesta HTTP", example = "200")
        int status,

        @Schema(description = "Mensaje informativo de la respuesta", example = "Success")
        String message,

        @Schema(description = "Información de la respuesta")
        T data
) {
}
