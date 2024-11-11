package com.app.microviaje.client;

import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-tarifa", url = "localhost:8080/api/tarifa")
public interface TarifaClient {
    @GetMapping("/actual")
    ApiResponse<TarifaDTO> getTarifaActual();
}
