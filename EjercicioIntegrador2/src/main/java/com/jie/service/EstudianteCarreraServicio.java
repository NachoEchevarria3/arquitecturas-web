package com.jie.service;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.model.EstudianteCarrera;
import com.jie.repository.EstudianteCarreraRepositoryImpl;
import com.jie.repository.Repository;
import com.jie.util.MySqlHibernateUtil;

import java.time.LocalDate;

public class EstudianteCarreraServicio {
    private Repository<EstudianteCarrera> estudianteCarreraRepository;

    public EstudianteCarreraServicio(RepositoryFactory repositoryFactory) {
        this.estudianteCarreraRepository = repositoryFactory.getEstudianteCarreraRepository();
    }

    public void matricularEstudiante(Estudiante estudiante, Carrera carrera, LocalDate fecha) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
        this.estudianteCarreraRepository.save(new EstudianteCarrera(estudiante, carrera, fecha));
    }

    public void graduarEstudiante(Estudiante estudiante, Carrera carrera, LocalDate fecha) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
    }
}
