package com.app.apigateway.service;

import com.app.apigateway.entity.MercadoPago;
import com.app.apigateway.repository.MercadoPagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoService {
    @Autowired
    private MercadoPagoRepository mercadoPagoRepository;

    public MercadoPago findById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("ID invalido.");
        return mercadoPagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La cuenta con el id " + id + " no existe."));
    }

    public MercadoPago iniciarSesion(MercadoPago info) {
        return mercadoPagoRepository.findByEmailAndPassword(info.getEmail(), info.getPassword())
                .orElseThrow(() -> new EntityNotFoundException("Email o Contraseña invalidos."));
    }

    public void cargarSaldo(Long idCuenta, Double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad invalida.");
        MercadoPago cuenta = findById(idCuenta);
        cuenta.setSaldo(cuenta.getSaldo() + cantidad);
        mercadoPagoRepository.save(cuenta);
    }

    public void descontarSaldo(Long idCuenta, Double cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad invalida.");
        MercadoPago cuenta = findById(idCuenta);

        // Verifica que tenga suficiente saldo
        if (cuenta.getSaldo() - cantidad < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        cuenta.setSaldo(cuenta.getSaldo() - cantidad);
        mercadoPagoRepository.save(cuenta);
    }

    public Double consultarSaldo(Long idCuenta) {
        MercadoPago cuenta = findById(idCuenta);
        return cuenta.getSaldo();
    }
}