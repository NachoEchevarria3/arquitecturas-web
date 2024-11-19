package com.app.microviaje.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FinalizarViajeDTO(
        @NotNull(message = "idParada no puede ser nulo.")
        String idParada,

        @Positive(message = "kilometrosRecorridos debe ser positivo.")
        int kilometrosRecorridos
) {
}
