package com.jie.repository;

import com.jie.model.EstudianteCarrera;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements Repository<EstudianteCarrera> {
    private EntityManager em;

    public EstudianteCarreraRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(EstudianteCarrera estudianteCarrera) {
        em.getTransaction().begin();
        em.merge(estudianteCarrera);
        em.getTransaction().commit();
    }

    @Override
    public EstudianteCarrera findById(int id) {
        return null;
    }

    @Override
    public List<EstudianteCarrera> findAll() {
        return List.of();
    }

    @Override
    public void delete(EstudianteCarrera estudianteCarrera) {

    }
}
