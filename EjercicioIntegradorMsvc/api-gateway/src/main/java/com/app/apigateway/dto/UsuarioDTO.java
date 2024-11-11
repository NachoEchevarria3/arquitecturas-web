package com.app.apigateway.dto;

import java.time.LocalDate;
import java.util.Set;

public record UsuarioDTO(
        Long id,
        String nombre,
        String apellido,
        String email,
        String telefono,
        LocalDate fechaAlta,
        Boolean activo,
        Set<Long> idsCuentasMp
) {
}
