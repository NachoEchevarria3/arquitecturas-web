package com.jie.repository;

import com.jie.dto.CarreraDto;
import com.jie.dto.ReporteCarreraDto;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;

import java.util.List;

public interface CarreraRepository extends Repository<Carrera> {
    List<CarreraDto> findAllOrderedByCantidadInscriptos();
    List<Estudiante> findAllEstudiantesByCarreraAndCiudad(Carrera carrera, String ciudad);
    List<ReporteCarreraDto> generarReporteCarreras();
}
