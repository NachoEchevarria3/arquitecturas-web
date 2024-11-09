package com.app.microparada.client;

import com.app.microparada.dto.ApiResponse;
import com.app.microparada.dto.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-monopatin", url = "http://localhost:8080/api/monopatin")
public interface MonopatinClient {
    @GetMapping("/parada/{idParada}")
    ApiResponse<List<MonopatinDTO>> getMonopatinesByParadaId(@PathVariable Long idParada);
}
