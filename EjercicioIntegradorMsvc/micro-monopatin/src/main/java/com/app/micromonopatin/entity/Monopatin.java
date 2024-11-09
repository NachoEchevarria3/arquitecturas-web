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

    @Min(value = 0, message = "kilometros debe ser mayor o igual a 0.")
    @Column(name = "historial_kilometros")
    private int historialKilometros;

    @Min(value = 1, message = "limiteKilometros debe ser mayor o igual a 0.")
    @Column(name = "limite_kilometros")
    private int limiteKilometros;

    @Min(value = 0, message = "tiempoDeUso debe ser mayor o igual a 0.")
    @Column(name = "tiempo_de_uso")
    private int tiempoDeUso;

    @Min(value = 0, message = "kilometros debe ser mayor o igual a 0.")
    @Column(name = "historial_tiempo_de_uso")
    private int historialTiempoDeUso;

    @Min(value = 0, message = "tiempoDeUso debe ser mayor o igual a 0.")
    @Column(name = "tiempo_de_pausa")
    private int tiempoDePausa;

    @Min(value = 1, message = "limiteTiempoDeUso debe ser mayor o igual a 0.")
    @Column(name = "limite_tiempo_de_uso")
    private int limiteTiempoDeUso;

    @Column(name = "id_parada")
    private Long idParada;

    public Monopatin(int limiteKilometros, int limiteTiempoDeUso, Long idParada) {
        this.estado = EstadoMonopatin.DISPONIBLE;
        this.kilometros = 0;
        this.historialKilometros = 0;
        this.limiteKilometros = limiteKilometros;
        this.tiempoDeUso = 0;
        this.historialTiempoDeUso = 0;
        this.tiempoDePausa = 0;
        this.limiteTiempoDeUso = limiteTiempoDeUso;
        this.idParada = idParada;
    }
}
