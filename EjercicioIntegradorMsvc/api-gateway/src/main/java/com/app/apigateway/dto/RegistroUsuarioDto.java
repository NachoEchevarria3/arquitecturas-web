package com.app.apigateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistroUsuarioDto(
        @NotBlank(message = "username no puede estar vacio.")
        String username,

        @NotBlank(message = "email no puede estar vacio.")
        String email,

        @NotBlank(message = "password no puede estar vacio.")
        String password,

        @NotBlank(message = "nombre no puede estar vacio.")
        String nombre,

        @NotBlank(message = "apellido no puede estar vacio.")
        String apellido,

        @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Número de celular inválido")
        String telefono,

        @NotBlank(message = "emailMp no puede ser vacio.")
        String emailMp,

        @NotBlank(message = "passwordMp no puede ser vacio.")
        String passwordMp
) {
}