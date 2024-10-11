package com.app.ejerciciointegrador3.controller;

import com.app.ejerciciointegrador3.dto.CarreraDto;
import com.app.ejerciciointegrador3.dto.ReporteCarreraDto;
import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.service.CarreraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrera")
public class CarreraController {
    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Carrera> carreras = this.carreraService.findAll();
        if (carreras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Carreras");
        }

        return ResponseEntity.status(HttpStatus.OK).body(carreras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(carreraService.findById(id));
    }

    @GetMapping("/orderbycantinscriptos")
    public ResponseEntity<?> getOrderedByCantidadInscriptos() {
        List<CarreraDto> carreras = this.carreraService.findAllOrderedByCantidadInscriptos();
        if (carreras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Carreras");
        }

        return ResponseEntity.status(HttpStatus.OK).body(carreras);
    }

    @GetMapping("/{carreraId}/ciudad/{ciudad}/estudiantes")
    public ResponseEntity<?> getAllEstudiantesByCarreraAndCiudad(@PathVariable int carreraId, @PathVariable String ciudad) {
        List<Estudiante> estudiantes = this.carreraService.findAllEstudiantesByCarreraAndCiudad(carreraId, ciudad);

        if (estudiantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron estudiantes");
        }

        return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
    }

    @GetMapping("/reporte")
    public ResponseEntity<?> getReporteCarreras() {
        List<ReporteCarreraDto> reporte = this.carreraService.generarReporteCarreras();

        if (reporte.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron Carreras");
        }

        return ResponseEntity.status(HttpStatus.OK).body(reporte);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Carrera carrera) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.carreraService.create(carrera));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody Carrera carrera) {
        return ResponseEntity.status(HttpStatus.OK).body(this.carreraService.update(id, carrera));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        this.carreraService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
