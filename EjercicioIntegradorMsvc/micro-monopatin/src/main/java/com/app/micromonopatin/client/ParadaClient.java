package com.app.micromonopatin.client;

import com.app.micromonopatin.dto.ApiResponse;
import com.app.micromonopatin.dto.ParadaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-parada", url = "localhost:8080/api/parada")
public interface ParadaClient {
    @GetMapping("/{id}")
    ApiResponse<ParadaDTO> getParadaById(@PathVariable("id") Long id);
}
