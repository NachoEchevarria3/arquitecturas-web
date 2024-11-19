package com.app.microparada.repository;

import com.app.microparada.entity.Parada;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, Long> {
    List<Parada> findByUbicacion(String ubicacion);
}
