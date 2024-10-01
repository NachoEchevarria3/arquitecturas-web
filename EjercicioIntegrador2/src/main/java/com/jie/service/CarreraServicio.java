package com.jie.service;

import com.jie.dto.CarreraDto;
import com.jie.dto.ReporteCarreraDto;
import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.repository.CarreraRepository;
import com.jie.repository.CarreraRepositoryImpl;
import com.jie.util.MySqlHibernateUtil;

import java.util.List;

public class CarreraServicio {
    private final CarreraRepository carreraRepository;

    public CarreraServicio(RepositoryFactory repositoryFactory) {
        this.carreraRepository = (CarreraRepository) repositoryFactory.getCarreraRepository();
    }

    public void save(Carrera carrera) {
        if (carrera == null) throw new IllegalArgumentException("El carrera no puede ser nulo");
        this.carreraRepository.save(carrera);
    }

    public Carrera findById(int id) {
        if (id < 1) throw new IllegalArgumentException("Id no puede ser negativo");
        return this.carreraRepository.findById(id);
    }

    public List<Carrera> findAll() {
        return this.carreraRepository.findAll();
    }

    public void delete(Carrera carrera) {
        if (carrera == null) throw new IllegalArgumentException("El carrera no puede ser nulo");
        this.carreraRepository.delete(carrera);
    }

    // Ejercicio 2f
    public List<CarreraDto> findAllOrderedByCantidadInscriptos() {
        return this.carreraRepository.findAllOrderedByCantidadInscriptos();
    }

    // Ejercicio 2g
    public List<Estudiante> findAllEstudiantesByCarreraAndCiudad(Carrera carrera, String ciudad) {
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
        if (ciudad == null) throw new IllegalArgumentException("Ciudad no puede ser nulo");
        return this.carreraRepository.findAllEstudiantesByCarreraAndCiudad(carrera, ciudad);
    }

    // Ejercicio 3
    public List<ReporteCarreraDto> generarReporteCarreras() {
        return this.carreraRepository.generarReporteCarreras();
    }
}
