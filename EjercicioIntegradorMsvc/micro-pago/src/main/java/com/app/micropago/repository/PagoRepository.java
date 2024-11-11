package com.app.micropago.repository;

import com.app.micropago.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    @Query(
            "SELECT SUM(p.total) " +
                    "FROM Pago p " +
                    "WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin"
    )
    Double getTotalFacturado(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
