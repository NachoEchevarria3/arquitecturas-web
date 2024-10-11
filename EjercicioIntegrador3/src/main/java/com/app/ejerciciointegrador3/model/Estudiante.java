package com.app.ejerciciointegrador3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Estudiante {
    @Id
    @Min(value = 1000000, message = "DNI invalido")
    private int dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @Min(value = 17, message = "La edad debe ser mayor o igual a 17")
    private int edad;

    @NotBlank(message = "El genero no puede estar vacío")
    private String genero;

    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudadResidencia;

    @Column(unique = true)
    @Min(value = 1, message = "El número de libreta debe ser mayor o igual a 1")
    private int numeroLibreta;

    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> carreras;

    public Estudiante() {}

    public Estudiante(int dni, String nombre, String apellido, int edad, String genero, String ciudadResidencia, int numeroLibreta) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.numeroLibreta = numeroLibreta;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public int getNumeroLibreta() {
        return numeroLibreta;
    }

    public void setNumeroLibreta(int numeroLibreta) {
        this.numeroLibreta = numeroLibreta;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                '}';
    }
}
