package com.app.micromonopatin.service;

import com.app.micromonopatin.dto.CreateMonopatinDTO;
import com.app.micromonopatin.dto.EstadoDTO;
import com.app.micromonopatin.entity.Monopatin;
import com.app.micromonopatin.repository.MonopatinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonopatinService {
    @Autowired
    private MonopatinRepository monopatinRepository;

    public void create(CreateMonopatinDTO monopatin) {
        if (monopatin == null) throw new IllegalArgumentException("monopatin no puede ser nulo.");
        monopatinRepository.save(new Monopatin(monopatin.limiteKilometros(), monopatin.limiteTiempoDeUso()));
    }

    public List<Monopatin> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();

        if (monopatines.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron monopatines.");
        }

        return monopatines;
    }

    public Monopatin findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        return monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
    }

    public void deleteById(Long id) {
        findById(id);
        monopatinRepository.deleteById(id);
    }

    public Monopatin actualizarEstado(Long id, EstadoDTO estadoDTO) {
        Monopatin monopatin = findById(id);
        monopatin.setEstado(estadoDTO.estado());
        return monopatinRepository.save(monopatin);
    }

    public void actualizarKilometros(Long id, int kilometros) {
        if (kilometros < 0) throw new IllegalArgumentException("kilometros debe ser mayor o igual a 0.");
        Monopatin monopatin = findById(id);
        monopatin.setKilometros(monopatin.getKilometros() + kilometros);
        monopatinRepository.save(monopatin);
    }

    public void actualizarTiempoDeUso(Long id, int tiempoDeUso) {
        if (tiempoDeUso < 0) throw new IllegalArgumentException("tiempoDeUso debe ser mayor o igual a 0.");
        Monopatin monopatin = findById(id);
        monopatin.setTiempoDeUso(monopatin.getTiempoDeUso() + tiempoDeUso);
        monopatinRepository.save(monopatin);
    }
}
