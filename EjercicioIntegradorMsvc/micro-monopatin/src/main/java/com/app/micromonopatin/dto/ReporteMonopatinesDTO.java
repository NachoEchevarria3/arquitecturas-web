package com.app.micromonopatin.dto;

import java.util.List;

public record ReporteMonopatinesDTO(
        String tipoReporte,
        List<EstadisticaMonopatinDTO> monopatines
) {
}
