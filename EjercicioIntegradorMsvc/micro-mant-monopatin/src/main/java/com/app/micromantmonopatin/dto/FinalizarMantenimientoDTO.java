package com.app.micromantmonopatin.dto;

import jakarta.validation.constraints.NotNull;

public record FinalizarMantenimientoDTO(
        @NotNull(message = "idMantenimiento no puede ser nulo.")
        Long idMantenimiento,

        @NotNull(message = "idParada no puede ser nulo.")
        String idParada
) {
}
