package com.app.microparada.controller;

import com.app.microparada.dto.ApiResponse;
import com.app.microparada.dto.CreateParadaDTO;
import com.app.microparada.dto.ParadaDTO;
import com.app.microparada.service.ParadaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parada")
public class ParadaController {
    @Autowired
    private ParadaService paradaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadas() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAll()
        ));
    }

    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadasByUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAllByUbicacion(ubicacion)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParadaDTO>> getParadaById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Parada obtenida con éxito.",
                paradaService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ParadaDTO>> createParada(@Valid @RequestBody CreateParadaDTO parada) {
        paradaService.create(parada);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Parada creada con éxito.",
                null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParada(@PathVariable Long id) {
        paradaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
