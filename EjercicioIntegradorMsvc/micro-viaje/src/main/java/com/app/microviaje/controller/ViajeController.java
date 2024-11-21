package com.app.microviaje.controller;

import com.app.microviaje.dto.ApiResponse;
import com.app.microviaje.dto.ComenzarViajeDTO;
import com.app.microviaje.dto.FinalizarViajeDTO;
import com.app.microviaje.dto.ReanudarViajeDTO;
import com.app.microviaje.entity.Viaje;
import com.app.microviaje.service.ViajeService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/viaje")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Viaje", description = "Controllador de Viajes")
public class ViajeController {
    @Autowired
    private ViajeService viajeService;

    @Hidden
    @GetMapping("/monopatines/minimo-viajes/{minimoViajes}")
    public ResponseEntity<ApiResponse<List<Long>>> getMonopatinesConMinimoDeViajes(
            @PathVariable Integer minimoViajes,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Monopatines obtenidos con éxito.",
                viajeService.getMonopatinesConMinimoDeViajes(minimoViajes, fechaDesde, fechaHasta)
        ));
    }

    @Operation(
            summary = "Comenzar viaje",
            description = "Registra el comienzo del viaje de un usuario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lleva la información del usuario y del monopatín.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ComenzarViajeDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Viaje comenzado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<?>> comenzarViaje(@Valid @RequestBody ComenzarViajeDTO viaje, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        viajeService.comenzarViaje(viaje, authorizationHeader.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Viaje comenzado con éxito.",
                null
        ));
    }
    @Operation(
            summary = "Pausar viaje",
            description = "Registra el comienzo de la pausa de un viaje",
            parameters = {
                    @Parameter(name = "idViaje", description = "Id del viaje a pausar.", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Viaje pausado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    )
            }
    )
    @PostMapping("/{id}/pausar")
    public ResponseEntity<ApiResponse<?>> pausarViaje(@PathVariable Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        viajeService.pausarViaje(id, authorizationHeader.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Viaje pausado con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Reanudar viaje",
            description = "Reanuda el viaje pausado anteriormente",
            parameters = {
                    @Parameter(name = "idViaje", description = "Id del viaje a reanudar.", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lleva la información de la pausa.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReanudarViajeDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Viaje reanudado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description =  "No se encontro el viaje"
                    )
            }
    )
    @PutMapping("/{id}/reanudar")
    public ResponseEntity<ApiResponse<?>> reanudarViaje(@PathVariable Long id, @Valid @RequestBody ReanudarViajeDTO infoReanudarViaje, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        viajeService.reanudarViaje(id, infoReanudarViaje, authorizationHeader.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Viaje reanudado con éxito.",
                null
        ));
    }

    @Operation(
            summary = "Finalizar viaje",
            parameters = {
                    @Parameter(name = "idViaje", description = "Id del viaje a finalizar.", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lleva la información del viaje.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FinalizarViajeDTO.class)
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Viaje finalizado con éxito"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Petición inválida"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description =  "No se encontró el viaje"
                    )
            }
    )
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ApiResponse<?>> finalizarViaje(@PathVariable Long id, @Valid @RequestBody FinalizarViajeDTO viaje, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        viajeService.finalizarViaje(id, viaje, authorizationHeader.split(" ")[1]);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                HttpStatus.OK.value(),
                "Viaje finalizado con éxito.",
                null
        ));
    }
}
