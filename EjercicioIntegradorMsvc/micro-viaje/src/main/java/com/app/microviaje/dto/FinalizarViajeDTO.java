package com.app.microviaje.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FinalizarViajeDTO(
        @NotNull(message = "idParada no puede ser nulo.")
        Long idParada,

        @NotNull(message = "idCuentaMercadoPago no puede ser nulo.")
        Long idCuentaMercadoPago,

        @Positive(message = "kilometrosRecorridos debe ser positivo.")
        int kilometrosRecorridos
) {
}
