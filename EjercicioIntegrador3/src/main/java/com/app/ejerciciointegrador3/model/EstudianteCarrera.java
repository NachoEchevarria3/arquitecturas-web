package com.app.ejerciciointegrador3.model;

import jakarta.persistence.*;

@Entity
public class EstudianteCarrera {
    @Id
    @EmbeddedId
    private EstudianteCarreraId id;

    @ManyToOne
    @MapsId("estudianteId")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("carreraId")
    private Carrera carrera;

    private int anioInscripcion;
    private int anioGraduacion;
    private int antiguedad;

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int anioInscripcion) {
        this.id = new EstudianteCarreraId(estudiante.getDni(), carrera.getId());
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anioInscripcion = anioInscripcion;
        this.anioGraduacion = 0;
        this.antiguedad = 0;
    }

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int anioInscripcion, int anioGraduacion, int antiguedad) {
        this.id = new EstudianteCarreraId(estudiante.getDni(), carrera.getId());
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anioInscripcion = anioInscripcion;
        this.anioGraduacion = anioGraduacion;
        this.antiguedad = antiguedad;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public int getAnioGraduacion() {
        return anioGraduacion;
    }

    public void setAnioGraduacion(int anioGraduacion) {
        this.anioGraduacion = anioGraduacion;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean seGraduo() {
        return anioGraduacion > 0;
    }
}
