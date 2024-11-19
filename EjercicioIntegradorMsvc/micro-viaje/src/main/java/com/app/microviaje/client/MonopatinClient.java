package com.app.microviaje.client;

import com.app.microviaje.constant.EstadoMonopatin;
import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.MonopatinDTO;
import com.app.microviaje.dto.ParadaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msvc-monopatin", url = "localhost:8080/api/monopatin")
public interface MonopatinClient {
    @PutMapping("/{idMonopatin}/estado/{estado}")
    ApiResponse<MonopatinDTO> actualizarEstado(@PathVariable Long idMonopatin, @PathVariable EstadoMonopatin estado);

    @GetMapping("/{id}")
    ApiResponse<MonopatinDTO> getMonopatinById(@PathVariable Long id);

    @PutMapping("/{idMonopatin}/ubicar-en-parada/{idParada}")
    ApiResponse<ParadaDTO> ubicarMonopatinEnParada(@PathVariable Long idMonopatin, @PathVariable String idParada);

    @PutMapping("/{idMonopatin}/kilometros/{cantKilometros}")
    ApiResponse<?> actualizarKilometros(@PathVariable Long idMonopatin, @PathVariable int cantKilometros);

    @PutMapping("/{idMonopatin}/tiempo-de-uso/{cantTiempoDeUso}")
    ApiResponse<?> actualizarTiempoDeUso(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDeUso);

    @PutMapping("/{idMonopatin}/tiempo-de-pausa/{cantTiempoDePausa}")
    ApiResponse<?> actualizarTiempoDePausa(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDePausa);
}
