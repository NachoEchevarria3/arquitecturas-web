package com.app.micropago.controller;

import com.app.micropago.dto.ApiResponse;
import com.app.micropago.dto.PagarViajeDTO;
import com.app.micropago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping("/total-facturado")
    public ResponseEntity<ApiResponse<Double>> getTotalFacturado(
            @RequestParam Integer anio,
            @RequestParam Integer mesInicio,
            @RequestParam Integer mesFin
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Total facturado obtenido con éxito",
                pagoService.getTotalFacturado(anio, mesInicio, mesFin)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> pagar(@Valid @RequestBody PagarViajeDTO infoPago) {
        pagoService.pagar(infoPago);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Pago realizado con éxito.",
                null
        ));
    }
}
