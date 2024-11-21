package com.app.apigateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa la información no confidencial de una cuenta de mercado pago")
public record MercadoPagoDTO(
        @Schema(description = "ID de la cuenta")
        Long id,

        @Schema(description = "Nombre de usuario de la cuenta")
        String username
) {
}
