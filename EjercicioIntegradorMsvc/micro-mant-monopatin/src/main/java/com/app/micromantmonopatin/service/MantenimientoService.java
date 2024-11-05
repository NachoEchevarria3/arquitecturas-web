package com.app.micromantmonopatin.service;

import com.app.micromantmonopatin.client.MonopatinClient;
import com.app.micromantmonopatin.constant.EstadoMonopatin;
import com.app.micromantmonopatin.dto.EstadoDTO;
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

    public Mantenimiento create(Mantenimiento mantenimiento) {
        if (mantenimiento == null) throw new IllegalArgumentException("El mantenimiento no puede ser nulo.");

        if (monopatinClient.getMonopatinById(mantenimiento.getIdMonopatin()).estado().equals(EstadoMonopatin.MANTENIMIENTO)) {
            throw new IllegalArgumentException("El monopatin ya esta en mantenimiento.");
        }

        monopatinClient.actualizarEstadoMonopatin(mantenimiento.getIdMonopatin(), new EstadoDTO(EstadoMonopatin.MANTENIMIENTO));
        return mantenimientoRepository.save(mantenimiento);
    }

    public Mantenimiento findById(Long id) {
        if (id == null || id < 0) throw new IllegalArgumentException("ID invalido.");
        return mantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El mantenimiento no existe."));
    }

    public List<Mantenimiento> findAll() {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAll();

        if (mantenimientos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron mantenimientos.");
        }

        return mantenimientos;
    }

    public List<Mantenimiento> findAllByIdMonopatin(Long idMonopatin) {
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findAllByIdMonopatin(idMonopatin);

        if (mantenimientos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron mantenimientos para el monopatin " + idMonopatin + ".");
        }

        return mantenimientos;
    }

    public Mantenimiento finalizarMantenimiento(Long idMantenimiento) {
        Mantenimiento mantenimiento = findById(idMantenimiento);

        if (monopatinClient.getMonopatinById(mantenimiento.getIdMonopatin()).estado().equals(EstadoMonopatin.DISPONIBLE)) {
            throw new IllegalArgumentException("El monopatin no estaba en mantenimiento.");
        }

        mantenimiento.setFechaFin(LocalDateTime.now());
        monopatinClient.actualizarEstadoMonopatin(mantenimiento.getIdMonopatin(), new EstadoDTO(EstadoMonopatin.DISPONIBLE));
        return mantenimientoRepository.save(mantenimiento);
    }
}
