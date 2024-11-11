package com.app.microtarifa.repository;

import com.app.microtarifa.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    @Query(
            "SELECT t " +
                    "FROM Tarifa t " +
                    "WHERE t.validaDesde <= :fecha " +
                    "ORDER BY t.validaDesde DESC "
    )
    List<Tarifa> findTarifaActual(@Param("fecha") LocalDate fecha);

    List<Tarifa> findByOrderByValidaDesdeDesc();
}
