package com.app.microviaje.dto;

import jakarta.validation.constraints.NotNull;

public record ReanudarViajeDTO(
        @NotNull(message = "idPausa no puede ser nulo.")
        Long idPausa
) {
}
