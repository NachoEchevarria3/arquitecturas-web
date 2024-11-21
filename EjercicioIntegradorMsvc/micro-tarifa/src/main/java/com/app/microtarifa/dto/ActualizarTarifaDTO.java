package com.app.microtarifa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Representa la información que se debe enviar para actualizar la tarifa de los viajes")
public record ActualizarTarifaDTO(
        @Schema(description = "Monto con el cual comienza el viaje")
        @Min(value = 1, message = "tarifaInicial debe ser mayor o igual a 1.")
        double tarifaInicial,

        @Schema(description = "Monto que se cobra por minuto")
        @Min(value = 1, message = "tarifaPorMinuto debe ser mayor o igual a 1.")
        double tarifaPorMinuto,

        @Schema(description = "Monto que se cobra por minuto en caso de exceder el tiempo de pausa del viaje")
        @Min(value = 1, message = "tarifaPausaEstensa debe ser mayor o igual a 1.")
        double tarifaPausaEstensa,

        @Schema(description = "Representa la fecha desde cuando va a ser válida la tarifa")
        @NotNull(message = "validaDesde no puede ser nulo.")
        LocalDate validaDesde
) {
}
