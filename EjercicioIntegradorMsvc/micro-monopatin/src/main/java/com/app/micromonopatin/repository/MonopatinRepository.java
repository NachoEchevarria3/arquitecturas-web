package com.app.micromonopatin.repository;

import com.app.micromonopatin.entity.Monopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<Monopatin, Long> {
    List<Monopatin> findMonopatinByIdParada(Long idParada);
}
