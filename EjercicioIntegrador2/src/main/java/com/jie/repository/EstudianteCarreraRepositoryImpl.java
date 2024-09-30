package com.jie.repository;

import com.jie.factory.RepositoryFactory;
import com.jie.model.EstudianteCarrera;
import com.jie.model.EstudianteCarreraId;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteCarreraRepositoryImpl implements Repository<EstudianteCarrera> {
    private RepositoryFactory repositoryFactory;

    public EstudianteCarreraRepositoryImpl(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public void save(EstudianteCarrera estudianteCarrera) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(estudianteCarrera);
            em.getTransaction().commit();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
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
