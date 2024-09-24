package com.jie.repository;

import com.jie.dto.CarreraDto;
import com.jie.model.Carrera;

import java.util.List;

public interface CarreraRepository extends Repository<Carrera> {
    List<CarreraDto> findAllOrderedByCantidadInscriptos();
}
