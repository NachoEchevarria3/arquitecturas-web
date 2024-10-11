package com.app.ejerciciointegrador3.dto;

import jakarta.validation.constraints.Min;

public class InscripcionRequestDto {
    @Min(value = 1, message = "CarreraId debe ser mayor o igual a 1")
    private int carreraId;

    @Min(value = 1, message = "estudianteId debe ser mayor o igual a 1")
    private int estudianteId;

    @Min(value = 1, message = "anioInscripcion debe ser mayor o igual a 1")
    private int anioInscripcion;

    public InscripcionRequestDto() {}

    public InscripcionRequestDto(int carreraId, int estudianteId, int anioInscripcion) {
        this.carreraId = carreraId;
        this.estudianteId = estudianteId;
        this.anioInscripcion = anioInscripcion;
    }

    public int getCarreraId() {
        return carreraId;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    @Override
    public String toString() {
        return "InscripcionRequestDto{" +
                "carreraId=" + carreraId +
                ", estudianteId=" + estudianteId +
                ", anioInscripcion=" + anioInscripcion +
                '}';
    }
}
