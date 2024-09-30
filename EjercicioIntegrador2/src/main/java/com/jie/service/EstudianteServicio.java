package com.jie.service;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Estudiante;
import com.jie.repository.EstudianteRepository;
import com.jie.repository.EstudianteRepositoryImpl;
import com.jie.util.MySqlHibernateUtil;

import java.util.List;

public class EstudianteServicio {
    private final EstudianteRepository estudianteRepository;

    public EstudianteServicio(RepositoryFactory repositoryFactory) {
        this.estudianteRepository = (EstudianteRepository) repositoryFactory.getEstudianteRepository();
    }

    public void save(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        this.estudianteRepository.save(estudiante);
    }

    public Estudiante findById(int id) {
        if (id < 1) throw new IllegalArgumentException("Id no puede ser negativo");
        return this.estudianteRepository.findById(id);
    }

    public List<Estudiante> findAll() {
        return this.estudianteRepository.findAll();
    }

    public void delete(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        this.estudianteRepository.delete(estudiante);
    }

    // Ejercicio 2 C, ordena por edad.
    public List<Estudiante> findAllOrederedByEdad() {
        return this.estudianteRepository.findAllOrderedByEdad();
    }

    public Estudiante findByNumeroLibreta(int numeroLibreta) {
        if (numeroLibreta < 1) throw new IllegalArgumentException("Numero de libreta no puede ser negativo");
        return this.estudianteRepository.findByNumeroLibreta(numeroLibreta);
    }

    public List<Estudiante> findAllByGenero(String genero) {
        return this.estudianteRepository.findAllByGenero(genero);
    }
}
