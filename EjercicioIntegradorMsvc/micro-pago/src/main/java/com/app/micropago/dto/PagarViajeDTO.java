package com.app.micropago.dto;

public record PagarViajeDTO(
        Long idViaje,
        Long idUsuario,
        Long idCuentaMercadoPago,
        Double total
) {
}
