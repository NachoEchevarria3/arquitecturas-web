package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.service.MercadoPagoService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-pago")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Mercado pago")
public class MercadoPagoController {
    @Autowired
    private MercadoPagoService mercadoPagoService;

    @GetMapping("/{id}/saldo")
    @Operation(
            summary = "Consulta saldo de la cuenta de mercado pago",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de la cuenta de mercado pago de la que se quiere consultar el saldo",
                            required = true
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Saldo obtenido con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cuenta de mercado pago no encontrada"
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
    public ResponseEntity<ApiResponse<Double>> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Saldo obtenido con éxito",
                mercadoPagoService.consultarSaldo(id)
        ));
    }

    @PutMapping("/{id}/cargar-saldo/{monto}")
    @Operation(
            summary = "Cargar saldo a cuenta de Mercado pago",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de la cuenta de mercado pago a la que se cargará el saldo",
                            required = true
                    ),
                    @Parameter(
                            name = "monto",
                            description = "Monto a cargar en pesos",
                            required = true
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Saldo cargado con éxito"
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
    public ResponseEntity<ApiResponse<?>> cargarSaldo(@PathVariable Long id, @PathVariable Double monto) {
        mercadoPagoService.cargarSaldo(id, monto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Se cargaron " + monto + " pesos a la cuenta con id " + id + ".",
                null
        ));
    }

    @PutMapping("/{id}/descontar-saldo/{monto}")
    @Hidden
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
