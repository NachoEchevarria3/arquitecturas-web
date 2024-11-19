package com.app.apigateway.controller;

import com.app.apigateway.dto.ApiResponse;
import com.app.apigateway.dto.UsuarioDTO;
import com.app.apigateway.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping ResponseEntity<ApiResponse<List<UsuarioDTO>>> getUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuarios obtenidos con éxito.",
                usuarioService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> getUsuario(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Usuario obtenido con éxito.",
                usuarioService.findById(id)
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