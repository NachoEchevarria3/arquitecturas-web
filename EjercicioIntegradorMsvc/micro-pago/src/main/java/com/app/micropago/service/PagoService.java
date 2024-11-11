package com.app.micropago.service;

import com.app.micropago.client.MercadoPagoClient;
import com.app.micropago.dto.PagarViajeDTO;
import com.app.micropago.entity.Pago;
import com.app.micropago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MercadoPagoClient mercadoPagoClient;

    public void pagar(PagarViajeDTO pagarViajeDTO) {
        if (pagarViajeDTO == null) throw new IllegalArgumentException("pagarViajeDTO no puede ser nulo.");
        mercadoPagoClient.descontarSaldo(pagarViajeDTO.idCuentaMercadoPago(), pagarViajeDTO.total());

        Pago pago = new Pago(pagarViajeDTO.idViaje(), pagarViajeDTO.idUsuario(), pagarViajeDTO.idCuentaMercadoPago(), pagarViajeDTO.total());

        pagoRepository.save(pago);
    }

    public Double getTotalFacturado(Integer anio, Integer mesInicio, Integer mesFin) {
        if (anio == null) throw new IllegalArgumentException("anio no puede ser nulo.");
        if (mesInicio == null) throw new IllegalArgumentException("mesInicio no puede ser nulo.");
        if (mesFin == null) throw new IllegalArgumentException("mesFin no puede ser nulo.");

        LocalDate fechaInicio = LocalDate.of(anio, mesInicio, 1);

        // Obtener el número de días de mesFin
        YearMonth yearMonth = YearMonth.of(anio, mesFin);
        int diasMesFin = yearMonth.lengthOfMonth();

        // Crear fechaFin con el último día del mes
        LocalDate fechaFin = LocalDate.of(anio, mesFin, diasMesFin);

        return pagoRepository.getTotalFacturado(fechaInicio, fechaFin);
    }
}
