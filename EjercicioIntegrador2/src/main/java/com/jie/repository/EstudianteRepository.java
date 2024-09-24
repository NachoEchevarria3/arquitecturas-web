package com.jie.repository;

import com.jie.model.Estudiante;

import java.util.List;

public interface EstudianteRepository extends Repository<Estudiante> {
    List<Estudiante> findAllOrderedByEdad();
    Estudiante findByNumeroLibreta(int numeroLibreta);
    List<Estudiante> findAllByGenero(String genero);
}
