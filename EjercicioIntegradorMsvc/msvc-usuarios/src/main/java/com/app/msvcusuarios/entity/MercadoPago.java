package com.app.msvcusuarios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mercado_pago")
public class MercadoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "email no puede estar vacio.")
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(message = "password no puede estar vacio.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "saldo no puede ser nulo.")
    @Column(nullable = false)
    private Double saldo;

    public MercadoPago(String email, String password) {
        this.email = email;
        this.password = password;
        this.saldo = 0.0;
    }
}
