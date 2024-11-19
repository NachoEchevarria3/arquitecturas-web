package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.service.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-pago")
public class MercadoPagoController {
    @Autowired
    private MercadoPagoService mercadoPagoService;

    @GetMapping("/{id}/saldo")
    public ResponseEntity<ApiResponse<Double>> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Saldo obtenido con éxito",
                mercadoPagoService.consultarSaldo(id)
        ));
    }

    @PutMapping("/{id}/cargar-saldo/{monto}")
    public ResponseEntity<ApiResponse<Double>> cargarSaldo(@PathVariable Long id, @PathVariable Double monto) {
        mercadoPagoService.cargarSaldo(id, monto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Se cargaron " + monto + " pesos a la cuenta con id " + id + ".",
                null
        ));
    }

    @PutMapping("/{id}/descontar-saldo/{monto}")
    public ResponseEntity<ApiResponse<Double>> descontarSaldo(
            @PathVariable Long id,
            @PathVariable Double monto
    ) {
        mercadoPagoService.descontarSaldo(id, monto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Se descontaron " + monto + " pesos de la cuenta con id " + id + ".",
                null
        ));
    }
}
