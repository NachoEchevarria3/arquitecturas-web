package com.app.microviaje.service;

import com.app.microviaje.client.*;
import com.app.microviaje.constant.EstadoMonopatin;
import com.app.microviaje.dto.*;
import com.app.microviaje.entity.Pausa;
import com.app.microviaje.entity.Viaje;
import com.app.microviaje.projections.MonopatinProjection;
import com.app.microviaje.repository.ViajeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViajeService {
    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TarifaClient tarifaClient;

    @Autowired
    private MonopatinClient monopatinClient;

    @Autowired
    private PagoClient pagoClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private MercadoPagoClient mercadoPagoClient;

    public void comenzarViaje(ComenzarViajeDTO infoViaje) {
        if (infoViaje == null) throw new IllegalArgumentException("infoViaje no puede ser nulo.");

        Viaje viaje = crearViaje(infoViaje);

        if (viaje.getFechaFin() != null) throw new IllegalArgumentException("El viaje ya ha finalizado.");

        viajeRepository.save(viaje);
        monopatinClient.actualizarEstado(infoViaje.idMonopatin(), EstadoMonopatin.VIAJANDO);
    }

    private Viaje crearViaje(ComenzarViajeDTO infoViaje) {
        ApiResponse<MonopatinDTO> monopatinRes = monopatinClient.getMonopatinById(infoViaje.idMonopatin());

        if (!monopatinRes.data().estado().equals(EstadoMonopatin.DISPONIBLE)) {
            throw new IllegalArgumentException("El monopatin no esta disponible.");
        }

        ApiResponse<UsuarioDTO> usuarioRes = usuarioClient.getUsuario(infoViaje.idUsuario());

        if (!usuarioRes.data().activo()) throw new IllegalArgumentException("Tu cuenta esta suspendida.");

        if (!usuarioRes.data().idsCuentasMp().contains(infoViaje.idCuentaMercadoPago())) throw new IllegalArgumentException("El usuario no tiene una cuenta asociada con el id " + infoViaje.idCuentaMercadoPago() + ".");

        ApiResponse<Double> saldoCuentaRes = mercadoPagoClient.consultarSaldo(infoViaje.idCuentaMercadoPago());

        if (saldoCuentaRes.data() <= 0) throw new IllegalArgumentException("No tienes suficiente saldo para comenzar el viaje, intenta con otra cuenta.");

        ApiResponse<TarifaDTO> tarifaActualRes = tarifaClient.getTarifaActual();

        return new Viaje(
                infoViaje.idMonopatin(),
                infoViaje.idUsuario(),
                infoViaje.idCuentaMercadoPago(),
                tarifaActualRes.data().tarifaInicial(),
                tarifaActualRes.data().tarifaPorMinuto(),
                tarifaActualRes.data().tarifaPausaEstensa()
        );
    }

    public void pausarViaje(Long idViaje) {
        if (idViaje == null || idViaje <= 0) throw new IllegalArgumentException("ID invalido.");

        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el viaje con id " + idViaje));

        if (viaje.getFechaFin() != null) throw new IllegalArgumentException("El viaje ya ha finalizado.");

        viaje.getPausas().add(new Pausa(viaje));

        viajeRepository.save(viaje);
    }

    public void reanudarViaje(Long idViaje, ReanudarViajeDTO reanudarViajeDto) {
        if (idViaje == null || idViaje <= 0) throw new IllegalArgumentException("ID invalido.");
        if (reanudarViajeDto == null) throw new IllegalArgumentException("reanudarViajeDto no puede ser nulo.");

        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el viaje con id " + idViaje));

        if (viaje.getFechaFin() != null) throw new IllegalArgumentException("El viaje ya ha finalizado.");

        Pausa pausa = viaje.getPausas().stream().filter(p -> p.getId().equals(reanudarViajeDto.idPausa())).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No se encontro la pausa."));

        pausa.setFechaFin(LocalDateTime.now());

        viajeRepository.save(viaje);
    }

    public void finalizarViaje(Long idViaje, FinalizarViajeDTO finalizarViajeDto) {
        if (idViaje == null || idViaje <= 0) throw new IllegalArgumentException("ID invalido.");
        if (finalizarViajeDto == null) throw new IllegalArgumentException("finalizarViajeDto no puede ser nulo.");

        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el viaje con id " + idViaje));

        if (viaje.getFechaFin() != null) throw new IllegalArgumentException("El viaje ya ha finalizado.");

        viaje.setFechaFin(LocalDateTime.now());

        viaje.setKilometros(finalizarViajeDto.kilometrosRecorridos());
        viaje.setTiempo(calcularTiempo(viaje.getFechaInicio(), viaje.getFechaFin()));
        actualizarEstadisticasMonopatin(viaje.getIdMonopatin(), viaje.getKilometros(), viaje.getTiempo(), calcularTiempoDePausaTotal(viaje));

        ApiResponse<ParadaDTO> paradaRes = monopatinClient.ubicarMonopatinEnParada(viaje.getIdMonopatin(), finalizarViajeDto.idParada());
        viaje.setIdParadaDestino(paradaRes.data().id());

        viaje.setPrecioTotal(calcularPrecioTotal(viaje));
        pagoClient.pagar(new PagarViajeDTO(viaje.getId(), viaje.getIdUsuario(), viaje.getMetodoPagoAsociado(), viaje.getPrecioTotal()));

        viajeRepository.save(viaje);
    }

    private void actualizarEstadisticasMonopatin(Long idMonopatin, Integer kilometros, Integer tiempoDeUso, Integer tiempoDePausa) {
        monopatinClient.actualizarKilometros(idMonopatin, kilometros);
        monopatinClient.actualizarTiempoDeUso(idMonopatin, tiempoDeUso);
        if (tiempoDePausa != 0) monopatinClient.actualizarTiempoDePausa(idMonopatin, tiempoDePausa);
    }

    private Double calcularPrecioTotal(Viaje viaje) {
        double precioTotal = 0.0;

        precioTotal += viaje.getTarifaInicial();

        Pausa pausaExtensa = viaje.getPausas().stream()
                .filter(p -> calcularTiempo(p.getFechaInicio(), p.getFechaFin()) > 5)
                .findFirst().orElse(null);

        if (pausaExtensa != null) {
            // Tarifa normal hasta el inicio de la pausa extensa, incluyendo los primeros 15 minutos de la pausa
            int tiempoAntesPausaExtensa = calcularTiempo(viaje.getFechaInicio(), pausaExtensa.getFechaInicio()) + 5;
            precioTotal += viaje.getTarifaPorMinuto() * tiempoAntesPausaExtensa;

            // Tarifa extra dentro de la pausa extensa, solo el tiempo que excede los primeros 15 minutos
            int tiempoExtraPausa = calcularTiempo(pausaExtensa.getFechaInicio(), pausaExtensa.getFechaFin()) - 5;
            precioTotal += viaje.getTarifaPausaExtensa() * tiempoExtraPausa;

            // Tarifa extra para el resto del viaje después de la pausa extensa
            int tiempoPostPausa = calcularTiempo(pausaExtensa.getFechaFin(), viaje.getFechaFin());
            precioTotal += viaje.getTarifaPausaExtensa() * tiempoPostPausa;
        } else {
            precioTotal += viaje.getTarifaPorMinuto() * viaje.getTiempo();
        }

        return precioTotal;
    }

    private Integer calcularTiempo(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null) throw new IllegalArgumentException("inicio no puede ser nulo.");
        if (fin == null) throw new IllegalArgumentException("fin no puede ser nulo.");

        return (int) Duration.between(inicio, fin).toMinutes();
    }

    private Integer calcularTiempoDePausaTotal(Viaje viaje) {
        int tiempoPausaTotal = 0;

        // Si no hay pausas retorna 0
        if (viaje.getPausas().isEmpty()) return tiempoPausaTotal;

        for (Pausa pausa: viaje.getPausas()) {
            tiempoPausaTotal += calcularTiempo(pausa.getFechaInicio(), pausa.getFechaFin());
        }

        return tiempoPausaTotal;
    }

    public List<Long> getMonopatinesConMinimoDeViajes(Integer minimoViajes, LocalDate fechaDesde, LocalDate fechaHasta) {
        if (minimoViajes == null) throw new IllegalArgumentException("minimoViajes no puede ser nulo.");
        if (fechaDesde == null) throw new IllegalArgumentException("minimoViajes no puede ser nulo.");
        if (fechaHasta == null) throw new IllegalArgumentException("minimoViajes no puede ser nulo.");

        List<MonopatinProjection> monopatines = viajeRepository.getMonopatinesConMinimoDeViajesEnAño(minimoViajes, fechaDesde, fechaHasta);
        return monopatines.stream().map(MonopatinProjection::getIdMonopatin).toList();
    }
}
