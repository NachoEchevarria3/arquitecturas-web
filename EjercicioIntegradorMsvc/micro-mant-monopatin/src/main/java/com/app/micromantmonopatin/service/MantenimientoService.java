package com.app.micromantmonopatin.service;

import com.app.micromantmonopatin.client.MonopatinClient;
import com.app.micromantmonopatin.constant.EstadoMonopatin;
import com.app.micromantmonopatin.dto.CreateMantenimientoDTO;
import com.app.micromantmonopatin.dto.MantenimientoDTO;
import com.app.micromantmonopatin.entity.Mantenimiento;
import com.app.micromantmonopatin.repository.MantenimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {
    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MonopatinClient monopatinClient;

    public MantenimientoDTO create(CreateMantenimientoDTO mantenimientoDto) {
        if (mantenimientoDto == null) throw new IllegalArgumentException("El mantenimiento no puede ser nulo.");

        Mantenimiento mantenimiento = new Mantenimiento(mantenimientoDto.idMonopatin());

        if (monopatinClient.getMonopatinById(mantenimiento.getIdMonopatin()).data().estado().equals(EstadoMonopatin.MANTENIMIENTO)) {
            throw new IllegalArgumentException("El monopatin ya esta en mantenimiento.");
        }

        mantenimientoRepository.save(mantenimiento);
        monopatinClient.actualizarEstadoMonopatin(mantenimiento.getIdMonopatin(), EstadoMonopatin.MANTENIMIENTO);
        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getIdMonopatin(),
                mantenimiento.getFechaInicio(),
                mantenimiento.getFechaFin()
        );
    }

    public MantenimientoDTO findById(Long id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID invalido.");
        Mantenimiento mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El mantenimiento no existe."));
        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getIdMonopatin(),
                mantenimiento.getFechaInicio(),
                mantenimiento.getFechaFin()
        );
    }

    public List<MantenimientoDTO> findAll() {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAll();

        if (mantenimientos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron mantenimientos.");
        }

        return mantenimientos.stream().map(m -> new MantenimientoDTO(
                m.getId(),
                m.getIdMonopatin(),
                m.getFechaInicio(),
                m.getFechaFin()
        )).toList();
    }

    public List<MantenimientoDTO> findAllByIdMonopatin(Long idMonopatin) {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAllByIdMonopatin(idMonopatin);

        if (mantenimientos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron mantenimientos para el monopatin " + idMonopatin + ".");
        }

        return mantenimientos.stream().map(m -> new MantenimientoDTO(
                m.getId(),
                m.getIdMonopatin(),
                m.getFechaInicio(),
                m.getFechaFin()
        )).toList();
    }

    public MantenimientoDTO finalizarMantenimiento(Long idMantenimiento) {
        Mantenimiento mantenimiento = mantenimientoRepository.findById(idMantenimiento)
                .orElseThrow(() -> new EntityNotFoundException("El mantenimiento no existe."));

        if (monopatinClient.getMonopatinById(mantenimiento.getIdMonopatin()).data().estado().equals(EstadoMonopatin.DISPONIBLE)) {
            throw new IllegalArgumentException("El monopatin no estaba en mantenimiento.");
        }

        mantenimiento.setFechaFin(LocalDateTime.now());
        mantenimientoRepository.save(mantenimiento);
        monopatinClient.actualizarEstadoMonopatin(mantenimiento.getIdMonopatin(), EstadoMonopatin.DISPONIBLE);
        monopatinClient.resetEstadisticas(mantenimiento.getIdMonopatin());
        return new MantenimientoDTO(
                mantenimiento.getId(),
                mantenimiento.getIdMonopatin(),
                mantenimiento.getFechaInicio(),
                mantenimiento.getFechaFin()
        );
    }
}
