package com.app.micromonopatin.entity;

import com.app.micromonopatin.constant.EstadoMonopatin;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "monopatin")
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "estado no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private EstadoMonopatin estado;

    @Min(value = 0, message = "kilometros debe ser mayor o igual a 0.")
    private int kilometros;

    @Min(value = 1, message = "limiteKilometros debe ser mayor o igual a 0.")
    @Column(name = "limite_kilometros")
    private int limiteKilometros;

    @Min(value = 0, message = "tiempoDeUso debe ser mayor o igual a 0.")
    @Column(name = "tiempo_de_uso")
    private int tiempoDeUso;

    @Min(value = 1, message = "limiteTiempoDeUso debe ser mayor o igual a 0.")
    @Column(name = "limite_tiempo_de_uso")
    private int limiteTiempoDeUso;

    public Monopatin(int limiteKilometros, int limiteTiempoDeUso) {
        this.estado = EstadoMonopatin.DISPONIBLE;
        this.kilometros = 0;
        this.limiteKilometros = limiteKilometros;
        this.tiempoDeUso = 0;
        this.limiteTiempoDeUso = limiteTiempoDeUso;
    }
}