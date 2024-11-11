package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.dto.RegistroUsuarioDto;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> getUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuario obtenido con éxito.",
                usuarioService.findById(id)
        ));
    }

    @PostMapping("/registrarse")
    public ResponseEntity<ApiResponse<UsuarioDTO>> registrarse(@RequestBody RegistroUsuarioDto usuario) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Cuenta creada con éxito.",
                usuarioService.registrar(usuario)
        ));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<ApiResponse<?>> anularCuenta(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuario anulado con éxito.",
                usuarioService.anularCuenta(id)
        ));
    }
}