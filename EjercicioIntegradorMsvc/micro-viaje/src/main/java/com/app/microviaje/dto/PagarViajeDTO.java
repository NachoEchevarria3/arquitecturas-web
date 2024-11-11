package com.app.microviaje.dto;

public record PagarViajeDTO(
        Long idViaje,
        Long idUsuario,
        Long idCuentaMercadoPago,
        Double total
) {
}
