package com.app.ejerciciointegrador3.service;

import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.model.EstudianteCarrera;
import com.app.ejerciciointegrador3.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public void save(EstudianteCarrera estudianteCarrera) {
        if (estudianteCarrera == null) throw new IllegalArgumentException("EstudianteCarrera no puede ser nulo");
        this.inscripcionRepository.save(estudianteCarrera);
    }

    // Ejercicio 2b
    public void matricularEstudiante(Estudiante estudiante, Carrera carrera, int anio) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
        if (anio <= 0) throw new IllegalArgumentException("Anio no puede ser menor que 0");
        this.inscripcionRepository.save(new EstudianteCarrera(estudiante, carrera, anio));
    }
}
