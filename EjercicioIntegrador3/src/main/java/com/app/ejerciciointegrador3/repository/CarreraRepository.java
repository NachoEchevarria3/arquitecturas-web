package com.app.ejerciciointegrador3.repository;

import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
    @Query("SELECT c.id, c.nombre, COUNT(c) AS cantidadInscriptos FROM Carrera c JOIN c.estudiantes e GROUP BY c.id HAVING COUNT(c) > 0 ORDER BY cantidadInscriptos DESC")
    List<Object[]> findAllOrderedByCantidadInscriptos();

    @Query("SELECT e FROM Estudiante e JOIN e.carreras ec WHERE ec.carrera = :carrera AND e.ciudadResidencia = :ciudad")
    List<Estudiante> findAllEstudiantesByCarreraAndCiudad(@Param("carrera") Carrera carrera, @Param("ciudad") String ciudad);

    @Query(value = "SELECT DISTINCT c.nombre, anios.year, " +
            "COUNT(CASE WHEN ec.anio_inscripcion = anios.year THEN 1 END) OVER (PARTITION BY c.nombre, anios.year) AS inscriptos, " +
            "COUNT(CASE WHEN ec.anio_graduacion = anios.year THEN 1 END) OVER (PARTITION BY c.nombre, anios.year) AS egresados " +
            "FROM (SELECT anio_inscripcion AS year FROM estudiante_carrera " +
            "      UNION " +
            "      SELECT anio_graduacion FROM estudiante_carrera " +
            "      ORDER BY year) AS anios " +
            "INNER JOIN estudiante_carrera ec ON anios.year = anio_inscripcion || anios.year = anio_graduacion " +
            "INNER JOIN carrera c ON c.id = ec.carrera_id " +
            "WHERE year != 0 " +
            "ORDER BY c.nombre, anios.year", nativeQuery = true)
    List<Object[]> generarReporteCarreras();
}
