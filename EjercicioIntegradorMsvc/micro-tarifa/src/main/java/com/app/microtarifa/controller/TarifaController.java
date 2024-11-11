package com.app.microtarifa.controller;

import com.app.microtarifa.dto.ApiResponse;
import com.app.microtarifa.dto.CreateTarifaDTO;
import com.app.microtarifa.dto.TarifaDTO;
import com.app.microtarifa.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifa")
public class TarifaController {
    @Autowired
    private TarifaService tarifaService;

    @GetMapping("/actual")
    public ResponseEntity<ApiResponse<TarifaDTO>> getTarifaActual() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tarifa obtenida con éxito.",
                tarifaService.getTarifaActual()
        ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TarifaDTO>>> getHistorialTarifas() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Historial de tarifas obtenido con éxito.",
                tarifaService.findAll()
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TarifaDTO>> createTarifa(@RequestBody CreateTarifaDTO tarifa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Tarifa creada con éxito.",
                tarifaService.crearTarifa(tarifa)
        ));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<TarifaDTO>> actualizarTarifa(@RequestBody CreateTarifaDTO tarifa) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tarifa creada con éxito.",
                tarifaService.actualizarTarifa(tarifa)
        ));
    }
}
