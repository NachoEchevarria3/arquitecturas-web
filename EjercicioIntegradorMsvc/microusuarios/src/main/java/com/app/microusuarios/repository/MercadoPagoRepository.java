package com.app.microusuarios.repository;

import com.app.microusuarios.entity.MercadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MercadoPagoRepository extends JpaRepository<MercadoPago, Long> {
    Optional<MercadoPago> findByEmailAndPassword(String email, String password);
}
