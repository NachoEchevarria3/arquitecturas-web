package com.app.microtarifa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tarifa_inicial")
    private double tarifaInicial;

    @Column(name = "tarifa_por_minuto")
    private double tarifaPorMinuto;

    @Column(name = "tarifa_pausa_extensa")
    private double tarifaPausaExtensa;

    @Column(name = "valida_desde", nullable = false)
    private LocalDate validaDesde;

    public Tarifa(double tarifaInicial, double tarifaPorMinuto, double tarifaPausaExtensa, LocalDate validaDesde) {
        this.tarifaInicial = tarifaInicial;
        this.tarifaPorMinuto = tarifaPorMinuto;
        this.tarifaPausaExtensa = tarifaPausaExtensa;
        this.validaDesde = validaDesde;
    }
}
