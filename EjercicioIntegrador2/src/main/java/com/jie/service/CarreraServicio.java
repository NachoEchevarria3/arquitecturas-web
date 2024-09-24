package com.jie.service;

import com.jie.dto.CarreraDto;
import com.jie.model.Carrera;
import com.jie.repository.CarreraRepository;
import com.jie.repository.CarreraRepositoryImpl;
import com.jie.repository.Repository;
import com.jie.util.HibernateUtil;

import java.util.List;

public class CarreraServicio {
    private final CarreraRepository carreraRepository;

    public CarreraServicio() {
        this.carreraRepository = new CarreraRepositoryImpl(HibernateUtil.getEntityManager());
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

    public List<CarreraDto> findAllOrderedByCantidadInscriptos() {
        return this.carreraRepository.findAllOrderedByCantidadInscriptos();
    }
}
