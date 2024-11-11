package com.app.micromantmonopatin.client;

import com.app.micromantmonopatin.dto.ApiResponse;
import com.app.micromantmonopatin.dto.MonopatinDTO;
import com.app.micromantmonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.dto.ParadaDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msvc-monopatin", url = "localhost:8080/api/monopatin")
public interface MonopatinClient {
    @PutMapping("/{idMonopatin}/estado/{estado}")
    ApiResponse<MonopatinDTO> actualizarEstadoMonopatin(@PathVariable Long idMonopatin, @Valid @PathVariable EstadoMonopatin estado);

    @PutMapping("/{idMonopatin}/reset-estadisticas")
    ApiResponse<?> resetEstadisticas(@PathVariable Long idMonopatin);

    @GetMapping("/{id}")
    ApiResponse<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PutMapping("/{idMonopatin}/ubicar-en-parada/{idParada}")
    ApiResponse<ParadaDTO> ubicarMonopatinEnParada(@PathVariable Long idMonopatin, @PathVariable Long idParada);
}
