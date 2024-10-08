package com.app.ejerciciointegrador3.service;

import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    @Autowired
    private EstudianteRepository estudianteRepository;

    public void save(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("El estudiante no puede ser nulo");
        this.estudianteRepository.save(estudiante);
    }

    public Estudiante findById(int id) {
        if (id < 1) throw new IllegalArgumentException("El ID no puede ser negativo");
        return this.estudianteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("EL estudiante no existe"));
    }

    public List<Estudiante> findAll() {
        return this.estudianteRepository.findAll();
    }

    public void delete(int id) {
        if (id < 1) throw new IllegalArgumentException("El ID no puede ser negativo");
        this.estudianteRepository.deleteById(id);
    }

    // Ejercicio 2 C, ordena por edad.
    public List<Estudiante> findAllOrederedByEdad() {
        return this.estudianteRepository.findAll(Sort.by(Sort.Direction.DESC, "edad"));
    }

    // Ejercicio 2d
    public Estudiante findByNumeroLibreta(int numeroLibreta) {
        if (numeroLibreta < 1) throw new IllegalArgumentException("Numero de libreta no puede ser negativo");
        return this.estudianteRepository.findEstudianteByNumeroLibreta(numeroLibreta);
    }

    // Ejercicio 2e
    public List<Estudiante> findAllByGenero(String genero) {
        return this.estudianteRepository.findEstudiantesByGenero(genero);
    }
}
