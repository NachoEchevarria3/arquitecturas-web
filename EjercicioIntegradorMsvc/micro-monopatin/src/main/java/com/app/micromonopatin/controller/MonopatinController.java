package com.app.micromonopatin.controller;

import com.app.micromonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.constant.TipoReporte;
import com.app.micromonopatin.dto.*;
import com.app.micromonopatin.service.MonopatinService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monopatin")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Monopatines")
public class MonopatinController {
    @Autowired
    private MonopatinService monopatinService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los monopatines",
            description = "Retorna una lista de todos los monopatines disponibles en el sistema.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatines obtenidos con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron monopatines"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatines() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findAll()
        ));
    }

    @GetMapping("/reporte")
    @Operation(
            summary = "Generar reporte de monopatines",
            description = "Obtiene un reporte según el tipo de reporte especificado.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reporte de monopatines obtenido con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron monopatines"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor"
                    )
            },
            parameters = {
                    @Parameter(description = "Tipo de reporte a generar", required = true, schema = @Schema(implementation = TipoReporte.class))
            }
    )
    public ResponseEntity<ApiResponse<ReporteMonopatinesDTO>> getReporteMonopatines(@Valid @RequestParam TipoReporte tipo) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Reporte de monopatines obtenido con éxito.",
                monopatinService.getReporteMonopatines(tipo)
        ));
    }

    @GetMapping("/minimo-viajes/{minimoViajes}/anio/{anio}")
    @Operation(
            summary = "Obtener monopatines con un mínimo de viajes en un año",
            description = "Devuelve una lista de monopatines que han realizado un número mínimo de viajes especificado durante un año dado.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatines obtenidos con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            },
            parameters = {
                    @Parameter(name = "minimoViajes", description = "Número mínimo de viajes", required = true),
                    @Parameter(name = "anio", description = "Año a considerar", required = true)
            }
    )
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesConMinimoDeViajes(
            @PathVariable Integer minimoViajes,
            @PathVariable Integer anio
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.getMonopatinesConMinimoDeViajes(minimoViajes, anio)
        ));
    }

    @GetMapping("/disponibles-vs-mantenimiento")
    @Operation(
            summary = "Obtener monopatines disponibles vs en mantenimiento",
            description = "Devuelve la cantidad de monopatines en operación y en mantenimiento",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatines obtenidos con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            }
    )
    public ResponseEntity<ApiResponse<Map<EstadoMonopatin, Integer>>> getMonopatinesDisponiblesVsMantenimiento() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.getMonopatinesEnOperacionYEnMantenimiento()
        ));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener monopatin por ID",
            description = "Devuelve los detalles de un monopatín buscandolo según su ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatín obtenido con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Monopatin no encontrado."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            },
            parameters = {
                    @Parameter(name = "id", description = "ID del monopatín", required = true)
            }
    )
    public ResponseEntity<ApiResponse<MonopatinDTO>> getMonopatinById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatin obtenido con éxito",
                monopatinService.findById(id)
        ));
    }

    @GetMapping("/parada/{idParada}")
    @Operation(
            summary = "Obtener monopatines en una parada específica",
            description = "Devuelve una lista de monopatines que se encuentran en la parada indicada.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatines obtenidos con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Parada no encontrada."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            },
            parameters = {
                    @Parameter(name = "idParada", description = "ID de la parada", required = true)
            }
    )
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesByParadaId(@PathVariable String idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findMonopatinesByParadaId(idParada)
        ));
    }

    @GetMapping("/ubicacion/{ubicacion}")
    @Operation(
            summary = "Obtener monopatines en una ubicación específica",
            description = "Devuelve una lista de monopatines que se encuentran en la ubicación indicada.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Monopatines obtenidos con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron monopatines."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesByUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.findMonopatinesByUbicacion(ubicacion)
        ));
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo monopatín",
            description = "Crea un nuevo monopatín con los datos especificados.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles del nuevo monopatin a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateMonopatinDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Monopatín creado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Peticicón inválida."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )
            }
    )
    public ResponseEntity<ApiResponse<?>> createMonopatin(@RequestBody @Valid CreateMonopatinDTO monopatin) {
        monopatinService.create(monopatin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Monopatin creado con éxito",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/estado/{estado}")
    @Hidden
    public ResponseEntity<ApiResponse<MonopatinDTO>> actualizarEstado(@PathVariable Long idMonopatin, @PathVariable EstadoMonopatin estado) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Estado actualizado con éxito.",
                monopatinService.actualizarEstado(idMonopatin, estado)
        ));
    }

    @PutMapping("/{idMonopatin}/ubicar-en-parada/{idParada}")
    @Hidden
    public ResponseEntity<ApiResponse<ParadaDTO>> ubicarMonopatinEnParada(@PathVariable Long idMonopatin, @PathVariable String idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "El monopatin se ubicó en la parada con éxito.",
                monopatinService.ubicarMonopatinEnParada(idMonopatin, idParada)
        ));
    }

    @PutMapping("/{idMonopatin}/kilometros/{cantKilometros}")
    @Hidden
    public ResponseEntity<ApiResponse<?>> actualizarKilometros(@PathVariable Long idMonopatin, @PathVariable int cantKilometros) {
        monopatinService.actualizarKilometros(idMonopatin, cantKilometros);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Kilometros actualizados con éxito.",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/tiempo-de-uso/{cantTiempoDeUso}")
    @Hidden
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDeUso(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDeUso) {
        monopatinService.actualizarTiempoDeUso(idMonopatin, cantTiempoDeUso);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de uso actualizado con éxito.",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/tiempo-de-pausa/{cantTiempoDePausa}")
    @Hidden
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDePausa(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDePausa) {
        monopatinService.actualizarTiempoDePausa(idMonopatin, cantTiempoDePausa);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de pausa actualizado con éxito.",
                null
        ));
    }

    @PutMapping("/{idMonopatin}/reset-estadisticas")
    @Hidden
    public ResponseEntity<ApiResponse<?>> resetEstadisticas(@PathVariable Long idMonopatin) {
        monopatinService.resetKilometrosYTiempoDeUso(idMonopatin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Los kilometros del momopatin se resetearon con éxito.",
                null
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un monopatín",
            description = "Elimina un monopatín de forma permanente.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Monopatín eliminado con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Peticicón inválida."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "No autorizado / Token inválido."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error del servidor."
                    )

            },
            parameters = {
                    @Parameter(name = "id", description = "ID del monopatín a eliminar", required = true)
            }
    )
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) {
        monopatinService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
