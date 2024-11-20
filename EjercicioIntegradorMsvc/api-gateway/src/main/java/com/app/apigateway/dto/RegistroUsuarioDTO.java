package com.app.apigateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Representa la información que debe enviar un usuario para crear una cuenta")
public record RegistroUsuarioDTO(
        @Schema(description = "Username del usuario")
        @NotBlank(message = "username no puede estar vacio.")
        String username,

        @Schema(description = "Email del usuario")
        @NotBlank(message = "email no puede estar vacio.")
        String email,

        @Schema(description = "Contraseña del usuario")
        @NotBlank(message = "password no puede estar vacio.")
        String password,

        @Schema(description = "Nombre del usuario")
        @NotBlank(message = "nombre no puede estar vacio.")
        String nombre,

        @Schema(description = "Apellido del usuario")
        @NotBlank(message = "apellido no puede estar vacio.")
        String apellido,

        @Schema(description = "Telefono del usuario")
        @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Número de celular inválido")
        String telefono,

        @Schema(description = "Email de cuenta de mercado pago del usuario")
        @NotBlank(message = "emailMp no puede ser vacio.")
        String emailMp,

        @Schema(description = "Contraseña de cuenta de mercado pago del usuario")
        @NotBlank(message = "passwordMp no puede ser vacio.")
        String passwordMp
) {
}