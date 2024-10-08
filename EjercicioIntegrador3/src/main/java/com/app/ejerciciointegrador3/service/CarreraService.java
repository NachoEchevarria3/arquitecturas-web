package com.app.ejerciciointegrador3.service;

import com.app.ejerciciointegrador3.dto.CarreraDto;
import com.app.ejerciciointegrador3.dto.ReporteCarreraDto;
import com.app.ejerciciointegrador3.model.Carrera;
import com.app.ejerciciointegrador3.model.Estudiante;
import com.app.ejerciciointegrador3.repository.CarreraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepository carreraRepository;

    public void save(Carrera carrera) {
        if (carrera == null) throw new IllegalArgumentException("La carrera no puede ser nulo");
        this.carreraRepository.save(carrera);
    }

    public Carrera findById(int id) {
        if (id < 1) throw new IllegalArgumentException("El ID no puede ser negativo");
        return this.carreraRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La carrera no existe"));
    }

    public List<Carrera> findAll() {
        return this.carreraRepository.findAll();
    }

    public void delete(int id) {
        if (id < 1) throw new IllegalArgumentException("El ID no puede ser negativo");
        this.carreraRepository.deleteById(id);
    }

    // Ejercicio 2f
    public List<CarreraDto> findAllOrderedByCantidadInscriptos() {
        List<CarreraDto> resultado = new ArrayList<>();
        List<Object[]> lista = this.carreraRepository.findAllOrderedByCantidadInscriptos();

        for (Object[] fila : lista) {
            int carreraId = (int) fila[0];
            String nombreCarrera = (String) fila[1];
            long cantidadInscriptos = (long) fila[2];
            resultado.add(new CarreraDto(carreraId, nombreCarrera, (int) cantidadInscriptos));
        }

        return resultado;
    }

    // Ejercicio 2g
    public List<Estudiante> findAllEstudiantesByCarreraAndCiudad(Carrera carrera, String ciudad) {
        if (carrera == null) throw new IllegalArgumentException("Carrera no puede ser nulo");
        if (ciudad == null) throw new IllegalArgumentException("Ciudad no puede ser nulo");
        return this.carreraRepository.findAllEstudiantesByCarreraAndCiudad(carrera, ciudad);
    }

    // Ejercicio 2h
    public List<ReporteCarreraDto> generarReporteCarreras() {
        List<ReporteCarreraDto> resultado = new ArrayList<>();
        List<Object[]> lista = this.carreraRepository.generarReporteCarreras();

        for (Object[] fila : lista) {
            String nombreCarrera = (String) fila[0];
            int anio = (int) fila[1];
            Long inscriptosLong = (Long) fila[2];
            Long graduadosLong = (Long) fila[3];
            long inscriptos = inscriptosLong;
            long graduados = graduadosLong;
            resultado.add(new ReporteCarreraDto(nombreCarrera, anio, (int) inscriptos, (int) graduados));
        }

        return resultado;
    }
}
