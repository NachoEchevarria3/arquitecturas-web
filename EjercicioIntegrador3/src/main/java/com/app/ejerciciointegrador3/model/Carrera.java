package com.app.ejerciciointegrador3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

import java.util.List;

@Entity
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "El nombre de la carrera no puede estar vacío")
    private String nombre;

    @Min(value = 1, message = "La duración debe ser mayor o igual a 1")
    private int duracion;

    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;

    public Carrera() {}

    public Carrera(String nombre, int duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", duracion=" + duracion +
                ", estudiantes=" + estudiantes +
                '}';
    }
}
