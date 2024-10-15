package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.dto.InscripcionRequestDto;
import com.app.ejerciciointegrador3.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscripcion")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<?> matricularEstudiante(@Valid @RequestBody InscripcionRequestDto inscripcion) {
        inscripcionService.matricularEstudiante(inscripcion);
        return ResponseEntity.status(HttpStatus.OK).body("Estudiante matriculado con éxito");
    }
}
