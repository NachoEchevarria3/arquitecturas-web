package com.app.apigateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa la información que debe enviar un usuario para iniciar sesión")
public record InicioSesionDTO(
        @Schema(description = "Email del usuario")
        String email,

        @Schema(description = "Contraseña del usuario")
        String password
) {
}
