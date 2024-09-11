package com.jie.dto;

public class ClienteDto {
    private int id;
    private String nombre;
    private float facturacion;

    public ClienteDto() {}

    public ClienteDto(int id, String nombre, float facturacion) {
        this.id = id;
        this.nombre = nombre;
        this.facturacion = facturacion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getFacturacion() {
        return facturacion;
    }

    @Override
    public String toString() {
        return "ClienteDto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", facturacion=" + facturacion +
                '}';
    }
}
