package com.app.ejerciciointegrador3.dto;

public class ReporteCarreraDto {
    private String nombre;
    private int anio;
    private int inscriptos;
    private int graduados;

    public ReporteCarreraDto() {}

    public ReporteCarreraDto(String nombre, int anio, int inscriptos, int graduados) {
        this.nombre = nombre;
        this.anio = anio;
        this.inscriptos = inscriptos;
        this.graduados = graduados;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio() {
        return anio;
    }

    public int getInscriptos() {
        return inscriptos;
    }

    public int getGraduados() {
        return graduados;
    }

    @Override
    public String toString() {
        return "ReporteCarreraDto{" +
                "nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", inscriptos=" + inscriptos +
                ", graduados=" + graduados +
                '}';
    }
}
