package com.app.micromonopatin.controller;

import com.app.micromonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.dto.CreateMonopatinDTO;
import com.app.micromonopatin.dto.ApiResponse;
import com.app.micromonopatin.dto.MonopatinDTO;
import com.app.micromonopatin.dto.ParadaDTO;
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
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatines() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findAll()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MonopatinDTO>> getMonopatinById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatin obtenido con éxito",
                monopatinService.findById(id)
        ));
    }

    @GetMapping("/parada/{idParada}")
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesByParadaId(@PathVariable Long idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findMonopatinesByParadaId(idParada)
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

    @PutMapping("/{idMonopatin}/estado/{estado}")
    public ResponseEntity<ApiResponse<MonopatinDTO>> actualizarEstado(@PathVariable Long idMonopatin, @PathVariable EstadoMonopatin estado) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Estado actualizado con éxito.",
                monopatinService.actualizarEstado(idMonopatin, estado)
        ));
    }

    @PutMapping("/{idMonopatin}/ubicar-en-parada/{idParada}")
    public ResponseEntity<ApiResponse<ParadaDTO>> ubicarMonopatinEnParada(@PathVariable Long idMonopatin, @PathVariable Long idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "El monopatin se ubicó en la parada con éxito.",
                monopatinService.ubicarMonopatinEnParada(idMonopatin, idParada)
        ));
    }

    @PutMapping("/{idMonopatin}/kilometros/{cantKilometros}")
    public ResponseEntity<ApiResponse<?>> actualizarKilometros(@PathVariable Long idMonopatin, @PathVariable int cantKilometros) {
        monopatinService.actualizarKilometros(idMonopatin, cantKilometros);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Kilometros actualizados con éxito.",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/tiempo-de-uso/{cantTiempoDeUso}")
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDeUso(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDeUso) {
        monopatinService.actualizarTiempoDeUso(idMonopatin, cantTiempoDeUso);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de uso actualizado con éxito.",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/reset-estadisticas")
    public ResponseEntity<ApiResponse<?>> resetEstadisticas(@PathVariable Long idMonopatin) {
        monopatinService.resetKilometrosYTiempoDeUso(idMonopatin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Los kilometros del momopatin se resetearon con éxito.",
                null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) {
        monopatinService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
