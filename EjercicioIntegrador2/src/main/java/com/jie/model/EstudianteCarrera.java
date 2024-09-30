package com.jie.model;

import javax.persistence.*;

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

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, int anioInscripcion) {
        this.id = new EstudianteCarreraId(estudiante.getId(), carrera.getId());
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anioInscripcion = anioInscripcion;
        this.anioGraduacion = -1;
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

    public boolean seGraduo() {
        return anioGraduacion != -1;
    }
}
