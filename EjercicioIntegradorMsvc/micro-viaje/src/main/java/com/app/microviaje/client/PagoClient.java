package com.app.microviaje.client;

import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.PagarViajeDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-pago", url = "localhost:8080/api/pago")
public interface PagoClient {
    @PostMapping
    ApiResponse<?> pagar(@Valid @RequestBody PagarViajeDTO infoPago);
}
