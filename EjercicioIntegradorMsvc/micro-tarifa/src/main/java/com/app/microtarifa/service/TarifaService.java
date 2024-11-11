package com.app.microtarifa.service;

import com.app.microtarifa.dto.CreateTarifaDTO;
import com.app.microtarifa.dto.TarifaDTO;
import com.app.microtarifa.entity.Tarifa;
import com.app.microtarifa.repository.TarifaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarifaService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public void create(CreateTarifaDTO tarifa) {
        if (tarifa == null) throw new IllegalArgumentException("Tarifa no puede ser nulo.");
        tarifaRepository.save(new Tarifa(
                tarifa.tarifaInicial(),
                tarifa.tarifaPorMinuto(),
                tarifa.tarifaPausaEstensa(),
                tarifa.validaDesde()
        ));
    }

    public TarifaDTO getTarifaActual() {
        Tarifa tarifa = tarifaRepository.findTarifaActual(LocalDate.now()).stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No hay tarifa configurada."));

        return new TarifaDTO(
                tarifa.getId(),
                tarifa.getTarifaInicial(),
                tarifa.getTarifaPorMinuto(),
                tarifa.getTarifaPausaExtensa(),
                tarifa.getValidaDesde()
        );
    }

    public TarifaDTO actualizarTarifa(CreateTarifaDTO tarifa) {
        if (tarifa.validaDesde().isBefore(LocalDate.now())) throw new IllegalArgumentException("La fecha de vigencia de la tarifa debe ser mayor a la fecha actual.");

        Tarifa tarifaVigente = tarifaRepository.findTarifaActual(LocalDate.now()).stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No hay tarifa configurada."));

        tarifaVigente.setTarifaInicial(tarifa.tarifaInicial());
        tarifaVigente.setTarifaPorMinuto(tarifa.tarifaPorMinuto());
        tarifaVigente.setTarifaPausaExtensa(tarifa.tarifaPausaEstensa());
        tarifaVigente.setValidaDesde(tarifa.validaDesde());

        tarifaRepository.save(tarifaVigente);
        return new TarifaDTO(
                tarifaVigente.getId(),
                tarifaVigente.getTarifaInicial(),
                tarifaVigente.getTarifaPorMinuto(),
                tarifaVigente.getTarifaPausaExtensa(),
                tarifaVigente.getValidaDesde()
        );
    }

    public TarifaDTO crearTarifa(CreateTarifaDTO tarifa) {
        if (tarifa.validaDesde().isBefore(LocalDate.now())) throw new IllegalArgumentException("La fecha de vigencia de la tarifa debe ser mayor a la fecha actual.");

        if (tarifaRepository.count() == 0) {
            Tarifa nuevaTarifa = new Tarifa(tarifa.tarifaInicial(), tarifa.tarifaPorMinuto(), tarifa.tarifaPausaEstensa(), tarifa.validaDesde());
            tarifaRepository.save(nuevaTarifa);
            return new TarifaDTO(
                    nuevaTarifa.getId(),
                    nuevaTarifa.getTarifaInicial(),
                    nuevaTarifa.getTarifaPorMinuto(),
                    nuevaTarifa.getTarifaPausaExtensa(),
                    nuevaTarifa.getValidaDesde()
            );
        } else {
            return actualizarTarifa(tarifa);
        }
    }

    public List<TarifaDTO> findAll() {
        List<Tarifa> tarifas = tarifaRepository.findByOrderByValidaDesdeDesc();

        if (tarifas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron tarifas.");
        }

        return tarifas.stream().map(t -> new TarifaDTO(
                t.getId(),
                t.getTarifaInicial(),
                t.getTarifaPorMinuto(),
                t.getTarifaPausaExtensa(),
                t.getValidaDesde()
        )).toList();
    }
}
