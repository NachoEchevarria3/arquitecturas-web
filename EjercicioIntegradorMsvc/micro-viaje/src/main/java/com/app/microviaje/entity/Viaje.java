package com.app.microviaje.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "viaje")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_monopatin", nullable = false)
    private Long idMonopatin;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "metodo_pago_asociado")
    private Long metodoPagoAsociado;

    @Column(name = "id_parada_destino")
    private String idParadaDestino;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    private Integer tiempo;

    private Integer kilometros;

    @Column(name = "tarifa_inicial")
    private double tarifaInicial;

    @Column(name = "tarifa_por_minuto")
    private double tarifaPorMinuto;

    @Column(name = "tarifa_pausa_extensa")
    private double tarifaPausaExtensa;

    @Column(name = "precio_total")
    private Double precioTotal;

    @OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Pausa> pausas;

    public Viaje(Long idMonopatin, Long idUsuario, Long metodoPagoAsociado, double tarifaInicial, double tarifaPorMinuto, double tarifaPausaExtensa) {
        this.idMonopatin = idMonopatin;
        this.idUsuario = idUsuario;
        this.metodoPagoAsociado = metodoPagoAsociado;
        this.fechaInicio = LocalDateTime.now();
        this.tarifaInicial = tarifaInicial;
        this.tarifaPorMinuto = tarifaPorMinuto;
        this.tarifaPausaExtensa = tarifaPausaExtensa;
    }
}
