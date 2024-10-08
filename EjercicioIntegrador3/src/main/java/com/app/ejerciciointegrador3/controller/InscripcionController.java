package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.service.CarreraService;
import com.app.ejerciciointegrador3.service.EstudianteService;
import com.app.ejerciciointegrador3.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inscripcion")
public class InscripcionController {
    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private CarreraService carreraService;

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping("/{idCarrera}/matricular/{idEstudiante}/anio/{anio}")
    public ResponseEntity<?> matricularEstudiante(@PathVariable int idCarrera, @PathVariable int idEstudiante, @PathVariable int anio) {
        Carrera carrera = this.carreraService.findById(idCarrera);
        if (carrera == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La carrera no existe");
        }

        Estudiante estudiante = this.estudianteService.findById(idEstudiante);
        if (estudiante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El estudiante no existe");
        }

        this.inscripcionService.matricularEstudiante(estudiante, carrera, anio);
        return ResponseEntity.status(HttpStatus.OK).body("Estudiante matriculado con éxito");
    }
}
