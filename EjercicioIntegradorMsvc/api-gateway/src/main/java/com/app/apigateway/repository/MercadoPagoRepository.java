package com.app.apigateway.repository;

import com.app.apigateway.entity.MercadoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MercadoPagoRepository extends JpaRepository<MercadoPago, Long> {
    Optional<MercadoPago> findByEmailAndPassword(String email, String password);
}
