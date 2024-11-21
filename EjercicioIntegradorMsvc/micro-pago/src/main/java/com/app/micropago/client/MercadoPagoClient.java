package com.app.micropago.client;

import com.app.micropago.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "msvc-gateway", url = "http://localhost:8080/api/mercado-pago", configuration = FeignConfig.class)
public interface MercadoPagoClient {
    @PutMapping("/{id}/descontar-saldo/{monto}")
    ApiResponse<Double> descontarSaldo(@PathVariable Long id, @PathVariable Double monto);
}
