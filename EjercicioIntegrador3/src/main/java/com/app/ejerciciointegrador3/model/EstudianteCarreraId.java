package com.app.ejerciciointegrador3.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EstudianteCarreraId implements Serializable {
    private int estudianteId;
    private int carreraId;

    public EstudianteCarreraId() {}

    public EstudianteCarreraId(int estudianteId, int carreraId) {
        this.estudianteId = estudianteId;
        this.carreraId = carreraId;
    }

    public int getEstudianteId() {
        return estudianteId;
    }

    public int getCarreraId() {
        return carreraId;
    }
}
