package com.jie.model;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate fechaInscripcion;
    private LocalDate fechaGraduacion;

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante estudiante, Carrera carrera, LocalDate fechaInscripcion) {
        this.id = new EstudianteCarreraId(estudiante.getId(), carrera.getId());
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaGraduacion = null;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public LocalDate getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(LocalDate fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }

    public boolean seGraduo() {
        return fechaGraduacion != null;
    }
}
