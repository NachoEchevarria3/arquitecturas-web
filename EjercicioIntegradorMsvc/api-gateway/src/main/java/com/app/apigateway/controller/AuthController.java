package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.dto.InicioSesionDTO;
import com.app.apigateway.dto.RegistroUsuarioDto;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.service.AuthService;
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
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registrarse")
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarse(@Valid @RequestBody RegistroUsuarioDto registroUsuarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Cuenta creada con éxito.",
                authService.registrarse(registroUsuarioDto)
        ));
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<ApiResponse<String>> iniciarSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDto) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Has iniciado sesión con éxito.",
                authService.iniciarSesion(inicioSesionDto)
        ));
    }
}
