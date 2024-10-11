package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Estudiante> estudiantes = this.estudianteService.findAll();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Carreras");
        }

        return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(estudianteService.findById(id));
    }

    @GetMapping("/orderbyedad")
    public ResponseEntity<?> getOrderedByEdad() {
        List<Estudiante> estudiantes = this.estudianteService.findAllOrederedByEdad();
        if (estudiantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron estudiantes");
        }

        return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
    }

    @GetMapping("/libreta/{numLibreta}")
    public ResponseEntity<?> getByNumeroLibreta(@PathVariable int numLibreta) {
        return ResponseEntity.status(HttpStatus.OK).body(this.estudianteService.findByNumeroLibreta(numLibreta));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> getAllByGenero(@PathVariable String genero) {
        List<Estudiante> estudiantes = this.estudianteService.findAllByGenero(genero);
        if (estudiantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Estudiantes con el genero " + genero);
        }

        return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid  @RequestBody Estudiante estudiante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.estudianteService.create(estudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody Estudiante estudiante) {
        return ResponseEntity.status(HttpStatus.OK).body(this.estudianteService.update(id, estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        this.estudianteService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
