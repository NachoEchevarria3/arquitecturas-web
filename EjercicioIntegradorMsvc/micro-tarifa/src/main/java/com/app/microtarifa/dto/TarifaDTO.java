package com.app.microtarifa.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Representa la información de la tarifa")
public record TarifaDTO(
        @Schema(description = "ID de la tarifa")
        Long id,

        @Schema(description = "Monto con el cual comienza el viaje")
        double tarifaInicial,

        @Schema(description = "Monto que se cobra por minuto")
        double tarifaPorMinuto,

        @Schema(description = "Monto que se cobra por minuto en caso de exceder el tiempo de pausa del viaje")
        double tarifaPausaEstensa,

        @Schema(description = "Representa la fecha desde cuando va a ser válida la tarifa")
        LocalDate validaDesde
) {
}
