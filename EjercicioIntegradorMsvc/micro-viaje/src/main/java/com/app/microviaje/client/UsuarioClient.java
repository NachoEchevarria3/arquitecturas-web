package com.app.microviaje.client;

import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-gateway-usuario", url = "http://localhost:8080/api/usuario")
public interface UsuarioClient {
    @GetMapping("/{id}")
    ApiResponse<UsuarioDTO> getUsuario(@PathVariable Long id);
}
