package com.app.microviaje.controller;

import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.ComenzarViajeDTO;
import com.app.microviaje.dto.FinalizarViajeDTO;
import com.app.microviaje.dto.ReanudarViajeDTO;
import com.app.microviaje.entity.Viaje;
import com.app.microviaje.service.ViajeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/viaje")
public class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @GetMapping("/monopatines/minimo-viajes/{minimoViajes}")
    public ResponseEntity<ApiResponse<List<Long>>> getMonopatinesConMinimoDeViajes(
            @PathVariable Integer minimoViajes,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                viajeService.getMonopatinesConMinimoDeViajes(minimoViajes, fechaDesde, fechaHasta)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> comenzarViaje(@Valid @RequestBody ComenzarViajeDTO viaje) {
        viajeService.comenzarViaje(viaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Viaje comenzado con éxito.",
                null
        ));
    }

    @PostMapping("/{id}/pausar")
    public ResponseEntity<ApiResponse<?>> pausarViaje(@PathVariable Long id) {
        viajeService.pausarViaje(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Viaje pausado con éxito.",
                null
        ));
    }

    @PostMapping("/{id}/reanudar")
    public ResponseEntity<ApiResponse<?>> reanudarViaje(@PathVariable Long id, @Valid @RequestBody ReanudarViajeDTO infoReanudarViaje) {
        viajeService.reanudarViaje(id, infoReanudarViaje);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Viaje reanudado con éxito.",
                null
        ));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponse<?>> finalizarViaje(@PathVariable Long id, @Valid @RequestBody FinalizarViajeDTO viaje) {
        viajeService.finalizarViaje(id, viaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Viaje finalizado con éxito.",
                null
        ));
    }
}
