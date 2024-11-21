package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.dto.MercadoPagoDTO;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.service.UsuarioService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Obtiene todos los usuarios",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Usuarios obtenidos con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron usuarios"
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
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> getUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuarios obtenidos con éxito.",
                usuarioService.findAll()
        ));
    }

    @GetMapping("/{id}")
    @Hidden
    public ResponseEntity<ApiResponse<UsuarioDTO>> getUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuario obtenido con éxito.",
                usuarioService.findById(id)
        ));
    }

    @PostMapping("/{idUsuario}/asociar-mp/{idCuentaMp}")
    @Operation(
            summary = "Asocia una cuenta de mercado pago a una cuenta de usuario",
            parameters = {
                    @Parameter(
                            name = "idUsuario",
                            description = "ID de la cuenta de usuario",
                            required = true
                    ),
                    @Parameter(
                            name = "idCuentaMp",
                            description = "ID de la cuenta de mercado pago",
                            required = true
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Cuenta asociada con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cuenta no encontrada"
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
    public ResponseEntity<ApiResponse<?>> asociarCuentaMercadoPago(@PathVariable Long idUsuario, @PathVariable Long idCuentaMp, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        usuarioService.asociarCuentaMercadoPago(idUsuario, idCuentaMp, authorizationHeader.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Cuenta asociada con éxito",
                null
        ));
    }

    @GetMapping("/{id}/cuentas-mp")
    @Operation(
            summary = "Obtiene las cuentas de mercado pago asociadas a una cuenta de usuario",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de la cuenta de usuario",
                            required = true
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Cuenta obtenidas con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cuenta no encontrada"
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
    public ResponseEntity<ApiResponse<List<MercadoPagoDTO>>> getCuentasMercadoPagoByUsuario(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Cuentas de mercado pago asociadas obtenidas con éxito",
                usuarioService.getCuentasMercadoPago(id, authorizationHeader.split(" ")[1])
        ));
    }

    @PutMapping("/{id}/anular")
    @Operation(
            summary = "Anula una cuenta de usuario",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID de la cuenta a anular",
                            required = true
                    )
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Cuenta anulada con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Cuenta no encontrada"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    ),
            }
    )
    public ResponseEntity<ApiResponse<?>> anularCuenta(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuario anulado con éxito.",
                usuarioService.anularCuenta(id)
        ));
    }
}