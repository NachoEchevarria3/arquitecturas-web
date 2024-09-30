package com.jie.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Carrera {
    @Id
    private int id;

    private String nombre;

    private int duracion;

    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;

    public Carrera() {}

    public Carrera(int id, String nombre, int duracion) {
        this.id = id;
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
