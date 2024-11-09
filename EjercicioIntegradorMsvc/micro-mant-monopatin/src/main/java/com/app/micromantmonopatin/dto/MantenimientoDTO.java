package com.app.micromantmonopatin.dto;

import java.time.LocalDateTime;

public record MantenimientoDTO(
        Long idMantenimiento,
        Long idMonopatin,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
