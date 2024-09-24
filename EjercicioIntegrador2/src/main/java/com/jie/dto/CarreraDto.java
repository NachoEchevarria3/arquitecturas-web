package com.jie.dto;

public class CarreraDto {
    private int id;
    private String nombre;
    private int cantidadInscriptos;

    public CarreraDto() {}

    public CarreraDto(int id, String nombre, int cantidadInscriptos) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    @Override
    public String toString() {
        return "CarreraDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidadInscriptos=" + cantidadInscriptos +
                '}';
    }
}
