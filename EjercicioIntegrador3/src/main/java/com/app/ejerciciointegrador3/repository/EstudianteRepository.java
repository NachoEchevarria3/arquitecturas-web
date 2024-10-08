package com.app.ejerciciointegrador3.repository;

import com.app.ejerciciointegrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    Estudiante findEstudianteByNumeroLibreta(int numeroLibreta);
    List<Estudiante> findEstudiantesByGenero(String genero);
}
