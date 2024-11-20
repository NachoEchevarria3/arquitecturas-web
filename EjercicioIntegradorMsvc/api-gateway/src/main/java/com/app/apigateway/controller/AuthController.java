package com.app.apigateway.controller;

import com.app.apigateway.dto.*;
import com.app.apigateway.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registrarse")
    @Operation(
            summary = "Registrarse",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario para crear la cuenta",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegistroUsuarioDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Cuenta creada con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "La cuenta de mercado pago no existe"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    )
            }
    )
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarse(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Cuenta creada con éxito.",
                authService.registrarse(registroUsuarioDto)
        ));
    }

    @PostMapping("/iniciar-sesion")
    @Operation(
            summary = "Iniciar sesión",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario para iniciar sesión",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InicioSesionDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Se inicio sesión con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Credenciales inválidas"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    )
            }
    )
    public ResponseEntity<ApiResponse<JwtDTO>> iniciarSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Has iniciado sesión con éxito.",
                authService.iniciarSesion(inicioSesionDto)
        ));
    }
}
