package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.service.EstudianteService;
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
        Estudiante estudiante = this.estudianteService.findByNumeroLibreta(numLibreta);
        if (estudiante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El estudiante con libreta " + numLibreta + " no existe");
        }

        return ResponseEntity.status(HttpStatus.OK).body(estudiante);
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
    public ResponseEntity<?> create(@RequestBody Estudiante estudiante) {
        this.estudianteService.save(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Estudiante estudiante) {
        Estudiante updatedEstudiante = this.estudianteService.findById(id);
        if (updatedEstudiante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La carrera no existe");
        }

        updatedEstudiante.setNombre(estudiante.getNombre());
        updatedEstudiante.setApellido(estudiante.getApellido());
        updatedEstudiante.setEdad(estudiante.getEdad());
        updatedEstudiante.setGenero(estudiante.getGenero());
        updatedEstudiante.setCiudadResidencia(estudiante.getCiudadResidencia());
        updatedEstudiante.setNumeroLibreta(estudiante.getNumeroLibreta());

        this.estudianteService.save(updatedEstudiante);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEstudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Estudiante deletedEstudiante = this.estudianteService.findById(id);
        if (deletedEstudiante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La carrera no existe");
        }

        this.estudianteService.delete(deletedEstudiante.getDni());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
