package com.jie.service;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.model.EstudianteCarrera;
import com.jie.repository.Repository;

public class EstudianteCarreraServicio {
    private Repository<EstudianteCarrera> estudianteCarreraRepository;

    public EstudianteCarreraServicio(RepositoryFactory repositoryFactory) {
        this.estudianteCarreraRepository = repositoryFactory.getEstudianteCarreraRepository();
    }

    public void save(EstudianteCarrera estudianteCarrera) {
        if (estudianteCarrera == null) throw new IllegalArgumentException("EstudianteCarrera no puede ser nulo");
        this.estudianteCarreraRepository.save(estudianteCarrera);
    }

    public void matricularEstudiante(Estudiante estudiante, Carrera carrera, int anio) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
        this.estudianteCarreraRepository.save(new EstudianteCarrera(estudiante, carrera, anio));
    }
}
