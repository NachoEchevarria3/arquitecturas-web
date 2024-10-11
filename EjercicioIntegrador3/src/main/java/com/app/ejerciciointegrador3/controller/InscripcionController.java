package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.dto.InscripcionRequestDto;
import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.service.CarreraService;
import com.app.ejerciciointegrador3.service.EstudianteService;
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

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<?> matricularEstudiante(@Valid @RequestBody InscripcionRequestDto inscripcion) {
        this.inscripcionService.matricularEstudiante(inscripcion);
        return ResponseEntity.status(HttpStatus.OK).body("Estudiante matriculado con éxito");
    }
}
