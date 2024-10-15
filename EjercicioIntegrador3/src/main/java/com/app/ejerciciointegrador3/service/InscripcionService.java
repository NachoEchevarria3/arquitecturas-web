package com.app.ejerciciointegrador3.service;

import com.app.ejerciciointegrador3.dto.InscripcionRequestDto;
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

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private CarreraService carreraService;

    public void save(EstudianteCarrera estudianteCarrera) {
        if (estudianteCarrera == null) throw new IllegalArgumentException("EstudianteCarrera no puede ser nulo");
        inscripcionRepository.save(estudianteCarrera);
    }

    // Ejercicio 2b
    public void matricularEstudiante(InscripcionRequestDto inscripcion) {
        if (inscripcion.getAnioInscripcion() <= 0) throw new IllegalArgumentException("Anio no puede ser menor que 0");

        Estudiante estudiante = estudianteService.findById(inscripcion.getEstudianteId());
        Carrera carrera = carreraService.findById(inscripcion.getCarreraId());

        if (inscripcionRepository.findByCarreraAndEstudiante(carrera, estudiante).isPresent()) {
            throw new IllegalArgumentException("El estudiante ya esta matriculado en esta carrera");
        }

        inscripcionRepository.save(new EstudianteCarrera(estudiante, carrera, inscripcion.getAnioInscripcion()));
    }
}
