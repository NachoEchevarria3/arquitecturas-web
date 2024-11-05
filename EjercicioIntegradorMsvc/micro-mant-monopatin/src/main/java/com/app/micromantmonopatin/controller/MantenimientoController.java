package com.app.micromantmonopatin.controller;

import com.app.micromantmonopatin.dto.ApiResponse;
import com.app.micromantmonopatin.entity.Mantenimiento;
import com.app.micromantmonopatin.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimiento")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> getMantenimientos() {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de mantenimientos obtenida con éxito.",
                mantenimientoService.findAll()
        ));
    }

    @GetMapping("/monopatin/{idMonopatin}")
    public ResponseEntity<ApiResponse<List<Mantenimiento>>> getMantenimientosByIdMonopatin(@PathVariable Long idMonopatin) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de mantenimientos obtenida con éxito.",
                mantenimientoService.findAllByIdMonopatin(idMonopatin)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mantenimiento>> create(@RequestBody Mantenimiento mantenimiento) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Mantenimiento creado con éxito.",
                mantenimientoService.create(mantenimiento)
        ));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponse<Mantenimiento>> finalizar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Mantenimiento finalizado con éxito.",
                mantenimientoService.finalizarMantenimiento(id)
        ));
    }
}
