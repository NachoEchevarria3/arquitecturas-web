package com.app.microtarifa.dto;

import java.time.LocalDate;

public record TarifaDTO(
        Long id,
        double tarifaInicial,
        double tarifaPorMinuto,
        double tarifaPausaEstensa,
        LocalDate validaDesde
) {
}
