package com.app.msvcusuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistroUsuarioDto(
        @NotBlank(message = "nombre no puede estar vacio.")
        String nombre,

        @NotBlank(message = "apellido no puede estar vacio.")
        String apellido,

        @NotBlank(message = "email no puede estar vacio.")
        String email,

        @NotBlank(message = "password no puede estar vacio.")
        String password,

        @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Número de celular inválido")
        String telefono,

        @NotBlank(message = "emailMp no puede ser vacio.")
        String emailMp,

        @NotBlank(message = "passwordMp no puede ser vacio.")
        String passwordMp
) {
}
