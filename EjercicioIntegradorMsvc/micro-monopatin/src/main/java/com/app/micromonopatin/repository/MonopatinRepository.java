package com.app.micromonopatin.repository;

import com.app.micromonopatin.constant.EstadoMonopatin;
import com.app.micromonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
    List<Monopatin> findMonopatinByIdParada(Long idParada);
    List<Monopatin> findByOrderByKilometrosDesc();
    List<Monopatin> findByOrderByTiempoDeUsoDesc();

    @Query(
            "SELECT m.id, (m.tiempoDeUso - m.tiempoDePausa) AS tiempoSinPausa FROM Monopatin m " +
                    "ORDER BY (m.tiempoDeUso - m.tiempoDePausa) DESC"
    )
    List<Object[]> findOrderByTiempoDeUsoSinPausaDesc();

    @Query("SELECT m FROM Monopatin m WHERE m.id IN :ids")
    List<Monopatin> findByIds(@Param("ids") List<Long> ids);
    List<Monopatin> findByEstado(EstadoMonopatin estado);

    @Query("SELECT m FROM Monopatin m WHERE m.idParada IN :paradaIds")
    List<Monopatin> findByParadaIds(@Param("paradaIds") List<Long> paradaIds);
}
