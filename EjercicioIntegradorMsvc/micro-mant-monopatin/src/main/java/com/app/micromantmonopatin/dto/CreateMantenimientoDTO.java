package com.app.micromantmonopatin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateMantenimientoDTO(
        @Min(value = 1, message = "idMonopatin debe ser mayor o igual a 1.")
        @NotNull(message = "idMonopatin no puede ser nulo.")
        Long idMonopatin
) {
}
