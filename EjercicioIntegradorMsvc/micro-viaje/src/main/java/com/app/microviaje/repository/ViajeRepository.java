package com.app.microviaje.repository;

import com.app.microviaje.entity.Viaje;
import com.app.microviaje.projections.MonopatinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    @Query(
            "SELECT v.idMonopatin AS idMonopatin, COUNT(v) AS cantViajes " +
                    "FROM Viaje v " +
                    "WHERE v.fechaInicio BETWEEN :fechaDesde AND :fechaHasta " +
                    "GROUP BY v.idMonopatin " +
                    "HAVING COUNT(v) >= :minimoViajes"
    )
    List<MonopatinProjection> getMonopatinesConMinimoDeViajesEnAño(
            @Param("minimoViajes") Integer minimoViajes,

            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );
}
