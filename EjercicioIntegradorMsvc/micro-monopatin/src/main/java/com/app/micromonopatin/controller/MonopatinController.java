package com.app.micromonopatin.controller;

import com.app.micromonopatin.dto.CreateMonopatinDTO;
import com.app.micromonopatin.dto.EstadoDTO;
import com.app.micromonopatin.dto.ApiResponse;
import com.app.micromonopatin.entity.Monopatin;
import com.app.micromonopatin.service.MonopatinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monopatin")
public class MonopatinController {
    @Autowired
    private MonopatinService monopatinService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Monopatin>>> getMonopatines() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Monopatin>> getMonopatinById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatin obtenido con éxito",
                monopatinService.findById(id)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMonopatin(@RequestBody @Valid CreateMonopatinDTO monopatin) {
        monopatinService.create(monopatin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Monopatin creado con éxito",
                null
        ));
    }

    @PostMapping("/{idMonopatin}/estado")
    public ResponseEntity<ApiResponse<Monopatin>> actualizarEstado(@PathVariable Long idMonopatin, @Valid @RequestBody EstadoDTO estadoDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Estado actualizado con éxito.",
                monopatinService.actualizarEstado(idMonopatin, estadoDTO)
        ));
    }

    @PostMapping("/{idMonopatin}/kilometros/{cantKilometros}")
    public ResponseEntity<ApiResponse<?>> actualizarKilometros(@PathVariable Long idMonopatin, @PathVariable int cantKilometros) {
        monopatinService.actualizarKilometros(idMonopatin, cantKilometros);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Kilometros actualizados con éxito.",
                null
        ));
    }

    @PostMapping("/{idMonopatin}/tiempo-de-uso/{cantTiempoDeUso}")
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDeUso(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDeUso) {
        monopatinService.actualizarTiempoDeUso(idMonopatin, cantTiempoDeUso);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de uso actualizado con éxito.",
                null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) {
        monopatinService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
