package com.jie.repository;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Estudiante;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private RepositoryFactory repositoryFactory;

    public EstudianteRepositoryImpl(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public void save(Estudiante estudiante) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(estudiante);
            em.getTransaction().commit();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public Estudiante findById(int id) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<Estudiante> findAll() {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public void delete(Estudiante estudiante) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(estudiante);
            em.getTransaction().commit();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<Estudiante> findAllOrderedByEdad() {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estudiante e ORDER BY e.edad DESC", Estudiante.class).getResultList();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public Estudiante findByNumeroLibreta(int numeroLibreta) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return (Estudiante) em.createQuery("SELECT e FROM Estudiante e WHERE e.numeroLibreta = :numeroLibreta")
                    .setParameter("numeroLibreta", numeroLibreta)
                    .getSingleResult();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<Estudiante> findAllByGenero(String genero) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero").setParameter("genero", genero).getResultList();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }
}
