package com.app.micromonopatin.controller;

import com.app.micromonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.constant.TipoReporte;
import com.app.micromonopatin.dto.*;
import com.app.micromonopatin.service.MonopatinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monopatin")
public class MonopatinController {
    @Autowired
    private MonopatinService monopatinService;

    @Operation(
            summary = "Obtener todos los monopatines",
            description = "Retorna una lista de todos los monopatines disponibles en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatines obtenidos con éxito")
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatines() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findAll()
        ));
    }

    @Operation(
            summary = "Generar reporte de monopatines",
            description = "Obtiene un reporte según el tipo de reporte especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte de monopatines obtenido con éxito.")
            },
            parameters = {
                    @Parameter(description = "Tipo de reporte a generar", required = true, schema = @Schema(implementation = TipoReporte.class))
            }
    )
    @GetMapping("/reporte")
    public ResponseEntity<ApiResponse<ReporteMonopatinesDTO>> getReporteMonopatines(@Valid @RequestParam TipoReporte tipo) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Reporte de monopatines obtenido con éxito.",
                monopatinService.getReporteMonopatines(tipo)
        ));
    }

    @Operation(
            summary = "Obtener monopatines con un mínimo de viajes en un año",
            description = "Devuelve una lista de monopatines que han realizado un número mínimo de viajes especificado durante un año dado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatines obtenidos con éxito.")
            },
            parameters = {
                    @Parameter(description = "Número mínimo de viajes", required = true),
                    @Parameter(description = "Año a considerar", required = true)
            }
    )
    @GetMapping("/minimo-viajes/{minimoViajes}/anio/{anio}")
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesConMinimoDeViajes(@PathVariable Integer minimoViajes, @PathVariable Integer anio) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.getMonopatinesConMinimoDeViajes(minimoViajes, anio)
        ));
    }

    @Operation(
            summary = "Obtener monopatines disponibles vs en mantenimiento",
            description = "Devuelve la cantidad de monopatines en operación y en mantenimiento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatines obtenidos con éxito.")
            }
    )
    @GetMapping("/disponibles-vs-mantenimiento")
    public ResponseEntity<ApiResponse<Map<EstadoMonopatin, Integer>>> getMonopatinesDisponiblesVsMantenimiento() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.getMonopatinesEnOperacionYEnMantenimiento()
        ));
    }

    @Operation(
            summary = "Obtener monopatin por ID",
            description = "Devuelve los detalles de un monopatín buscandolo según su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatín obtenido con éxito")
            },
            parameters = {
                    @Parameter(description = "ID del monopatín", required = true)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MonopatinDTO>> getMonopatinById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatin obtenido con éxito",
                monopatinService.findById(id)
        ));
    }

    @Operation(
            summary = "Obtener monopatines en una parada específica",
            description = "Devuelve una lista de monopatines que se encuentran en la parada indicada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatines obtenidos con éxito")
            },
            parameters = {
                    @Parameter(description = "ID de la parada", required = true)
            }
    )
    @GetMapping("/parada/{idParada}")
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesByParadaId(@PathVariable String idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito",
                monopatinService.findMonopatinesByParadaId(idParada)
        ));
    }

    @Operation(
            summary = "Obtener monopatines en una ubicación específica",
            description = "Devuelve una lista de monopatines que se encuentran en la ubicación indicada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Monopatines obtenidos con éxito")
            }
    )
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<ApiResponse<List<MonopatinDTO>>> getMonopatinesByUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                monopatinService.findMonopatinesByUbicacion(ubicacion)
        ));
    }

    @Operation(
            summary = "Crear un nuevo monopatín",
            description = "Crea un nuevo monopatín con los datos especificados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Monopatín creado con éxito")
            },
            parameters = {
                    @Parameter(description = "Datos del nuevo monopatín", required = true)
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMonopatin(@RequestBody @Valid CreateMonopatinDTO monopatin) {
        monopatinService.create(monopatin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Monopatin creado con éxito",
                null
        ));
    }

    @Operation(
            summary = "Actualizar el estado de un monopatín",
            description = "Permite cambiar el estado de un monopatín dado su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estado actualizado con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín a actualizar", required = true),
                    @Parameter(name = "estado", description = "Nuevo estado del monopatín", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/estado/{estado}")
    public ResponseEntity<ApiResponse<MonopatinDTO>> actualizarEstado(@PathVariable Long idMonopatin, @PathVariable EstadoMonopatin estado) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Estado actualizado con éxito.",
                monopatinService.actualizarEstado(idMonopatin, estado)
        ));
    }

    @Operation(
            summary = "Ubicar un monopatín en una parada",
            description = "Asocia un monopatín a una parada específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "El monopatín se ubicó en la parada con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín a ubicar", required = true),
                    @Parameter(name = "idParada", description = "ID de la parada donde se ubicará el monopatín", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/ubicar-en-parada/{idParada}")
    public ResponseEntity<ApiResponse<ParadaDTO>> ubicarMonopatinEnParada(@PathVariable Long idMonopatin, @PathVariable String idParada) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "El monopatin se ubicó en la parada con éxito.",
                monopatinService.ubicarMonopatinEnParada(idMonopatin, idParada)
        ));
    }

    @Operation(
            summary = "Actualizar kilómetros recorridos",
            description = "Actualiza los kilómetros recorridos de un monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Kilómetros actualizados con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín", required = true),
                    @Parameter(name = "cantKilometros", description = "Cantidad de kilómetros a actualizar", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/kilometros/{cantKilometros}")
    public ResponseEntity<ApiResponse<?>> actualizarKilometros(@PathVariable Long idMonopatin, @PathVariable int cantKilometros) {
        monopatinService.actualizarKilometros(idMonopatin, cantKilometros);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Kilometros actualizados con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Actualizar tiempo de uso",
            description = "Registra o actualiza el tiempo de uso acumulado de un monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tiempo de uso actualizado con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín", required = true),
                    @Parameter(name = "cantTiempoDeUso", description = "Cantidad de tiempo de uso en minutos", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/tiempo-de-uso/{cantTiempoDeUso}")
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDeUso(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDeUso) {
        monopatinService.actualizarTiempoDeUso(idMonopatin, cantTiempoDeUso);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de uso actualizado con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Actualizar tiempo de pausa",
            description = "Registra o actualiza el tiempo de pausa acumulado de un monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tiempo de pausa actualizado con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín", required = true),
                    @Parameter(name = "cantTiempoDePausa", description = "Cantidad de tiempo de pausa en minutos", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/tiempo-de-pausa/{cantTiempoDePausa}")
    public ResponseEntity<ApiResponse<?>> actualizarTiempoDePausa(@PathVariable Long idMonopatin, @PathVariable int cantTiempoDePausa) {
        monopatinService.actualizarTiempoDePausa(idMonopatin, cantTiempoDePausa);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tiempo de pausa actualizado con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Resetear estadísticas de un monopatín",
            description = "Reinicia a cero los kilómetros y el tiempo de uso de un monopatín.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estadísticas reseteadas con éxito.")
            },
            parameters = {
                    @Parameter(name = "idMonopatin", description = "ID del monopatín a resetear", required = true)
            }
    )
    @PutMapping("/{idMonopatin}/reset-estadisticas")
    public ResponseEntity<ApiResponse<?>> resetEstadisticas(@PathVariable Long idMonopatin) {
        monopatinService.resetKilometrosYTiempoDeUso(idMonopatin);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Los kilometros del momopatin se resetearon con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Eliminar un monopatín",
            description = "Elimina un monopatín de forma permanente.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Monopatín eliminado con éxito.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID del monopatín a eliminar", required = true)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMonopatin(@PathVariable Long id) {
        monopatinService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
