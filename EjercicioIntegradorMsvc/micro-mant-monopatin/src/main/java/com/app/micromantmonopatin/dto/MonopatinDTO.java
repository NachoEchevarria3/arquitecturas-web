package com.app.micromantmonopatin.dto;


import com.app.micromantmonopatin.constant.EstadoMonopatin;

public record MonopatinDTO(
        Long id,
        EstadoMonopatin estado,
        int kilometros,
        int historialkilometros,
        int limiteKilometros,
        int tiempoDeUso,
        int historialTiempoDeUso,
        int limiteTiempoDeUso,
        String paradaId
) {
}
