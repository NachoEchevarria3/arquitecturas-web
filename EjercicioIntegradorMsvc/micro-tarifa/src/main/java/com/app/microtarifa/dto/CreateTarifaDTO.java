package com.app.microtarifa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTarifaDTO(
        @Min(value = 1, message = "tarifaInicial debe ser mayor o igual a 1.")
        double tarifaInicial,

        @Min(value = 1, message = "tarifaPorMinuto debe ser mayor o igual a 1.")
        double tarifaPorMinuto,

        @Min(value = 1, message = "tarifaPausaEstensa debe ser mayor o igual a 1.")
        double tarifaPausaEstensa,

        @NotNull(message = "validaDesde no puede ser nulo.")
        LocalDate validaDesde
) {
}
