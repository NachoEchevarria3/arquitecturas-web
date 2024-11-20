package com.app.microparada.controller;

import com.app.microparada.dto.ApiResponse;
import com.app.microparada.dto.CreateParadaDTO;
import com.app.microparada.dto.ParadaDTO;
import com.app.microparada.service.ParadaService;
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

@RestController
@RequestMapping("/api/parada")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Paradas")
public class ParadaController {
    @Autowired
    private ParadaService paradaService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las paradas",
            description = "Devuelve una lista de todas las paradas disponibles.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paradas obtenidas con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron paradas"
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
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadas() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAll()
        ));
    }

    @GetMapping("/ubicacion/{ubicacion}")
    @Operation(
            summary = "Obtener paradas por ubicación",
            description = "Devuelve una lista de paradas según la ubicación especificada.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paradas obtenidas con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron paradas para la ubicación especificada."
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
                    @Parameter(name = "ubicacion", description = "Ubicación de las paradas a buscar", required = true)
            }
    )
    public ResponseEntity<ApiResponse<List<ParadaDTO>>> getParadasByUbicacion(@PathVariable String ubicacion) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Paradas obtenidas con éxito.",
                paradaService.findAllByUbicacion(ubicacion)
        ));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener parada por ID",
            description = "Devuelve los detalles de una parada específica según su ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Parada obtenida con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
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
                    @Parameter(name = "id", description = "ID de la parada a buscar", required = true)
            }
    )
    public ResponseEntity<ApiResponse<ParadaDTO>> getParadaById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Parada obtenida con éxito.",
                paradaService.findById(id)
        ));
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva parada",
            description = "Crea una nueva parada con los detalles proporcionados en el cuerpo de la solicitud.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles de la nueva parada a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateParadaDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Parada creada con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
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
    public ResponseEntity<ApiResponse<ParadaDTO>> createParada(@Valid @RequestBody CreateParadaDTO parada) {
        paradaService.create(parada);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Parada creada con éxito.",
                null
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una parada por ID",
            description = "Elimina una parada específica según su ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Parada eliminada con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
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
                    @Parameter(name = "id", description = "ID de la parada a eliminar", required = true)
            }
    )
    public ResponseEntity<?> deleteParada(@PathVariable String id) {
        paradaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
