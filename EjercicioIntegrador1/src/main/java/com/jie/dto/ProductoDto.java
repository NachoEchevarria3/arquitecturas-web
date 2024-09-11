package com.jie.dto;

public class ProductoDto {
    private int id;
    private String nombre;
    private float precio;
    private float recaudacion;

    public ProductoDto() {}

    public ProductoDto(int id, String nombre, float precio, float recaudacion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.recaudacion = recaudacion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    @Override
    public String toString() {
        return "ProductoDto{" +
                "id=" + id +
                ", nombre='" + nombre +
                ", precio=" + precio +
                ", recaudacion=" + recaudacion +
                '}';
    }
}
