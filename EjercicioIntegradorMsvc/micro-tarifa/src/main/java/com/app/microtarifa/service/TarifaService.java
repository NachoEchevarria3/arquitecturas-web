package com.app.microtarifa.service;

import com.app.microtarifa.dto.ActualizarTarifaDTO;
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

    public TarifaDTO actualizarTarifa(ActualizarTarifaDTO tarifa) {
        if (tarifa.validaDesde().isBefore(LocalDate.now())) throw new IllegalArgumentException("La fecha de vigencia de la tarifa debe ser mayor a la fecha actual.");


        Tarifa tarifaActualizada = tarifaRepository.save(new Tarifa(tarifa.tarifaInicial(), tarifa.tarifaPorMinuto(), tarifa.tarifaPausaEstensa(), tarifa.validaDesde()));

        return new TarifaDTO(
                tarifaActualizada.getId(),
                tarifaActualizada.getTarifaInicial(),
                tarifaActualizada.getTarifaPorMinuto(),
                tarifaActualizada.getTarifaPausaExtensa(),
                tarifaActualizada.getValidaDesde()
        );
    }

    public TarifaDTO crearTarifa(CreateTarifaDTO tarifa) {
        if (tarifaRepository.count() == 0) {
            Tarifa nuevaTarifa = tarifaRepository.save(new Tarifa(tarifa.tarifaInicial(), tarifa.tarifaPorMinuto(), tarifa.tarifaPausaEstensa(), LocalDate.now()));
            return new TarifaDTO(
                    nuevaTarifa.getId(),
                    nuevaTarifa.getTarifaInicial(),
                    nuevaTarifa.getTarifaPorMinuto(),
                    nuevaTarifa.getTarifaPausaExtensa(),
                    nuevaTarifa.getValidaDesde()
            );
        } else {
            return actualizarTarifa(
                    new ActualizarTarifaDTO(
                            tarifa.tarifaInicial(),
                            tarifa.tarifaPorMinuto(),
                            tarifa.tarifaPausaEstensa(),
                            LocalDate.now()
                    )
            );
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
