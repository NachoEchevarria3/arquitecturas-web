package com.app.micromantmonopatin.controller;

import com.app.micromantmonopatin.dto.ApiResponse;
import com.app.micromantmonopatin.dto.CreateMantenimientoDTO;
import com.app.micromantmonopatin.dto.FinalizarMantenimientoDTO;
import com.app.micromantmonopatin.dto.MantenimientoDTO;
import com.app.micromantmonopatin.service.MantenimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimiento")
@Tag(name = "Mantenimiento de monopatines", description = "Controllador de Mant-monopatines")
@SecurityRequirement(name = "BearerAuth")
public class MantenimientoController {
    @Autowired
    private MantenimientoService mantenimientoService;

    @Operation(
            summary = "Obtiene una lista de los mantenimientos",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Lista de mantenimientos obtenida con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron mantenimientos."
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<MantenimientoDTO>>> getMantenimientos() {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de mantenimientos obtenida con éxito.",
                mantenimientoService.findAll()
        ));
    }

    @Operation(
            summary = "Obtiene una lista de mantenimientos mediante el id de un monopatín",
            parameters = {
                    @Parameter(name = "idMonopatin", description = "id del monopatín", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Lista de mantenimientos obtenida con éxito."
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron mantenimientos."
                    )
            }
    )
    @GetMapping("/monopatin/{idMonopatin}")
    public ResponseEntity<ApiResponse<List<MantenimientoDTO>>> getMantenimientosByIdMonopatin(@PathVariable Long idMonopatin) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de mantenimientos obtenida con éxito.",
                mantenimientoService.findAllByIdMonopatin(idMonopatin)
        ));
    }

    @Operation(
            summary = "Envía un monopatín a mantenimiento",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lleva la información del monopatín.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateMantenimientoDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Mantenimiento comenzado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    )
            }
    )
    @PostMapping("/iniciar")
    public ResponseEntity<ApiResponse<MantenimientoDTO>> create(@RequestBody CreateMantenimientoDTO mantenimiento) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Mantenimiento creado con éxito.",
                mantenimientoService.create(mantenimiento)
        ));
    }

    @Operation(
            summary = "Finaliza el mantenimiento de un monopatín",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lleva la información del monopatín y de la parada en la cual se va a dejar.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FinalizarMantenimientoDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Mantenimiento finalizado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "El mantenimiento no existe."
                    )
            }
    )
    @PostMapping("/finalizar")
    public ResponseEntity<ApiResponse<MantenimientoDTO>> finalizar(@RequestBody FinalizarMantenimientoDTO mantenimiento) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Mantenimiento finalizado con éxito.",
                mantenimientoService.finalizarMantenimiento(mantenimiento)
        ));
    }
}
