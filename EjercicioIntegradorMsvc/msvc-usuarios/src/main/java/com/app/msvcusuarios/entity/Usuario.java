package com.app.msvcusuarios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nombre no puede estar vacio.")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "apellido no puede estar vacio.")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "email no puede estar vacio.")
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(message = "password no puede estar vacio.")
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "^[+]?\\d{10,15}$", message = "Número de celular inválido")
    @Column(nullable = false)
    private String telefono;

    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_mercado_pago",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "mercado_pago_id")
    )
    List<MercadoPago> cuentasMercadoPago;

    public Usuario(String nombre, String apellido, String email, String password, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.fechaAlta = new Date();
    }
}
