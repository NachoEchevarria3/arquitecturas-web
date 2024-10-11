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

    public Estudiante create(Estudiante estudiante) {
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no puede ser nulo");
        if (this.estudianteRepository.findById(estudiante.getDni()).isPresent()) {
            throw new IllegalArgumentException("El estudiante con dni " + estudiante.getDni() + " ya existe");
        }

        return this.estudianteRepository.save(estudiante);
    }

    public Estudiante update(int id, Estudiante info) {
        if (info == null) throw new IllegalArgumentException("Info no puede ser nulo");
        Estudiante estudiante = this.estudianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

        estudiante.setNombre(info.getNombre());
        estudiante.setApellido(info.getApellido());
        estudiante.setEdad(info.getEdad());
        estudiante.setGenero(info.getGenero());
        estudiante.setCiudadResidencia(info.getCiudadResidencia());
        estudiante.setNumeroLibreta(info.getNumeroLibreta());

        return this.estudianteRepository.save(estudiante);
    }

    public Estudiante findById(int id) {
        if (id < 1) throw new IllegalArgumentException("El ID no puede ser negativo");
        return this.estudianteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("EL estudiante no existe"));
    }

    public List<Estudiante> findAll() {
        return this.estudianteRepository.findAll();
    }

    public void delete(int id) {
        if (!this.estudianteRepository.existsById(id)) throw new EntityNotFoundException("El estudiante no existe");

        this.estudianteRepository.deleteById(id);
    }

    // Ejercicio 2 C, ordena por edad.
    public List<Estudiante> findAllOrederedByEdad() {
        return this.estudianteRepository.findAll(Sort.by(Sort.Direction.DESC, "edad"));
    }

    // Ejercicio 2d
    public Estudiante findByNumeroLibreta(int numeroLibreta) {
        if (numeroLibreta < 1) throw new IllegalArgumentException("Numero de libreta no puede ser negativo");
        return this.estudianteRepository.findEstudianteByNumeroLibreta(numeroLibreta)
                .orElseThrow(() -> new EntityNotFoundException("El estudiante con libreta " + numeroLibreta + " no existe"));
    }

    // Ejercicio 2e
    public List<Estudiante> findAllByGenero(String genero) {
        return this.estudianteRepository.findEstudiantesByGenero(genero);
    }
}
