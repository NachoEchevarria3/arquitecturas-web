package com.app.micromantmonopatin.dto;

import com.app.micromantmonopatin.constant.EstadoMonopatin;
import jakarta.validation.constraints.NotNull;

public record EstadoDTO(
        @NotNull(message = "estado no puede ser nulo.")
        EstadoMonopatin estado
) {
}
