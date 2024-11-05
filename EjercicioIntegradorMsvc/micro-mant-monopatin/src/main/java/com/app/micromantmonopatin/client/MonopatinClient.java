package com.app.micromantmonopatin.client;

import com.app.micromantmonopatin.dto.EstadoDTO;
import com.app.micromantmonopatin.dto.MonopatinDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-monopatin", url = "localhost:8080/api/monopatin")
public interface MonopatinClient {
    @PostMapping("/{idMonopatin}/estado")
    MonopatinDTO actualizarEstadoMonopatin(@PathVariable Long idMonopatin, @Valid @RequestBody EstadoDTO estadoDTO);

    @GetMapping("/{id}")
    MonopatinDTO getMonopatinById(@PathVariable Long id);
}
