package com.app.microparada.controller;

import com.app.microparada.dto.ApiResponse;
import com.app.microparada.dto.CreateParadaDTO;
import com.app.microparada.dto.ParadaDTO;
import com.app.microparada.service.ParadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parada")
public class ParadaController {
    @Autowired
    private ParadaService paradaService;

    @Operation(
            summary = "Obtener todas las paradas",
            description = "Devuelve una lista de todas las paradas disponibles.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paradas obtenidas con éxito.")
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadas() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAll()
        ));
    }

    @Operation(
            summary = "Obtener paradas por ubicación",
            description = "Devuelve una lista de paradas según la ubicación especificada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paradas obtenidas con éxito."),
                    @ApiResponse(responseCode = "404", description = "No se encontraron paradas para la ubicación especificada.")
            },
            parameters = {
                    @Parameter(name = "ubicacion", description = "Ubicación de las paradas a buscar", required = true)
            }
    )
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadasByUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAllByUbicacion(ubicacion)
        ));
    }

    @Operation(
            summary = "Obtener parada por ID",
            description = "Devuelve los detalles de una parada específica según su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parada obtenida con éxito.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID de la parada a buscar", required = true)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParadaDTO>> getParadaById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Parada obtenida con éxito.",
                paradaService.findById(id)
        ));
    }

    @Operation(
            summary = "Crear una nueva parada",
            description = "Crea una nueva parada con los detalles proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parada creada con éxito.")
            },
            requestBody = @RequestBody(
                    description = "Detalles de la nueva parada a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateParadaDTO.class))
            )
    )
    @PostMapping
    public ResponseEntity<ApiResponse<ParadaDTO>> createParada(@Valid @RequestBody CreateParadaDTO parada) {
        paradaService.create(parada);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Parada creada con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Eliminar una parada por ID",
            description = "Elimina una parada específica según su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Parada eliminada con éxito.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID de la parada a eliminar", required = true)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParada(@PathVariable String id) {
        paradaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
