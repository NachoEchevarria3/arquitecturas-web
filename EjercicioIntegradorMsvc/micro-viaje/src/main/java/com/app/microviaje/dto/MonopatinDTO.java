package com.app.microviaje.dto;

import com.app.microviaje.constant.EstadoMonopatin;

public record MonopatinDTO(
        Long id,
        EstadoMonopatin estado,
        int kilometros,
        int historialkilometros,
        int limiteKilometros,
        int tiempoDeUso,
        int historialTiempoDeUso,
        int limiteTiempoDeUso,
        Long paradaId
) {
}
