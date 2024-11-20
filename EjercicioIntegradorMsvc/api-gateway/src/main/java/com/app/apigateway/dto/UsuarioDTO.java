package com.app.apigateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Representa la información de un usuario")
public record UsuarioDTO(
        @Schema(description = "ID de la cuenta del usuario")
        Long id,

        @Schema(description = "Email del usuario")
        String email,

        @Schema(description = "Username del usuario")
        String username,

        @Schema(description = "Nombre del usuario")
        String nombre,

        @Schema(description = "Apellido del usuario")
        String apellido,

        @Schema(description = "Telefono del usuario")
        String telefono,

        @Schema(description = "Fecha en la que se creo la cuenta")
        LocalDate fechaAlta,

        @Schema(description = "Indica si la cuenta esta activa")
        Boolean activo,

        @Schema(description = "Ccuentas de mercado pago que tiene asociadas el usuario a su cuenta")
        List<MercadoPagoDTO> cuentasMpAsociadas
) {
}
