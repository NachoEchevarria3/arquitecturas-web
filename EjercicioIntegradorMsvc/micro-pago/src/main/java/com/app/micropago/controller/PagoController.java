package com.app.micropago.controller;

import com.app.micropago.dto.ApiResponse;
import com.app.micropago.dto.PagarViajeDTO;
import com.app.micropago.service.PagoService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pago")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Pagos")
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @GetMapping("/total-facturado")
    @Operation(
            summary = "Obtiene total facturado en un año",
            parameters = {
                    @Parameter(name = "anio", description = "Año del que se quiere obtener el total facturado", required = true),
                    @Parameter(name = "mesInicio", description = "Mes desde el que se quiere obtener el total facturado", required = true),
                    @Parameter(name = "mesFin", description = "Mes hasta el que se quiere obtener el total facturado", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Total facturado obtneido con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    )
            }
    )
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
    @Hidden
    public ResponseEntity<ApiResponse<?>> pagar(@Valid @RequestBody PagarViajeDTO infoPago) {
        pagoService.pagar(infoPago);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Pago realizado con éxito.",
                null
        ));
    }
}
