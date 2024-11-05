package com.app.micromonopatin.dto;

import com.app.micromonopatin.constant.EstadoMonopatin;
import jakarta.validation.constraints.NotNull;

public record EstadoDTO(
        @NotNull(message = "estado no puede ser nulo.")
        EstadoMonopatin estado
) {
}
