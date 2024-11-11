package com.app.microviaje.client;

import com.app.microviaje.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-gateway-mercado-pago", url = "http://localhost:8080/api/mercado-pago")
public interface MercadoPagoClient {
    @GetMapping("/{id}/saldo")
    ApiResponse<Double> consultarSaldo(@PathVariable Long id);
}
