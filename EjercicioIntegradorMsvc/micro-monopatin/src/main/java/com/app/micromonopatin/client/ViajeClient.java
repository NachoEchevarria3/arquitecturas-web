package com.app.micromonopatin.client;

import com.app.micromonopatin.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "msvc-viaje", url = "http://localhost:8080/api/viaje")
public interface ViajeClient {
    @GetMapping("/monopatines/minimo-viajes/{minimoViajes}")
    ApiResponse<List<Long>> getMonopatinesConMinimoDeViajes(
            @PathVariable Integer minimoViajes,
            @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @Valid @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    );
}
