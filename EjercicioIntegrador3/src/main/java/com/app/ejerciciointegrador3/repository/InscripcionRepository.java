package com.app.ejerciciointegrador3.repository;

import com.app.ejerciciointegrador3.model.EstudianteCarrera;
import com.app.ejerciciointegrador3.model.EstudianteCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<EstudianteCarrera, EstudianteCarreraId> {
}
