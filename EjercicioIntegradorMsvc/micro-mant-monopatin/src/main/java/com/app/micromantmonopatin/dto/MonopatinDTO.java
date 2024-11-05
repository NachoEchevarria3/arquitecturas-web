package com.app.micromantmonopatin.dto;


import com.app.micromantmonopatin.constant.EstadoMonopatin;

public record MonopatinDTO(
        Long id,
        EstadoMonopatin estado,
        int kilometros,
        int limiteKilometros,
        int tiempoDeUso,
        int limiteTiempoDeUso
) {
}
