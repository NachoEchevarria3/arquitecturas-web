package com.app.micropago.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_viaje", nullable = false)
    private Long idViaje;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_metodo_pago", nullable = false)
    private Long idMetodoPago;

    private LocalDate fecha;

    @Column(nullable = false)
    private Double total;

    public Pago(Long idViaje, Long idUsuario, Long idMetodoPago, Double total) {
        this.idViaje = idViaje;
        this.idUsuario = idUsuario;
        this.idMetodoPago = idMetodoPago;
        this.fecha = LocalDate.now();
        this.total = total;
    }
}
