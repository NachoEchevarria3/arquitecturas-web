package com.app.microviaje.dto;

import java.time.LocalDate;

public record TarifaDTO(
        Long id,
        double tarifaInicial,
        double tarifaPorMinuto,
        double tarifaPausaEstensa,
        LocalDate validaDesde
) {
}
