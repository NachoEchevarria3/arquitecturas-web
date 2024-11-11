package com.app.microviaje.dto;

import jakarta.validation.constraints.NotNull;

public record ComenzarViajeDTO(
        @NotNull(message = "idMonopatin no puede ser nulo.")
        Long idMonopatin,

        @NotNull(message = "idUsuario no puede ser nulo.")
        Long idUsuario,

        @NotNull(message = "idCuentaMercadoPago no puede ser nulo.")
        Long idCuentaMercadoPago
) {
}
