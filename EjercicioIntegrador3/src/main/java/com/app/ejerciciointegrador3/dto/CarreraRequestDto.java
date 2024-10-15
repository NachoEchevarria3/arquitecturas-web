package com.app.ejerciciointegrador3.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CarreraRequestDto {
    @NotBlank(message = "El nombre de la carrera no puede estar vacío")
    private String nombre;

    @Min(value = 1, message = "La duración debe ser mayor o igual a 1")
    private int duracion;

    public CarreraRequestDto() {}

    public CarreraRequestDto(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }
}
