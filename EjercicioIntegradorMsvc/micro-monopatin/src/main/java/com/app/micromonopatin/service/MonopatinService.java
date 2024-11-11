package com.app.micromonopatin.service;

import com.app.micromonopatin.client.ParadaClient;
import com.app.micromonopatin.client.ViajeClient;
import com.app.micromonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.constant.TipoReporte;
import com.app.micromonopatin.dto.*;
import com.app.micromonopatin.entity.Monopatin;
import com.app.micromonopatin.repository.MonopatinRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonopatinService {
    @Autowired
    private MonopatinRepository monopatinRepository;

    @Autowired
    private ParadaClient paradaClient;

    @Autowired
    private ViajeClient viajeClient;

    public void create(CreateMonopatinDTO monopatin) {
        if (monopatin == null) throw new IllegalArgumentException("monopatin no puede ser nulo.");

        ApiResponse<ParadaDTO> parada = paradaClient.getParadaById(monopatin.idParada());

        monopatinRepository.save(new Monopatin(monopatin.limiteKilometros(), monopatin.limiteTiempoDeUso(), parada.data().id()));
    }

    public List<MonopatinDTO> findAll() {
        List<Monopatin> monopatines = monopatinRepository.findAll();

        if (monopatines.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron monopatines.");
        }

        return monopatines.stream().map(m -> new MonopatinDTO(
                m.getId(),
                m.getEstado(),
                m.getKilometros(),
                m.getHistorialKilometros(),
                m.getLimiteKilometros(),
                m.getTiempoDeUso(),
                m.getHistorialTiempoDeUso(),
                m.getLimiteTiempoDeUso(),
                m.getIdParada()
        )).toList();
    }

    public MonopatinDTO findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");

        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));

        return new MonopatinDTO(
                monopatin.getId(),
                monopatin.getEstado(),
                monopatin.getKilometros(),
                monopatin.getHistorialKilometros(),
                monopatin.getLimiteKilometros(),
                monopatin.getTiempoDeUso(),
                monopatin.getHistorialTiempoDeUso(),
                monopatin.getLimiteTiempoDeUso(),
                monopatin.getIdParada()
        );
    }

    public List<MonopatinDTO> findMonopatinesByParadaId(Long paradaId) {
        ApiResponse<ParadaDTO> parada = paradaClient.getParadaById(paradaId);
        List<Monopatin> monopatines = monopatinRepository.findMonopatinByIdParada(paradaId);
        return monopatines.stream().map(m -> new MonopatinDTO(
                m.getId(),
                m.getEstado(),
                m.getKilometros(),
                m.getHistorialKilometros(),
                m.getLimiteKilometros(),
                m.getTiempoDeUso(),
                m.getHistorialTiempoDeUso(),
                m.getLimiteTiempoDeUso(),
                m.getIdParada()
        )).toList();
    }

    public void deleteById(Long id) {
        findById(id);
        monopatinRepository.deleteById(id);
    }

    public ParadaDTO ubicarMonopatinEnParada(Long monopatinId, Long paradaId) {
        ApiResponse<ParadaDTO> parada = paradaClient.getParadaById(paradaId);
        Monopatin monopatin = monopatinRepository.findById(monopatinId)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
        monopatin.setIdParada(parada.data().id());
        monopatin.setEstado(EstadoMonopatin.DISPONIBLE);
        monopatinRepository.save(monopatin);
        return new ParadaDTO(parada.data().id(), parada.data().ubicacion());
    }

    public MonopatinDTO actualizarEstado(Long id, EstadoMonopatin estado) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));

        // Si el monopatin pasa a viajar o a mantenimiento, se lo saca de la parada
        if (estado.equals(EstadoMonopatin.MANTENIMIENTO) || estado.equals(EstadoMonopatin.VIAJANDO)) {
            monopatin.setIdParada(null);
        }

        monopatin.setEstado(estado);
        monopatinRepository.save(monopatin);
        return new MonopatinDTO(
                monopatin.getId(),
                monopatin.getEstado(),
                monopatin.getKilometros(),
                monopatin.getHistorialKilometros(),
                monopatin.getLimiteKilometros(),
                monopatin.getTiempoDeUso(),
                monopatin.getHistorialTiempoDeUso(),
                monopatin.getLimiteTiempoDeUso(),
                monopatin.getIdParada()
        );
    }

    public void actualizarKilometros(Long id, int kilometros) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        if (kilometros < 0) throw new IllegalArgumentException("kilometros debe ser mayor o igual a 0.");
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
        monopatin.setKilometros(monopatin.getKilometros() + kilometros);
        monopatinRepository.save(monopatin);
    }

    public void actualizarTiempoDeUso(Long id, int tiempoDeUso) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        if (tiempoDeUso < 0) throw new IllegalArgumentException("tiempoDeUso debe ser mayor o igual a 0.");
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
        monopatin.setTiempoDeUso(monopatin.getTiempoDeUso() + tiempoDeUso);
        monopatinRepository.save(monopatin);
    }

    public void actualizarTiempoDePausa(Long id, int tiempoDePausa) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        if (tiempoDePausa < 0) throw new IllegalArgumentException("tiempoDeUso debe ser mayor o igual a 0.");
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
        monopatin.setTiempoDePausa(monopatin.getTiempoDePausa() + tiempoDePausa);
        monopatinRepository.save(monopatin);
    }

    public void resetKilometrosYTiempoDeUso(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe un monopatin con ese id."));
        monopatin.setKilometros(0);
        monopatin.setTiempoDeUso(0);
        monopatinRepository.save(monopatin);
    }

    public ReporteMonopatinesDTO getReporteMonopatines(TipoReporte tipoReporte) {
        if (findAll().isEmpty()) throw new EntityNotFoundException("No existen monopatines.");
        if (tipoReporte.equals(TipoReporte.KILOMETROS)) return getReporteMonopatinesPorKilometros();
        if (tipoReporte.equals(TipoReporte.TIEMPO_CON_PAUSAS)) return getReporteMonopatinesPorTiempoConPausas();
        if (tipoReporte.equals(TipoReporte.TIEMPO_SIN_PAUSAS)) return getReporteMonopatinesPorTiempoSinPausas();
        throw new IllegalArgumentException("Tipo de reporte no valido.");
    }

    private ReporteMonopatinesDTO getReporteMonopatinesPorKilometros() {
        List<Monopatin> monopatines = monopatinRepository.findByOrderByKilometrosDesc();
        return new ReporteMonopatinesDTO(
                "Reporte de uso de monopatines por kilómetros",
                monopatines.stream().map(m -> new EstadisticaMonopatinDTO(m.getId(), m.getKilometros())).toList()
        );
    }

    private ReporteMonopatinesDTO getReporteMonopatinesPorTiempoConPausas() {
        List<Monopatin> monopatines = monopatinRepository.findByOrderByTiempoDeUsoDesc();
        return new ReporteMonopatinesDTO(
                "Reporte de uso de monopatines por tiempo con pausas",
                monopatines.stream().map(m -> new EstadisticaMonopatinDTO(m.getId(), m.getTiempoDeUso())).toList()
        );
    }

    private ReporteMonopatinesDTO getReporteMonopatinesPorTiempoSinPausas() {
        List<Object[]> monopatines = monopatinRepository.findOrderByTiempoDeUsoSinPausaDesc();

        List<EstadisticaMonopatinDTO> estadisticasMonopatines = new ArrayList<>();

        for (Object[] monopatin : monopatines) {
            Long id = (Long) monopatin[0];
            int estadistica =  (int) monopatin[1];
            estadisticasMonopatines.add(new EstadisticaMonopatinDTO(id, estadistica));
        }

        return new ReporteMonopatinesDTO(
                "Reporte de uso de monopatines por tiempo sin pausas",
                estadisticasMonopatines
        );
    }

    public List<MonopatinDTO> getMonopatinesConMinimoDeViajes(Integer minimoViajes, Integer anio) {
        LocalDate fechaDesde = LocalDate.of(anio, 1, 1);
        LocalDate fechaHasta = LocalDate.of(anio, 12, 31);

        ApiResponse<List<Long>> idsMonopatinesRes = viajeClient.getMonopatinesConMinimoDeViajes(minimoViajes, fechaDesde, fechaHasta);

        List<Monopatin> monopatines = monopatinRepository.findByIds(idsMonopatinesRes.data());

        return monopatines.stream().map(m -> new MonopatinDTO(
                m.getId(),
                m.getEstado(),
                m.getKilometros(),
                m.getHistorialKilometros(),
                m.getLimiteKilometros(),
                m.getTiempoDeUso(),
                m.getHistorialTiempoDeUso(),
                m.getLimiteTiempoDeUso(),
                m.getIdParada()
        )).toList();
    }

    public Map<EstadoMonopatin, Integer> getMonopatinesEnOperacionYEnMantenimiento() {
        List<Monopatin> disponibles = monopatinRepository.findByEstado(EstadoMonopatin.DISPONIBLE);
        List<Monopatin> enMantenimiento = monopatinRepository.findByEstado(EstadoMonopatin.MANTENIMIENTO);
        Map<EstadoMonopatin, Integer> monopatines = new HashMap<>();
        monopatines.put(EstadoMonopatin.DISPONIBLE, disponibles.size());
        monopatines.put(EstadoMonopatin.MANTENIMIENTO, enMantenimiento.size());
        return monopatines;
    }
}
